package abaca.com.voucher.service.impl;

import abaca.com.voucher.annotation.Loggable;
import abaca.com.voucher.dto.PurchasePrepaidDataDTO;
import abaca.com.voucher.dto.VoucherDataDTO;
import abaca.com.voucher.service.VoucherService;
import abaca.com.voucher.utility.BytesToHex;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
public class VoucherServiceImpl implements VoucherService {

    @Loggable
    @Override
    public VoucherDataDTO getVoucherPrePaidData(PurchasePrepaidDataDTO purchasePrepaidDataDTO) throws NoSuchAlgorithmException {
        UUID uuid = UUID.randomUUID();
        MessageDigest salt = MessageDigest.getInstance("SHA-256");
        salt.update(uuid.toString().getBytes(StandardCharsets.UTF_8));
        String digest = BytesToHex.bytesToHex(salt.digest());

        return VoucherDataDTO.builder()
                .amount(purchasePrepaidDataDTO.getAmount())
                .voucherCode(digest)
                .phone(purchasePrepaidDataDTO.getPhone())
                .currency("VND")
                .build();
    }
}

