package entity;

import java.time.LocalDateTime;

public class Ticket {
    private static int AUTO_ID = 1;
    private int id;
    private Seat seat;
    private ShowTime showTime;
    private double price;
    private User user;
    private LocalDateTime createdDateTime;
    private int ticketQuantity;
    private double totalAmount;

    public Ticket(Seat seat, ShowTime showTime, double price, User user, LocalDateTime createdDateTime, int ticketQuantity, double totalAmount) {
        this.seat = seat;
        this.showTime = showTime;
        this.price = price;
        this.user = user;
        this.createdDateTime = createdDateTime;
        this.ticketQuantity = ticketQuantity;
        this.totalAmount = totalAmount;
    }

    public Ticket(int id, Seat seat, ShowTime showTime, double price, User user, LocalDateTime createdDateTime) {
        this.id = id;
        this.seat = seat;
        this.showTime = showTime;
        this.price = price;
        this.user = user;
        this.createdDateTime = createdDateTime;
    }

    public Ticket() {
        this.id = AUTO_ID;
        AUTO_ID++;
    }

    public int getId() {
        return id;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public ShowTime getShowTime() {
        return showTime;
    }

    public void setShowTime(ShowTime showTime) {
        this.showTime = showTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public int getTicketQuantity() {
        return ticketQuantity;
    }

    public void setTicketQuantity(int ticketQuantity) {
        this.ticketQuantity = ticketQuantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "seat=" + seat +
                ", showTime=" + showTime +
                ", price=" + price +
                ", user=" + user +
                ", createdDateTime=" + createdDateTime +
                ", ticketQuantity=" + ticketQuantity +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
