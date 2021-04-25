package abaca.com.voucher.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoucherDataDTO {
    private String phone;
    private String voucherCode;
    private BigDecimal amount;
    private String currency;
}
