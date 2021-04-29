package abaca.com.prepaid.data.service;

public interface EncryptDecryptService {
    String encrypt(String strToEncrypt);

    String decrypt(String strToDecrypt);
}
