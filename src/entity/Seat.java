package entity;

import constant.SeatClass;
import constant.Status;

public class Seat {

    private static int AUTO_ID = 1;
    private int id ;
    private String row;
    private int seatNumber;
    private int seatQuantity;
    private Status status;
    private SeatClass seatClass;

    public Seat(int id, String row, int seatQuantity, Status status, SeatClass seatClass) {
        this.id = id;
        this.row = row;
        this.seatQuantity = seatQuantity;
        this.status = status;
        this.seatClass = seatClass;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public int getSeatQuantity() {
        return seatQuantity;
    }

    public void setSeatQuantity(int seatQuantity) {
        this.seatQuantity = seatQuantity;
    }

    public Seat() {
        this.id = AUTO_ID;
        AUTO_ID++;
    }

    public static int getAutoId() {
        return AUTO_ID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public Status getStatus(Status status) {
        return status;
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
                "id=" + id +
                ", row='" + row + '\'' +
                ", seatNumber=" + seatNumber +
                ", seatQuantity=" + seatQuantity +
                ", status=" + status +
                ", seatClass=" + seatClass +
                '}';
    }
}
