package abaca.com.prepaid.data.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
