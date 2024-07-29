package Entity;

import Constant.Status;

public class SeatDetails {

    private int row;
    private int seatNumber;
    private Status status;
    private Theater theater;

    public SeatDetails(int row, int seatNumber, Status status, Theater theater) {
        this.row = row;
        this.seatNumber = seatNumber;
        this.status = status;
        this.theater = theater;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Theater getTheater() {
        return theater;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
    }

    @Override
    public String toString() {
        return "SeatDetails{" +
                "row=" + row +
                ", seatNumber=" + seatNumber +
                ", status=" + status +
                ", theater=" + theater +
                '}';
    }
}
