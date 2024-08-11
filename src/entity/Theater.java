package entity;

import constant.Status;

import java.time.LocalDate;
import java.util.List;

public class Theater {
    private static int AUTO_ID = 1;
    private int theaterID;
    private String theaterName;
    private LocalDate createdDate;
    private Status status;
    private List<Seat> seats;

    public Theater(int theaterID, String theaterName, LocalDate createdDate, Status status, List<Seat> seats) {
        this.theaterID = theaterID;
        this.theaterName = theaterName;
        this.createdDate = createdDate;
        this.status = status;
        this.seats = seats;
    }

    public Theater() {
        this.theaterID = AUTO_ID;
        AUTO_ID++;
    }


    public int getTheaterID() {
        return theaterID;
    }

    public void setTheaterID(int theaterID) {
        this.theaterID = theaterID;
    }

    public String getTheaterName() {
        return theaterName;
    }

    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public Status getStatus(Status active) {
        return status;
    }

    @Override
    public String toString() {
        return "Theater{" +
                "theaterID=" + theaterID +
                ", theaterName='" + theaterName + '\'' +
                ", createdDate=" + createdDate +
                ", status=" + status +
                ", seats=" + seats +
                '}';
    }
}
