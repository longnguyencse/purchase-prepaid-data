package abaca.com.voucher.controller;

import abaca.com.voucher.annotation.AccessCheck;
import abaca.com.voucher.constant.AccessType;
import abaca.com.voucher.dto.PurchasePrepaidDataDTO;
import abaca.com.voucher.dto.ResultDTO;
import abaca.com.voucher.dto.VoucherDataDTO;
import abaca.com.voucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "voucher")
public class VoucherController {

    @Autowired
    VoucherService voucherService;

    @AccessCheck(accessType = AccessType.PUBLIC)
    @PostMapping(value = "purchase-prepaid-data")
    public ResultDTO<VoucherDataDTO> purchasePrepaidData(@RequestHeader(value = "Accept-Language", defaultValue = "vi") String language,
                                                         @RequestHeader(value = "Accept-Token") String token,
                                                         @RequestBody PurchasePrepaidDataDTO purchasePrepaidDataDTO)
            throws Exception {
        return ResultDTO.<VoucherDataDTO>builder()
                .data(voucherService.getVoucherPrePaidData(purchasePrepaidDataDTO))
                .build();
    }
}