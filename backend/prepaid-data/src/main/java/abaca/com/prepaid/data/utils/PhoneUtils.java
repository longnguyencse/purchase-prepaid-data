package abaca.com.prepaid.data.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;
import java.util.regex.Pattern;

@Slf4j
public class PhoneUtils {
  private static final Pattern CHECK_PHONE_NUMBER_PATTERN = Pattern.compile("^[0][1-9]\\d{9}$");
  
  public static String generatePhoneFormat(final String phoneStr) throws Exception {
    String result = null;
    try {
      MessageFormat phoneMsgFmt = new MessageFormat("({0})-{1}-{2}");
      String[] phoneNumArr = {phoneStr.substring(0, 3),
              phoneStr.substring(3, 6),
              phoneStr.substring(6)};
      result = phoneMsgFmt.format(phoneNumArr);
    } catch (Exception e) {
      log.error("Generate phone format failed", e);
      throw new Exception("Phone invalid");
    }
    return result;
  }
  
  public static boolean isValidPhoneNumber(final String phoneStr) {
    boolean result = false;
    if(CHECK_PHONE_NUMBER_PATTERN.matcher(phoneStr).matches()) {
      result = true;
    }
    return result;
  }
  
}
