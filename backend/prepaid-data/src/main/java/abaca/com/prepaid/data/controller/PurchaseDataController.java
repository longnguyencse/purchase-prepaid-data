package abaca.com.prepaid.data.controller;


import abaca.com.prepaid.data.dto.PrepaidDataDTO;
import abaca.com.prepaid.data.dto.ResultDTO;
import abaca.com.prepaid.data.dto.VoucherDataDTO;
import abaca.com.prepaid.data.enums.PhoneTransactionEnum;
import abaca.com.prepaid.data.model.PhoneVoucherEntity;
import abaca.com.prepaid.data.repository.PhoneVoucherRepository;
import abaca.com.prepaid.data.service.PurchaseDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
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
            @RequestBody PrepaidDataDTO prepaidDataDTO) throws Exception {

        final CompletableFuture<ResultDTO<VoucherDataDTO>> futureResult =
                new CompletableFuture<>();
        ResultDTO<VoucherDataDTO> dataResult = ResultDTO.<VoucherDataDTO>builder().build();

        //Validate phone
//        if(!PhoneUtils.isValidPhoneNumber(prepaidDataDTO.getPhone())) {
//          log.warn("Phone number is invalid.");
//          dataResult.setSuccess(false);
//          dataResult.setMessage("Phone number is invalid.");
//          futureResult.complete(dataResult);
//          return futureResult;
//        }

        final String phoneStr = prepaidDataDTO.getPhone();
        final LocalDateTime currentTime = LocalDateTime.now();
        PhoneVoucherEntity phoneVoucherEntity = PhoneVoucherEntity.builder().phoneNumber(phoneStr).createTime(currentTime)
                .transmissionTime(currentTime)
                .status(PhoneTransactionEnum.PREPARING_VOUCHER_DATA.getValue()).build();
        phoneVoucherRepository.save(phoneVoucherEntity);
        log.info("ID " + phoneVoucherEntity.getId());
        Long phoneVoucherID = phoneVoucherEntity.getId();
        dataResult = ResultDTO.<VoucherDataDTO>builder()
                .data(VoucherDataDTO.builder()
                        .id(phoneVoucherEntity.getId())
                        .build()).message("Voucher is being prepared...").build();
        CompletableFuture.runAsync(() -> purchaseDataService.purchaseData(prepaidDataDTO, phoneVoucherID));
        futureResult.complete(dataResult);
        return futureResult;
    }

    @GetMapping(value = "{id}")
    public ResultDTO<VoucherDataDTO> getVoucher(@PathVariable("id") Long id) {
        return ResultDTO.<VoucherDataDTO>builder()
                .data(purchaseDataService.getVoucher(id))
                .build();
    }

    @GetMapping(value = "all")
    public ResultDTO<List<VoucherDataDTO>> getAllVoucher(@RequestParam("phone") String phone, @RequestParam("page") Integer page,
                                                         @RequestParam("size") Integer size) {

        return purchaseDataService.getAllVoucher(phone, page, size);
    }
}
