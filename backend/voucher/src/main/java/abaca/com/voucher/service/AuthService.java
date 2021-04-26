package abaca.com.voucher.service;

import abaca.com.voucher.constant.AccessType;

public interface AuthService {
    boolean isHadPermission(String token, AccessType role);
}
