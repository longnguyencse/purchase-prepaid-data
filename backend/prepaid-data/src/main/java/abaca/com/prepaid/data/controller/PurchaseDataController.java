package abaca.com.prepaid.data.controller;


import abaca.com.prepaid.data.dto.PrepaidDataDTO;
import abaca.com.prepaid.data.dto.ResultDTO;
import abaca.com.prepaid.data.dto.VoucherDataDTO;
import abaca.com.prepaid.data.repository.PhoneVoucherRepository;
import abaca.com.prepaid.data.service.PurchaseDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

        //TODO : Validate phone
//        if(!PhoneUtils.isValidPhoneNumber(prepaidDataDTO.getPhone())) {
//          log.warn("Phone number is invalid.");
//          dataResult.setSuccess(false);
//          dataResult.setMessage("Phone number is invalid.");
//          futureResult.complete(dataResult);
//          return futureResult;
//        }
        futureResult.complete(purchaseDataService.processPurchaseDataForMobile(prepaidDataDTO));
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

    @PostMapping(value = "notification")
    public ResultDTO<Boolean> sendNotification(@RequestParam("message") String message) {

        return ResultDTO.<Boolean>builder()
                .data(purchaseDataService.sendNotification(message))
                .build();
    }
}
