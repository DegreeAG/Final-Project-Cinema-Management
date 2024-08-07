package constant;

public enum MovieClass {
    MIDTIER("Phim phổ thông"),
    HIGHTIER("Phim chất lượng cao"),
    LUXYRYTIER("Phim bom tấn");

    private final String tier;

    public String getTier() {
        return tier;
    }

    MovieClass(String tier) {
        this.tier = tier;
    }

}
