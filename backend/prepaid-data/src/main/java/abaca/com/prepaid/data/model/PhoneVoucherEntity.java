package abaca.com.prepaid.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * The Class PhoneEntity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "phone_voucher")
public class PhoneVoucherEntity {

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
