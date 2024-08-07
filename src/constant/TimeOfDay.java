package constant;

public enum TimeOfDay {

    MORNING("Sáng"),
    AFTERNOON("Trưa"),
    EVENING("Tối"),
    LATENIGHT("Đêm khuya");

    private final String time ;

    public String getTime() {
        return time;
    }

    TimeOfDay(String time) {
        this.time = time;
    }

}
