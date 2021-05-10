package abaca.com.prepaid.data.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * The Class PhoneEntity.
 */

@Entity
@Table(name = "phone_voucher")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneVoucher implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "status")
    private Short status;

    @Column(name = "voucher_code")
    private String voucherCode;

    @Column(name = "voucher_amount", columnDefinition = "decimal(20,2)")
    private BigDecimal voucherAmount;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "transmission_time")
    private LocalDateTime transmissionTime;

}
