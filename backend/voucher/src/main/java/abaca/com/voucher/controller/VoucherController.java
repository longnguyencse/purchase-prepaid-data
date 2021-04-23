package abaca.com.voucher.controller;

import abaca.com.voucher.dto.PurchasePrepaidDataDTO;
import abaca.com.voucher.dto.ResultDTO;
import abaca.com.voucher.service.VoucherService;
import abaca.com.voucher.utility.BytesToHex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@RestController
@RequestMapping(value = "voucher")
public class VoucherController {

    @Autowired
    VoucherService voucherService;

    @PostMapping(value = "purchase-prepaid-data")
    public ResultDTO purchasePrepaidData(@RequestHeader(value = "Accept-Language", defaultValue = "vi") String language,
                                         @RequestHeader(value = "Accept-Token") String token, @RequestBody PurchasePrepaidDataDTO purchasePrepaidDataDTO)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        UUID uuid = UUID.randomUUID();
        MessageDigest salt = MessageDigest.getInstance("SHA-256");
        salt.update(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
        String digest = BytesToHex.bytesToHex(salt.digest());
        return ResultDTO.builder()
                .data(digest)
                .build();
    }
}