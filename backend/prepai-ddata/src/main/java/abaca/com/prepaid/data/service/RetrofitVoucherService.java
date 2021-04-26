package abaca.com.prepaid.data.service;

import abaca.com.prepaid.data.dto.PurchasePrepaidDataDTO;
import abaca.com.prepaid.data.dto.VoucherDataDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RetrofitVoucherService {
    @POST("voucher/purchase-prepaid-data")
    Call<VoucherDataDTO> purchaseVoucher(@Header("Accept-Language") String language, @Header("Accept-Token") String token,
                                         @Body PurchasePrepaidDataDTO purchasePrepaidDataDTO);
}
