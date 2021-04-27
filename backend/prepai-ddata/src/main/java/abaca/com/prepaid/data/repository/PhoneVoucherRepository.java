package abaca.com.prepaid.data.repository;

import abaca.com.prepaid.data.model.PhoneVoucherEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhoneVoucherRepository extends JpaRepository<PhoneVoucherEntity, Long> {

  /**
   * @param phoneStr
   * @return
   */
  PhoneVoucherEntity findByPhoneNumber(String phoneStr);

  Optional<Page<PhoneVoucherEntity>> getAllByPhoneNumber(String PhoneStr, Pageable pageable);

}
