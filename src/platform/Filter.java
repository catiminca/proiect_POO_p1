package platform;

import java.util.ArrayList;

public class Filter {
    private Sort sort;
    private Contains contains;
    public Filter() {
    }

    public Sort getSort() {
        return this.sort;
    }

    public Contains getContains() {
        return this.contains;
    }

    /**
     * functie pentru a verifica daca sortarea se va face dupa actori, genuri
     * de film sau ambele
     * @param movies
     */
    public ArrayList<Movie> sortedByContains(ArrayList<Movie> movies) {
        ArrayList<Movie> filtered = new ArrayList<>();
        if(this.contains.getActors() != null && this.contains.getGenre() != null)
            filtered = this.contains.sortByContains(movies);
        else if (this.contains.getActors() != null) {
            filtered = this.contains.sortByContainsActors(movies);
        } else if (this.contains.getGenre() != null)
            filtered = this.contains.sortByContainsGenre(movies);
        return filtered;
    }

    /**
     * functie cu ajutorul careia apelez functia care sorteaza atat dupa
     * nota pe care o are cat si dupa durata sa
     * @param movies
     */
    public ArrayList<Movie> sortByBoth(ArrayList<Movie> movies) {
        ArrayList<Movie> sortedMovies;
        sortedMovies = this.sort.sort(movies);
        return sortedMovies;
    }

    /**
     * se cheama functia care sorteaza dupa evaluarea(rating) pe care o au
     * filmele
     * @param movies
     * @param rating
     * @return
     */
    public ArrayList<Movie> sortByRating(ArrayList<Movie> movies, String rating) {
        ArrayList<Movie> sortedMovies;
        sortedMovies = this.sort.sortByRating(movies, rating);
        return sortedMovies;
    }

    /**
     * este apelata functia care sorteaza dupa durata filmelor
     * @param movies
     * @param duration
     */
    public void sortByDuration(ArrayList<Movie> movies, String duration) {
        this.sort.sortByDuration(movies, duration);
    }
}
