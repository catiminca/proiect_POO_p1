package platform;

import java.util.ArrayList;
import inputs.MovieInput;
public class Movie {
    private String name;
    private int year;
    private int duration;
    private ArrayList<String> genres;
    private ArrayList<String> actors;
    private ArrayList<String> countriesBanned;
    private Double rating;
    private int numLikes;
    private int numRatings;

    public Movie(MovieInput movie) {
        this.name = movie.getName();
        this.year = movie.getYear();
        this.duration = movie.getDuration();
        this.genres = movie.getGenres();
        this.actors = movie.getActors();
        this.countriesBanned = movie.getCountriesBanned();
        if(movie.getRating() == null)
            this.rating = (double) 0;
        this.numLikes = movie.getNumLikes();
        this.numRatings = movie.getNumRatings();
    }

    public Movie(Movie movie) {
        this.name = movie.getName();
        this.year = movie.getYear();
        this.duration = movie.getDuration();
        this.genres = movie.getGenres();
        this.actors = movie.getActors();
        this.countriesBanned = movie.getCountriesBanned();
        this.rating = movie.getRating();
        this.numLikes = movie.getNumLikes();
        this.numRatings = movie.getNumRatings();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(ArrayList<String> actors) {
        this.actors = actors;
    }

    public ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }

    public void setCountriesBanned(ArrayList<String> countriesBanned) {
        this.countriesBanned = countriesBanned;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(int nrLikes) {
        this.numLikes = nrLikes;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(int nrRating) {
        this.numRatings = nrRating;
    }
}
