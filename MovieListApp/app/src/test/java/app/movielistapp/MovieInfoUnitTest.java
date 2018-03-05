package app.movielistapp;

import org.junit.Test;

import static org.junit.Assert.*;

public class MovieInfoUnitTest {
    @Test
    public void createMovieInfo_isCorrect() throws Exception {
        String name = "name";
        String genre = "genre";
        String releaseDate = "releaseDate";
        String overview = "overview";
        String imageLink = "imageLink";
        MovieInfo movie = new MovieInfo(name, genre, releaseDate, overview, imageLink);
        assertEquals(name, movie.getName());
        assertEquals(genre, movie.getGenre());
        assertEquals(releaseDate, movie.getReleaseDate());
        assertEquals(overview, movie.getOverview());
        assertEquals(imageLink, movie.getImageLink());
    }
}