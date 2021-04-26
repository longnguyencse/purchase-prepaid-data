package abaca.com.voucher.service;

import abaca.com.voucher.dto.PurchasePrepaidDataDTO;
import abaca.com.voucher.dto.VoucherDataDTO;

import java.security.NoSuchAlgorithmException;

public interface VoucherService {
    VoucherDataDTO getVoucherPrePaidData(PurchasePrepaidDataDTO purchasePrepaidDataDTO) throws NoSuchAlgorithmException, InterruptedException;
}
