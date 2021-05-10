package abaca.com.prepaid.data.service;

import abaca.com.prepaid.data.dto.PrepaidDataDTO;
import abaca.com.prepaid.data.dto.ResultDTO;
import abaca.com.prepaid.data.dto.VoucherDataDTO;

import java.util.List;

public interface PurchaseDataService {
    VoucherDataDTO purchaseData(PrepaidDataDTO prepaidDataDTO, Long id);

    VoucherDataDTO getVoucher(Long id);

    ResultDTO<List<VoucherDataDTO>> getAllVoucher(final String phoneNumber, final Integer page, final Integer size);

    Boolean sendNotification(String msg);

    ResultDTO<VoucherDataDTO> processPurchaseDataForMobile(final PrepaidDataDTO prepaidDataDTO);
}
