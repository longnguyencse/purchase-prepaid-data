package abaca.com.prepaid.data.controller;


import abaca.com.prepaid.data.dto.PrepaidDataDTO;
import abaca.com.prepaid.data.dto.ResultDTO;
import abaca.com.prepaid.data.dto.VoucherDataDTO;
import abaca.com.prepaid.data.enums.PhoneTransactionEnum;
import abaca.com.prepaid.data.model.PhoneVoucherEntity;
import abaca.com.prepaid.data.repository.PhoneVoucherRepository;
import abaca.com.prepaid.data.service.PurchaseDataService;
import abaca.com.prepaid.data.utils.PhoneUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "purchase-data")
@Slf4j
public class PurchaseDataController {

    @Autowired
    PurchaseDataService purchaseDataService;

    @Autowired
    PhoneVoucherRepository phoneVoucherRepository;

    @PostMapping(value = "/prepaid")
    public CompletableFuture<ResultDTO<VoucherDataDTO>> prepareDataVoucher(
            @RequestBody PrepaidDataDTO prepaidDataDTO) {

        //Validate phone
        final String phoneStr = PhoneUtils.generatePhoneFormat(prepaidDataDTO.getPhone());
        final LocalDateTime currentTime = LocalDateTime.now();
        PhoneVoucherEntity phoneVoucherEntity = PhoneVoucherEntity.builder().phoneNumber(phoneStr).createTime(currentTime)
                .transmissionTime(currentTime)
                .status(PhoneTransactionEnum.PREPARING_VOUCHER_DATA.getValue()).build();
        phoneVoucherRepository.save(phoneVoucherEntity);
        log.info("ID " + phoneVoucherEntity.getId());
        Long phoneVoucherID = phoneVoucherEntity.getId();
        final CompletableFuture<ResultDTO<VoucherDataDTO>> futureResult =
                new CompletableFuture<>();
        final ResultDTO<VoucherDataDTO> dataResult = ResultDTO.<VoucherDataDTO>builder()
                .data(VoucherDataDTO.builder()
                        .id(phoneVoucherEntity.getId())
                        .build()).message("Voucher is being prepared...").build();

        futureResult.complete(dataResult);
        CompletableFuture.runAsync(() -> purchaseDataService.purchaseData(prepaidDataDTO, phoneVoucherID));
        return futureResult;
    }

    @GetMapping(value = "")
    public ResultDTO<VoucherDataDTO> getVoucher(@RequestParam("id") Integer id) {
        return null;
//        return ResultDTO.<VoucherDataDTO>BuiBuilder
//                .
    }

}
