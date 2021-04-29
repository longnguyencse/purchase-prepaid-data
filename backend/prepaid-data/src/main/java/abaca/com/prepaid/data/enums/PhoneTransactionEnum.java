package abaca.com.prepaid.data.enums;

/**
 * The Enum PhoneTransactionEnum.
 */
public enum PhoneTransactionEnum {

    /**
     * The preparing voucher data.
     */
    PREPARING_VOUCHER_DATA((short) 1, "Preparing data"),

    /**
     * The success.
     */
    SUCCESS((short) 2, "Success"),
    /**
     * The failed.
     */
    FAILED((short) 3, "Failed");

    /**
     * The value.
     */
    private final short value;

    /**
     * The text.
     */
    private final String text;

    /**
     * Instantiates a new phone transaction enum.
     *
   * @param value the value
   * @param text the text
   */
  PhoneTransactionEnum(final short value, final String text) {
    this.value = value;
    this.text = text;
  }

  /**
   * From value.
   *
   * @param value the value
   * @return the phone transaction enum
   */
  public static PhoneTransactionEnum fromValue(final int value) {
    for (final PhoneTransactionEnum status : PhoneTransactionEnum.values()) {
      if (status.value == value) {
        return status;
      }
    }
    return null;
  }

  /**
   * From text.
   *
   * @param text the text
   * @return the phone transaction enum
   */
  public static PhoneTransactionEnum fromText(final String text) {
    for (final PhoneTransactionEnum status : PhoneTransactionEnum.values()) {
      if (status.text.equalsIgnoreCase(text)) {
        return status;
      }
    }
    return null;
  }

  /**
   * Gets the value.
   *
   * @return the value
   */
  public short getValue() {
    return value;
  }

  /**
   * Gets the text.
   *
   * @return the text
   */
  public String getText() {
    return text;
  }
}
