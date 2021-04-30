package abaca.com.prepaid.data.repository;

import abaca.com.prepaid.data.model.PhoneVoucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneVoucherRepository extends JpaRepository<PhoneVoucher, Long> {
    Page<PhoneVoucher> findByPhoneNumber(String phoneNumber, Pageable pageable);
}
