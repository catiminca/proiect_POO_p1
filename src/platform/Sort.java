package platform;

import java.util.ArrayList;
import java.util.Comparator;
interface SortInterface {
    ArrayList<Movie> sortInterf(ArrayList<Movie> movies);
}
public class Sort {
    private String rating;
    private String duration;

    public Sort() {

    }

    public String getRating() {
        return this.rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * sortare in functie de nota pe care o are(rating)
     * @param movies
     * @param rating
     */
    public ArrayList<Movie> sortByRating(ArrayList<Movie> movies, String rating) {
        SortByRating sortByRating = new SortByRating(rating, movies);
        sortByRating.sortInterf(movies);
        return movies;
    }

    /**
     * sortare in functie de durata
     * @param movies
     * @param duration
     */
    public void sortByDuration(ArrayList<Movie> movies, String duration) {
        SortByDuration sortByDuration = new SortByDuration(duration, movies);
        movies = sortByDuration.sortInterf(movies);
    }

    /**
     * sortare dupa ambele criterii, folosind functiile specifice
     * pentru fiecare tip de sortare
     * @param movies
     */
    public ArrayList<Movie> sort(ArrayList<Movie> movies) {
        ArrayList<Movie> sortedMovies;
        sortedMovies = sortByRating(movies, this.rating);
        sortByDuration(sortedMovies, this.duration);
        return sortedMovies;
    }
}

class SortByRating implements SortInterface {
    private final String rating;

    public SortByRating(String rating, ArrayList<Movie> currentMovies) {
        this.rating = rating;
        ArrayList<Movie> movies = new ArrayList<>();
        movies.addAll(currentMovies);
    }

    @Override
    public ArrayList<Movie> sortInterf(ArrayList<Movie> movies) {
        for (Movie movie : movies)
            if (movie.getRating() == null)
                movie.setRating(0.0);
        if (rating.equals("increasing")) {
            movies.sort(Comparator.comparingDouble(Movie::getRating));
        } else if (rating.equals("decreasing")) {
            movies.sort(Comparator.comparingDouble(Movie::getRating).reversed());
        }
        return movies;
    }
}

class SortByDuration implements SortInterface {
    private final String duration;
    public SortByDuration(String duration, ArrayList<Movie> currentMovies) {
        this.duration = duration;
        ArrayList<Movie> movies = new ArrayList<>();
        movies.addAll(currentMovies);
    }
    @Override
    public ArrayList<Movie> sortInterf(ArrayList<Movie> movies) {
        if (duration.equals("increasing")) {
            movies.sort(Comparator.comparingDouble(Movie::getDuration));
        } else if (duration.equals("decreasing")) {
            movies.sort(Comparator.comparingDouble(Movie::getDuration).reversed());
        }
        return movies;
    }
}