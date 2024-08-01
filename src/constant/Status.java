package constant;

public enum Status {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    MAINTAINING("Maintaining");

    private final String value;

    public String getValue() {
        return value;
    }

    Status(String value) {
        this.value = value;
    }
}
