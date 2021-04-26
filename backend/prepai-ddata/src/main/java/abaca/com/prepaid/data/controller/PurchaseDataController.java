package abaca.com.prepaid.data.controller;


import abaca.com.prepaid.data.dto.PrepaidDataDTO;
import abaca.com.prepaid.data.dto.ResultDTO;
import abaca.com.prepaid.data.dto.VoucherDataDTO;
import abaca.com.prepaid.data.service.PurchaseDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "purchase-data")
public class PurchaseDataController {

    @Autowired
    PurchaseDataService purchaseDataService;

    @PostMapping(value = "/prepaid")
    public ResultDTO<VoucherDataDTO> purchaseDataPrepaid(@RequestBody PrepaidDataDTO prepaidDataDTO) {

        return ResultDTO.<VoucherDataDTO>builder()
                .data(purchaseDataService.purchaseData(prepaidDataDTO))
                .build();
    }
}
