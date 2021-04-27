package abaca.com.prepaid.data.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class PhoneEntity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "phone")
public class PhoneEntity {

  /** The id. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** The phone number. */
  @Column(name = "phone_number")
  private String phoneNumber;

  /** The status. */
  @Column(name = "phone_transaction")
  private Integer phoneTransaction;

  /** The create time. */
  @Column(name = "create_time")
  private Timestamp createTime;

  /** The transmission time. */
  @Column(name = "transmission_time")
  private Timestamp transmissionTime;

  /** The voucher entity list. */
  @OneToMany(mappedBy = "phoneEntity")
  private List<VoucherEntity> voucherEntityList;
}
