package abaca.com.voucher.constant;

public enum AccessType {
    ADMIN(-1),
    PUBLIC(0); // web anonymous token

    private final int accessTypeID;

    private AccessType(int accessTypeID) {
        this.accessTypeID = accessTypeID;
    }

    public int getAccessTypeID() {
        return accessTypeID;
    }
}
