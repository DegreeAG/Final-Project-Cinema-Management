package constant;

public enum FormatMovie {
    TWO_DIMENSION("2D"),
    THREE_DIMENSION("3D"),
    FOUR_DIMENSION_X("4DX"),
    IMAX("IMAX");

    private final String format;

    private String getFormat() {
        return format;
    }

    FormatMovie(String format) {
        this.format = format;
    }



}
