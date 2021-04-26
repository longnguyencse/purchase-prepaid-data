package abaca.com.voucher.service.impl;

import abaca.com.voucher.constant.AccessType;
import abaca.com.voucher.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public boolean isHadPermission(String token, AccessType role) {
        return true;
    }
}
