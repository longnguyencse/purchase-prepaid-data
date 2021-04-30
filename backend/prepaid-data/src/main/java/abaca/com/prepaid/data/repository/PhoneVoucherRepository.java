package abaca.com.prepaid.data.repository;

import abaca.com.prepaid.data.model.PhoneVoucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneVoucherRepository extends JpaRepository<PhoneVoucher, Long> {

    List<PhoneVoucher> getAllByPhoneNumber(String phoneStr);

    Page<PhoneVoucher> getAllByCreateTimeIsNotNull(Pageable pageable);

//  Page<PhoneVoucher> findAll(Pageable pageable);

//    List<PhoneVoucher> findALl();


}
