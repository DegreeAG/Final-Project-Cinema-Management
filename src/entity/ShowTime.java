package entity;

import java.time.LocalDateTime;

public class ShowTime {

    private Movie movie;
    private Theater theater;
    private LocalDateTime movieTime;

    public ShowTime() {
    }

    public ShowTime(Movie movie, Theater theater, LocalDateTime movieTime) {
        this.movie = movie;
        this.theater = theater;
        this.movieTime = movieTime;
    }



    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Theater getTheater() {
        return theater;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
    }

    public LocalDateTime getMovieTime() {
        return movieTime;
    }

    public void setMovieTime(LocalDateTime movieTime) {
        this.movieTime = movieTime;
    }

    @Override
    public String toString() {
        return "ShowTime{" +
                "movie=" + movie +
                ", theater=" + theater +
                ", movieTime=" + movieTime +
                '}';
    }
}
