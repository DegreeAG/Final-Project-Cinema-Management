package Entity;

public class TicketDetails {

    private Seat seat;
    private Movie movie;
    private int ticketNumber;

    public TicketDetails(Seat seat, Movie movie, int ticketNumber) {
        this.seat = seat;
        this.movie = movie;
        this.ticketNumber = ticketNumber;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    @Override
    public String toString() {
        return "TicketDetails{" +
                "seat=" + seat +
                ", movie=" + movie +
                ", ticketNumber=" + ticketNumber +
                '}';
    }
}
