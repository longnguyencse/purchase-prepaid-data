package abaca.com.prepaid.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import abaca.com.prepaid.data.model.VoucherEntity;

@Repository
public interface VoucherRepository extends JpaRepository<VoucherEntity, Long>{

}
