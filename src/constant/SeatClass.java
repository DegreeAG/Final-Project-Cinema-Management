package constant;

public enum SeatClass {

    STANDARD("Ghế phổ thông"),
    VIP("Ghế VIP"),
    SWEETBOX("Ghế đôi");

    private final String className;

    public String getValue() {
        return className;
    }

    SeatClass(String value) {
        this.className = value;
    }

}
