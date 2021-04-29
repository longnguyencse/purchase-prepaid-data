package abaca.com.prepaid.data.service;

import abaca.com.prepaid.data.dto.PrepaidDataDTO;
import abaca.com.prepaid.data.dto.ResultDTO;
import abaca.com.prepaid.data.dto.VoucherDataDTO;

import java.util.List;

public interface PurchaseDataService {
    VoucherDataDTO purchaseData(PrepaidDataDTO prepaidDataDTO, Long id);

    VoucherDataDTO getVoucher(Long id);

    ResultDTO<List<VoucherDataDTO>> getAllVoucher(String phoneNumber, Integer page, Integer size);
}
