package abaca.com.prepaid.data.service.impl;

import abaca.com.jms.dto.PhoneVoucherJmsDto;
import abaca.com.prepaid.data.dto.PrepaidDataDTO;
import abaca.com.prepaid.data.dto.PurchasePrepaidDataDTO;
import abaca.com.prepaid.data.dto.ResultDTO;
import abaca.com.prepaid.data.dto.VoucherDataDTO;
import abaca.com.prepaid.data.enums.PhoneTransactionEnum;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import retrofit2.Retrofit;

import java.time.LocalDateTime;
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
            if (timeReceive - timeStart >= timeOutSendSMS * 1000) {
              // TODO: message queue
              final PhoneVoucherJmsDto phoneJmsDto =
                  PhoneVoucherJmsDto.builder().phoneNumber(prepaidDataDTO.getPhone())
                      .message("Purchase voucher successfully!").build();
              String msg = objectMapper.writeValueAsString(phoneJmsDto);
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
      PhoneVoucherEntity phoneVoucherEntity =
          phoneVoucherRepository.findById(phoneVoucherID).orElse(null);
      if (null == phoneVoucherEntity) {
        log.warn("Update failed: PhoneVoucherEntity is null");
        return;
      }
      if (isSuccess) {
        phoneVoucherEntity
            .setVoucherCode(encryptDecryptService.encrypt(voucherDataDTO.getVoucherCode()));
        phoneVoucherEntity.setVoucherAmount(voucherDataDTO.getAmount());
        phoneVoucherEntity.setStatus(PhoneTransactionEnum.SUCCESS.getValue());
      } else {
        phoneVoucherEntity.setStatus(PhoneTransactionEnum.FAILED.getValue());
      }
      phoneVoucherEntity.setTransmissionTime(LocalDateTime.now());
      phoneVoucherRepository.save(phoneVoucherEntity);
      log.info("Update phone voucher successfully: {}", phoneVoucherEntity.toString());
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
