package entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class VoteHistory {

    private User user;
    private Movie movie;
    private String content;
    private LocalDate createdDate;
    private double voteStarHistory;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public double getVoteStarHistory() {
        return voteStarHistory;
    }

    public void setVoteStarHistory(double voteStarHistory) {
        this.voteStarHistory = voteStarHistory;
    }

    public VoteHistory() {
    }

    public VoteHistory(User user, Movie movie, String content, double voteStarHistory) {
        this.user = user;
        this.movie = movie;
        this.content = content;
        this.createdDate = LocalDate.now();
        this.voteStarHistory = voteStarHistory;
    }

    @Override
    public String toString() {
        return "VoteHistory{" +
                "user=" + user +
                ", movie=" + movie +
                ", content='" + content + '\'' +
                ", createdDate=" + createdDate +
                ", voteStarHistory=" + voteStarHistory +
                '}';
    }
}
