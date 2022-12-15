package platform;

import java.util.ArrayList;


public class Contains {
    private ArrayList<String> actors;
    private ArrayList<String> genre;

    public Contains() {
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(ArrayList<String> actors) {
        this.actors = actors;
    }

    public ArrayList<String> getGenre() {
        return genre;
    }

    public void setGenre(ArrayList<String> genre) {
        this.genre = genre;
    }

    /**
     * sortarea se face atat dupa actori cat si dupa genul dat
     * @param movies
     */
    public ArrayList<Movie> sortByContains(ArrayList<Movie> movies) {
        ArrayList<Movie> allSortedMovies;
        allSortedMovies = sortByContainsActors(movies);
        ArrayList<Movie> allmovies;
        allmovies = sortByContainsGenre(allSortedMovies);
        return allmovies;

    }

    /**
     * sortare dupa actori
     * @param movies
     */
    public ArrayList<Movie> sortByContainsActors(ArrayList<Movie> movies) {
        ArrayList<Movie> allSortedMovies = new ArrayList<>();
        for (Movie movie : movies) {
            if (!this.actors.isEmpty()) {
                for (String s : this.actors) {
                    if (movie.getActors().contains(s)) {
                        allSortedMovies.add(movie);
                    }
                }
            }
        }
        return allSortedMovies;
    }

    /**
     * sortare dupa genurile filmelor
     * @param movies
     */
    public ArrayList<Movie> sortByContainsGenre(ArrayList<Movie> movies) {
        ArrayList<Movie> allSortedMovies = new ArrayList<>();
        for (Movie movie : movies) {
            if (!this.genre.isEmpty()) {
                for (String s : this.genre) {
                    if (movie.getGenres().contains(s)) {
                        allSortedMovies.add(movie);
                    }
                }
            }
        }
        return allSortedMovies;
    }
}
