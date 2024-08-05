package entity;

import constant.FormatMovie;

import java.time.LocalDateTime;

public class ShowTime {

    private static int AUTO_ID = 1;

    private int showtimeId;
    private FormatMovie formatMovie;
    private Movie movie;
    private Theater theater;
    private LocalDateTime movieTime;

    public ShowTime() {
    }

    public ShowTime(int showtimeId, Movie movie, Theater theater,FormatMovie formatMovie, LocalDateTime movieTime) {
        this.showtimeId = showtimeId;
        this.movie = movie;
        this.theater = theater;
        this.movieTime = movieTime;
        this.formatMovie = formatMovie;
    }

    public ShowTime(Movie movie, Theater theater, LocalDateTime movieTime) {
        this.movie = movie;
        this.theater = theater;
        this.movieTime = movieTime;
    }

    public FormatMovie getFormatMovie() {
        return formatMovie;
    }

    public void setFormatMovie(FormatMovie formatMovie) {
        this.formatMovie = formatMovie;
    }

    public int getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(int showtimeId) {
        this.showtimeId = showtimeId;
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
