package abaca.com.prepaid.data.controller;


import abaca.com.prepaid.data.dto.PrepaidDataDTO;
import abaca.com.prepaid.data.dto.ResultDTO;
import abaca.com.prepaid.data.dto.VoucherDataDTO;
import abaca.com.prepaid.data.model.PhoneEntity;
import abaca.com.prepaid.data.repository.PhoneRepository;
import abaca.com.prepaid.data.service.PurchaseDataService;
import abaca.com.prepaid.data.utils.PhoneUtils;
import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "purchase-data")
@Slf4j
public class PurchaseDataController {

    @Autowired
    PurchaseDataService purchaseDataService;
    
    @Autowired
    PhoneRepository phoneRepository;
    

    @PostMapping(value = "/prepaid")
    public ResultDTO<VoucherDataDTO> purchaseDataPrepaid(@RequestBody PrepaidDataDTO prepaidDataDTO) {

        return ResultDTO.<VoucherDataDTO>builder()
                .data(purchaseDataService.purchaseData(prepaidDataDTO))
                .build();
    }
    
//  @PostMapping(value = "/prepaid")
//  public CompletableFuture<ResultDTO<VoucherDataDTO>> prepareDataVoucher(
//      @RequestBody PrepaidDataDTO prepaidDataDTO) {
//    
//    //Validate phone
//    final String phoneStr = PhoneUtils.generatePhoneFormat(prepaidDataDTO.getPhone());
//    PhoneEntity phoneEntity = phoneRepository.findByPhoneNumber(phoneStr);
//    if(null == phoneEntity) {
//      log.info("Phone number is not exist in DB, to create new phone");
//      
//    }else {
//      log.info("Phone number is exist in DB");
//      
//    }
//    
//    final CompletableFuture<ResultDTO<VoucherDataDTO>> futureResult =
//        new CompletableFuture<>();
//    final ResultDTO<VoucherDataDTO> dataResult = ResultDTO.<VoucherDataDTO> builder()
//        .data(null).message("Voucher is being prepared...").build();
//
//    futureResult.complete(dataResult);
//    CompletableFuture.runAsync(() -> purchaseDataService.purchaseData(prepaidDataDTO));
//
//    return futureResult;
//  }
}
