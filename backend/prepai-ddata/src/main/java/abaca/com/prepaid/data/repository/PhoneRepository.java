package abaca.com.prepaid.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import abaca.com.prepaid.data.model.PhoneEntity;

@Repository
public interface PhoneRepository extends JpaRepository<PhoneEntity, Long>{

  /**
   * @param phoneStr
   * @return
   */
  PhoneEntity findByPhoneNumber(String phoneStr);

}
