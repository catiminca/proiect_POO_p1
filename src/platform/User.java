package platform;

import java.util.ArrayList;
import inputs.MovieInput;
import inputs.UserInput;

public class User {
    private Credentials credentials;
    private int tokensCount;
    private int numFreePremiumMovies = 15;
    private ArrayList<Movie> purchasedMovies = new ArrayList<>();
    private ArrayList<Movie> watchedMovies = new ArrayList<>();
    private ArrayList<Movie> likedMovies = new ArrayList<>();
    private ArrayList<Movie> ratedMovies = new ArrayList<>();
    public User(UserInput user) {
        this.credentials = user.getCredentials();
        this.tokensCount = user.getTokensCount();
        this.numFreePremiumMovies = user.getFreeMoviesNumber();
        this.purchasedMovies = new ArrayList<>();
        for(MovieInput movie : user.getPurchasedMovies()) {
            Movie movieCop = new Movie(movie);
            this.purchasedMovies.add(movieCop);
        }
        this.watchedMovies = new ArrayList<>();
        for(MovieInput movie : user.getWatchedMovies()) {
            Movie movieCop = new Movie(movie);
            this.watchedMovies.add(movieCop);
        }
        this.likedMovies = new ArrayList<>();
        for(MovieInput movie : user.getLikedMovies()) {
            Movie movieCop = new Movie(movie);
            this.likedMovies.add(movieCop);
        }
        this.ratedMovies = new ArrayList<>();
        for(MovieInput movie : user.getRatedMovies()) {
            Movie movieCop = new Movie(movie);
            this.ratedMovies.add(movieCop);
        }
    }

    public User(User user) {
        this.credentials = new Credentials(user.getCredentials());
        this.tokensCount = user.getTokensCount();
        this.numFreePremiumMovies = user.getNumFreePremiumMovies();
        this.purchasedMovies = new ArrayList<>();
        for(Movie movie : user.getPurchasedMovies()) {
            Movie movieCop = new Movie(movie);
            this.purchasedMovies.add(movieCop);
        }
        this.watchedMovies = new ArrayList<>();
        for(Movie movie : user.getWatchedMovies()) {
            Movie movieCop = new Movie(movie);
            this.watchedMovies.add(movieCop);
        }
        this.likedMovies = new ArrayList<>();
        for(Movie movie : user.getLikedMovies()) {
            Movie movieCop = new Movie(movie);
            this.likedMovies.add(movieCop);
        }
        this.ratedMovies = new ArrayList<>();
        for(Movie movie : user.getRatedMovies()) {
            Movie movieCop = new Movie(movie);
            this.ratedMovies.add(movieCop);
        }
    }
    public User(String name, String password, String accountType, String country, String balance) {
        this.credentials = new Credentials(name, password, accountType, country, balance);
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public int getTokensCount() {
        return tokensCount;
    }

    public void setTokensCount(int numberTokens) {
        this.tokensCount = numberTokens;
    }

    public int getNumFreePremiumMovies() {
        return numFreePremiumMovies;
    }

    public void setNumFreePremiumMovies(int freeMoviesNumber) {
        this.numFreePremiumMovies = freeMoviesNumber;
    }

    public ArrayList<Movie> getPurchasedMovies() {
        return purchasedMovies;
    }

    public void setPurchasedMovies(ArrayList<Movie> purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }

    public ArrayList<Movie> getWatchedMovies() {
        return watchedMovies;
    }

    public void setWatchedMovies(ArrayList<Movie> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }

    public ArrayList<Movie> getLikedMovies() {
        return likedMovies;
    }

    public void setLikedMovies(ArrayList<Movie> likedMovies) {
        this.likedMovies = likedMovies;
    }

    public ArrayList<Movie> getRatedMovies() {
        return ratedMovies;
    }

    public void setRatedMovies(ArrayList<Movie> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }
}
