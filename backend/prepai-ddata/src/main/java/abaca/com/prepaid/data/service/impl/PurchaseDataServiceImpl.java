package abaca.com.prepaid.data.service.impl;

import abaca.com.prepaid.data.dto.PrepaidDataDTO;
import abaca.com.prepaid.data.dto.PurchasePrepaidDataDTO;
import abaca.com.prepaid.data.dto.VoucherDataDTO;
import abaca.com.prepaid.data.service.NotificationService;
import abaca.com.prepaid.data.service.PurchaseDataService;
import abaca.com.prepaid.data.service.RetrofitVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;

@Service
public class PurchaseDataServiceImpl implements PurchaseDataService {

    @Autowired
    Retrofit retrofit;

    @Autowired
    NotificationService notificationService;

    @Override
    public VoucherDataDTO purchaseData(PrepaidDataDTO prepaidDataDTO) {
        long timeStart = System.currentTimeMillis();
        RetrofitVoucherService retrofitOrderService = retrofit.create(RetrofitVoucherService.class);
        try {
            PurchasePrepaidDataDTO data = PurchasePrepaidDataDTO.builder()
                    .phone(prepaidDataDTO.getPhone())
                    .amount(prepaidDataDTO.getAmount())
                    .build();
            VoucherDataDTO voucherDataDTO = retrofitOrderService.purchaseVoucher("vi", "123", data).execute().body();
            long timeReceive = System.currentTimeMillis();
            if (timeReceive - timeStart >= 30 * 1000) {
                String msg = "";
                sendSMS(msg);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    private boolean sendSMS(String data) {
        // TODO: save db, if fail, had scheduler job send again
        return notificationService.sendSMS(data);
    }
}
