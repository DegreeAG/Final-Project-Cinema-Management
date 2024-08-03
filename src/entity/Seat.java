package entity;

import constant.SeatClass;
import constant.Status;

public class Seat {

    private int seatNumber ;
    private String row;
    private Status status;
    private SeatClass seatClass;

    public Seat(int seatNumber, String row, Status status, SeatClass seatClass) {
        this.seatNumber = seatNumber;
        this.row = row;
        this.status = status;
        this.seatClass = seatClass;
    }


    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }


    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public SeatClass getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(SeatClass seatClass) {
        this.seatClass = seatClass;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "seatNumber=" + seatNumber +
                ", row='" + row + '\'' +
                ", status=" + status +
                ", seatClass=" + seatClass +
                '}';
    }
}
