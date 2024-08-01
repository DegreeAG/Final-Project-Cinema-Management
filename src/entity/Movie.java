package entity;

import java.time.LocalDate;

public class Movie {
    private static int AUTO_ID = 1;

    private int id;
    private String movieName;
    private LocalDate publishedYear;
    private int movieTime;
    private String actors;
    private String category;
    private String rated;
    private String format;
    private String language;

    public Movie() {
        this.id = AUTO_ID;
        AUTO_ID++;
    }

    public static int getAutoId() {
        return AUTO_ID;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
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

    @Override
    public String toString() {
        return "Movie{" +
                "movieID=" + id +
                ", movieName='" + movieName + '\'' +
                ", movieTime=" + movieTime +
                ", actors='" + actors + '\'' +
                ", category='" + category + '\'' +
                ", rated='" + rated + '\'' +
                ", format='" + format + '\'' +
                ", language='" + language + '\'' +
                ", publishedYear=" + publishedYear +
                '}';
    }
}
