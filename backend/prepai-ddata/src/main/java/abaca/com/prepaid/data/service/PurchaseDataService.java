package abaca.com.prepaid.data.service;

import abaca.com.prepaid.data.dto.PrepaidDataDTO;
import abaca.com.prepaid.data.dto.VoucherDataDTO;

public interface PurchaseDataService {
    VoucherDataDTO purchaseData(PrepaidDataDTO prepaidDataDTO);
}
