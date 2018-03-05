package app.movielistapp;

import java.io.Serializable;

public class MovieInfo implements Serializable {
    private String name;
    private String genre;
    private String releaseDate;
    private String overview;
    private String imageLink;

    public MovieInfo(String name, String genre, String releaseDate, String overview, String imageLink) {
        this.name = name;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.imageLink = imageLink;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public String getImageLink() {
        return imageLink;
    }
}
