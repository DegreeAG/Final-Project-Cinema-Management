package entity;

import constant.FormatMovie;
import constant.Status;

import java.text.Format;
import java.time.LocalDate;

public class Movie {
    private static int AUTO_ID = 1;

    private int id;
    private String movieName;
    private String actors;
    private MovieCategory category;
    private LocalDate publishedYear;
    private int movieTime;
    private double voteStar;
    private String language;
    private Status status;
    private int voteCount;

    public Movie(int id, String movieName, String actors, MovieCategory category, LocalDate publishedYear, int movieTime,  String language, Status status) {
        this.id = id;
        this.movieName = movieName;
        this.actors = actors;
        this.category = category;
        this.publishedYear = publishedYear;
        this.movieTime = movieTime;
        this.language = language;
        this.status = status;

    }

    public Movie() {
        this.id = AUTO_ID;
        AUTO_ID++;
    }



    public int getAutoId() {
        return AUTO_ID;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public int getMovieTime() {
        return movieTime;
    }

    public void setMovieTime(int movieTime) {
        this.movieTime = movieTime;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public MovieCategory getCategory() {
        return category;
    }

    public void setCategory(MovieCategory category) {
        this.category = category;
    }

    public double getVoteStar() {
        return voteStar;
    }

    public void setVoteStar(double voteStar) {
        this.voteStar = voteStar;
    }


    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public LocalDate getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(LocalDate publishedYear) {
        this.publishedYear = publishedYear;
    }

    public Status getStatus(Status active) {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", movieName='" + movieName + '\'' +
                ", actors='" + actors + '\'' +
                ", category=" + category +
                ", publishedYear=" + publishedYear +
                ", movieTime=" + movieTime +
                ", voteStar=" + voteStar +
                ", language='" + language + '\'' +
                ", status=" + status +
                ", voteCount=" + voteCount +
                '}';
    }
}
