package abaca.com.prepaid.data.service.impl;

import abaca.com.prepaid.data.dto.PrepaidDataDTO;
import abaca.com.prepaid.data.dto.PurchasePrepaidDataDTO;
import abaca.com.prepaid.data.dto.ResultDTO;
import abaca.com.prepaid.data.dto.VoucherDataDTO;
import abaca.com.prepaid.data.enums.PhoneTransactionEnum;
import abaca.com.prepaid.data.model.PhoneVoucher;
import abaca.com.prepaid.data.repository.PhoneVoucherRepository;
import abaca.com.prepaid.data.service.EncryptDecryptService;
import abaca.com.prepaid.data.service.NotificationService;
import abaca.com.prepaid.data.service.PurchaseDataService;
import abaca.com.prepaid.data.service.RetrofitVoucherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PurchaseDataServiceImpl implements PurchaseDataService {

    @Autowired
    private Retrofit retrofit;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private PhoneVoucherRepository phoneVoucherRepository;

    @Autowired
    private EncryptDecryptService encryptDecryptService;
    
    private final ObjectMapper objectMapper;
    
    public PurchaseDataServiceImpl() {
      objectMapper = new ObjectMapper();
    }

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
                this.updatePhoneVoucher(phoneVoucherID, voucherDataDTO, true);
            }
            long timeReceive = System.currentTimeMillis();
            if (timeReceive - timeStart >= 0) {
                // TODO: message queue
                log.info("sent sms");
                String msg = "";
                if (null == voucherDataDTO) {
                    msg = "Transaction error ";
                } else {
                    msg = "your voucher is " + voucherDataDTO.getVoucherCode();
                }
                sendSMS(msg);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            this.updatePhoneVoucher(phoneVoucherID, null, false);
            return null;
        }
        return null;
    }
    
    private void updatePhoneVoucher(final Long phoneVoucherID,
        final VoucherDataDTO voucherDataDTO, boolean isSuccess) {
        PhoneVoucher phoneVoucher =
                phoneVoucherRepository.findById(phoneVoucherID).orElse(null);
        if (null == phoneVoucher) {
            log.warn("Update failed: PhoneVoucherEntity is null");
            return;
        }
        if (isSuccess) {
            phoneVoucher
                    .setVoucherCode(encryptDecryptService.encrypt(voucherDataDTO.getVoucherCode()));
            phoneVoucher.setVoucherAmount(voucherDataDTO.getAmount());
            phoneVoucher.setStatus(PhoneTransactionEnum.SUCCESS.getValue());
        } else {
            phoneVoucher.setStatus(PhoneTransactionEnum.FAILED.getValue());
        }
        phoneVoucher.setTransmissionTime(LocalDateTime.now());
        phoneVoucherRepository.save(phoneVoucher);
        log.info("Update phone voucher successfully: {}", phoneVoucher.toString());
    }

    private void sendSMS(String data) {
        // TODO: save db, if fail, had scheduler job send again
        notificationService.sendSMS(data);
    }

    @Override
    public VoucherDataDTO getVoucher(Long id) {
        PhoneVoucher phoneVoucher = phoneVoucherRepository.findById(id).orElse(null);
        if (null != phoneVoucher) {
            return
                    VoucherDataDTO.builder()
                            .phone(phoneVoucher.getPhoneNumber())
                            .amount(phoneVoucher.getVoucherAmount())
                            .voucherCode(encryptDecryptService.decrypt(phoneVoucher.getVoucherCode()))
                            .amount(phoneVoucher.getVoucherAmount())
                            .build();
        }
        return null;
    }

    @Override
    public ResultDTO<List<VoucherDataDTO>> getAllVoucher(String phoneNumber, Integer page, Integer size) {
        if (null == page || null == size) {
            return null;
        }
        List<VoucherDataDTO> voucherDataDTOS = new ArrayList<>();
        Pageable pageable =
                PageRequest.of(page, size, Sort.by("createTime").descending());
        List<PhoneVoucher> phoneVouchers = phoneVoucherRepository.findAll();

        Page<PhoneVoucher> rs = phoneVoucherRepository.findByPhoneNumber(phoneNumber, pageable);
        if (null != rs) {
            List<PhoneVoucher> phoneVoucherEntities = rs.getContent();
            for (PhoneVoucher item : phoneVoucherEntities) {
                voucherDataDTOS.add(VoucherDataDTO.builder()
                        .amount(item.getVoucherAmount())
                        .voucherCode(encryptDecryptService.decrypt(item.getVoucherCode()))
                        .phone(item.getPhoneNumber())
                        .build());
            }
            return ResultDTO.<List<VoucherDataDTO>>builder()
                    .data(voucherDataDTOS)
                    .page(page)
                    .size(size)
                    .totalPage(rs.getTotalPages())
                    .totalRecord(rs.getTotalElements())
                    .build();
        }
        return null;
    }

    @Override
    public Boolean sendNotification(String msg) {
        try {
            return notificationService.sendSMS(msg);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }
}
