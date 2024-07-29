package Entity;

public class Seat {

    private int seatQuantity;
    private Theater theater;
    private SeatDetails[] details;

    public Seat(Theater theater, int seatQuantity) {
        this.theater = theater;
        this.seatQuantity = seatQuantity;
    }

    public Seat(Theater theater, SeatDetails[] details) {
        this.theater = theater;
        this.details = details;
    }

    public Seat(int seatQuantity, SeatDetails[] details) {
        this.seatQuantity = seatQuantity;
        this.details = details;
    }

    public Seat(int seatQuantity, Theater theater, SeatDetails[] details) {
        this.seatQuantity = seatQuantity;
        this.theater = theater;
        this.details = details;
    }

    public int getSeatQuantity() {
        return seatQuantity;
    }

    public void setSeatQuantity(int seatQuantity) {
        this.seatQuantity = seatQuantity;
    }

    public Theater getTheater() {
        return theater;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
    }

    public SeatDetails[] getDetails() {
        return details;
    }

    public void setDetails(SeatDetails[] details) {
        this.details = details;
    }
}
