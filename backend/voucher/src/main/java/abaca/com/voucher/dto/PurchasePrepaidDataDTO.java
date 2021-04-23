package abaca.com.voucher.dto;

import abaca.com.voucher.constant.GSMType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class PurchasePrepaidDataDTO {
    private GSMType gsmType;
    private BigDecimal amount;
}
