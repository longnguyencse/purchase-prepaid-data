package abaca.com.prepaid.data.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class VoucherEntity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="voucher")
public class VoucherEntity {

  /** The id. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** The voucher code. */
  @Column(name = "voucher_code")
  private String voucherCode;

  /** The voucher amount value. */
  @Column(name = "voucher_amount_value")
  private Integer voucherAmountValue;

  /** The create time. */
  @Column(name = "create_time")
  private Timestamp createTime;

  /** The phone entity. */
  @JoinColumn(name = "phone_id",referencedColumnName = "id", nullable = false)
  @ManyToOne
  private PhoneEntity phoneEntity;
}
