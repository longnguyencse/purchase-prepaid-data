package abaca.com.prepaid.data.service.impl;

import abaca.com.prepaid.data.dto.PrepaidDataDTO;
import abaca.com.prepaid.data.dto.PurchasePrepaidDataDTO;
import abaca.com.prepaid.data.dto.ResultDTO;
import abaca.com.prepaid.data.dto.VoucherDataDTO;
import abaca.com.prepaid.data.model.PhoneVoucherEntity;
import abaca.com.prepaid.data.repository.PhoneVoucherRepository;
import abaca.com.prepaid.data.service.EncryptDecryptService;
import abaca.com.prepaid.data.service.NotificationService;
import abaca.com.prepaid.data.service.PurchaseDataService;
import abaca.com.prepaid.data.service.RetrofitVoucherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class PurchaseDataServiceImpl implements PurchaseDataService {

    @Autowired
    Retrofit retrofit;

    @Autowired
    NotificationService notificationService;

    @Autowired
    PhoneVoucherRepository phoneVoucherRepository;

    @Autowired
    EncryptDecryptService encryptDecryptService;

    @Value("${timeout.send.sms}")
    Integer timeOutSendSMS;

    @Override
    public VoucherDataDTO purchaseData(PrepaidDataDTO prepaidDataDTO, final Long phoneVoucherID) {
        long timeStart = System.currentTimeMillis();
        RetrofitVoucherService retrofitOrderService = retrofit.create(RetrofitVoucherService.class);
        try {
            PurchasePrepaidDataDTO data = PurchasePrepaidDataDTO.builder()
                    .phone(prepaidDataDTO.getPhone())
                    .amount(prepaidDataDTO.getAmount())
                    .build();
            ResultDTO<VoucherDataDTO> resultDTO = retrofitOrderService.purchaseVoucher("vi", "123", data).execute().body();
            VoucherDataDTO voucherDataDTO = null;
            if (null != resultDTO) {
                voucherDataDTO = resultDTO.getData();
            }
            if (null != voucherDataDTO) {
                log.info("begin save voucher");

                PhoneVoucherEntity phoneVoucherEntity = phoneVoucherRepository.findById(phoneVoucherID).orElse(null);
                if (phoneVoucherEntity != null) {
                    phoneVoucherEntity.setTransmissionTime(LocalDateTime.now());
                    phoneVoucherEntity.setVoucherCode(encryptDecryptService.encrypt(voucherDataDTO.getVoucherCode()));
                    phoneVoucherEntity.setPhoneNumber(voucherDataDTO.getPhone());
                    phoneVoucherEntity.setVoucherAmount(voucherDataDTO.getAmount());
                    phoneVoucherRepository.save(phoneVoucherEntity);
                }
            }
            long timeReceive = System.currentTimeMillis();
            if (timeReceive - timeStart >= timeOutSendSMS * 1000) {
                // TODO: message queue
                String msg = "";
                sendSMS(msg);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
        return null;
    }

    private void sendSMS(String data) {
        // TODO: save db, if fail, had scheduler job send again
        notificationService.sendSMS(data);
    }

    @Override
    public VoucherDataDTO getVoucher(Long id) {
        PhoneVoucherEntity phoneVoucherEntity = phoneVoucherRepository.findById(id).orElse(null);
        if (null != phoneVoucherEntity) {
            return
                    VoucherDataDTO.builder()
                            .phone(phoneVoucherEntity.getPhoneNumber())
                            .amount(phoneVoucherEntity.getVoucherAmount())
                            .voucherCode(encryptDecryptService.decrypt(phoneVoucherEntity.getVoucherCode()))
                            .amount(phoneVoucherEntity.getVoucherAmount())
                            .build();
        }
        return null;
    }

    @Override
    public List<VoucherDataDTO> getAllVoucher(String phoneNumber, Integer page, Integer size) {
        if (null == page || null == size) {
            return null;
        }
        Pageable pageable =
                PageRequest.of(page, size, Sort.by("create_time").descending());
        phoneVoucherRepository.getAllByPhoneNumber(phoneNumber, pageable).orElse(null);
        return null;
    }
}
