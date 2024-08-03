package entity;

import constant.Status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class Theater {

    private int theaterID;
    private String theaterName;
    private LocalDate createdDate;
    private Status status;
    private Seat[] seat;

    public Theater(int theaterID, String theaterName, LocalDate createdDate, Status status, Seat[] seat) {
        this.theaterID = theaterID;
        this.theaterName = theaterName;
        this.createdDate = createdDate;
        this.status = status;
        this.seat = seat;
    }

    public Theater() {

    }

    public void setTheaterID(int theaterID) {
        this.theaterID = theaterID;
    }

    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }

    public void setCreatedDate(LocalDate createdDate, DateTimeFormatter dateFormatter) {
        this.createdDate = createdDate;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setSeat(Seat[] seat) {
        this.seat = seat;
    }

    @Override
    public String toString() {
        return "Theater{" +
                "theaterID=" + theaterID +
                ", theaterName='" + theaterName + '\'' +
                ", createdDate=" + createdDate +
                ", status=" + status +
                ", seat=" + Arrays.toString(seat) +
                '}';
    }
}
