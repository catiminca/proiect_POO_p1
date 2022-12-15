package inputs;

import platform.Credentials;

import java.util.ArrayList;

public class UserInput {
    private Credentials credentials;
    private int tokensCount;
    private int freeMoviesNumber = 15;
    private ArrayList<MovieInput> purchasedMovies = new ArrayList<>();
    private ArrayList<MovieInput> watchedMovies = new ArrayList<>();
    private ArrayList<MovieInput> likedMovies = new ArrayList<>();
    private ArrayList<MovieInput> ratedMovies = new ArrayList<>();

    public UserInput() {
    }

    public Credentials getCredentials() {
        return this.credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public int getTokensCount() {
        return this.tokensCount;
    }

    public void setTokensCount(int tokensCount) {
        this.tokensCount = tokensCount;
    }

    public int getFreeMoviesNumber() {
        return this.freeMoviesNumber;
    }

    public ArrayList<MovieInput> getPurchasedMovies() {
        return this.purchasedMovies;
    }

    public void setPurchasedMovies(ArrayList<MovieInput> purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }

    public ArrayList<MovieInput> getWatchedMovies() {
        return this.watchedMovies;
    }

    public void setWatchedMovies(ArrayList<MovieInput> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }

    public ArrayList<MovieInput> getLikedMovies() {
        return this.likedMovies;
    }

    public void setLikedMovies(ArrayList<MovieInput> likedMovies) {
        this.likedMovies = likedMovies;
    }

    public ArrayList<MovieInput> getRatedMovies() {
        return this.ratedMovies;
    }

    public void setRatedMovies(ArrayList<MovieInput> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }
}

