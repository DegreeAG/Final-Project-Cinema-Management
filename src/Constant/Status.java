package Constant;

public enum Status {
    ACT("Active"),
    INA("Inactive"),
    MAIN("Maintaining");

    private final String value;

    public String getValue() {
        return value;
    }

    Status(String value) {
        this.value = value;
    }
}
