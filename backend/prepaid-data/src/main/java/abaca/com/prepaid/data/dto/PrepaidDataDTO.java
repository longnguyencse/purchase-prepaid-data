package abaca.com.prepaid.data.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrepaidDataDTO {
    private String phone;
    private BigDecimal amount;
}
