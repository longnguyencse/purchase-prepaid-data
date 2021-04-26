package abaca.com.prepaid.data.service.impl;

import abaca.com.prepaid.data.dto.PrepaidDataDTO;
import abaca.com.prepaid.data.dto.PurchasePrepaidDataDTO;
import abaca.com.prepaid.data.dto.VoucherDataDTO;
import abaca.com.prepaid.data.service.PurchaseDataService;
import abaca.com.prepaid.data.service.RetrofitVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;

@Service
public class PurchaseDataServiceImpl implements PurchaseDataService {

    @Autowired
    Retrofit retrofit;

    @Override
    public VoucherDataDTO purchaseData(PrepaidDataDTO prepaidDataDTO) {
        RetrofitVoucherService retrofitOrderService = retrofit.create(RetrofitVoucherService.class);
        try {
            PurchasePrepaidDataDTO data = PurchasePrepaidDataDTO.builder()
                    .phone(prepaidDataDTO.getPhone())
                    .amount(prepaidDataDTO.getAmount())
                    .build();
            VoucherDataDTO voucherDataDTO = retrofitOrderService.purchaseVoucher("vi", "123", data).execute().body();
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
