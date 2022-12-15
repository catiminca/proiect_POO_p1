package platform;

import java.util.ArrayList;

public class OutCommand {
    private String error;
    private ArrayList<Movie> currentMoviesList;
    private User currentUser;

//    public static class Builder {
//        private String error;
//        private ArrayList<Movie> currentMovieList;
//        private User currentUser;

//        public Builder(String error) {
//            this.error = error;
//        }
//
//        public Builder currentMovieList(ArrayList<Movie> currentMovieList) {
//            this.currentMovieList = new ArrayList<>();
//            for (Movie value : currentMovieList) {
//                Movie movie = new Movie(value);
//                this.currentMovieList.add(movie);
//            }
//            return this;
//        }
//
//        public Builder currentUser(User currentUser) {
//            this.currentUser = new User(currentUser);
//            return this;
//        }
//        public OutCommand build() {
//            return new OutCommand(this);
//        }
//    }
//
//    private OutCommand(Builder builder) {
//        this.error = builder.error;
//        this.currentMoviesList = new ArrayList<>();
//                builder.currentMovieList;
//        this.currentUser = builder.currentUser;
//    }
    public OutCommand(String error, ArrayList<Movie> currentMovieList, User currentUser) {
        this.error = error;
        this.currentMoviesList = new ArrayList<>();
        for (Movie value : currentMovieList) {
            Movie movie = new Movie(value);
            this.currentMoviesList.add(movie);
        }

        this.currentUser = new User(currentUser);
    }

    public OutCommand(String error, User currentUser) {
        this.error = error;
        this.currentMoviesList = new ArrayList<>();
        this.currentUser = new User(currentUser);
    }
    public OutCommand(String error) {
        this.error = error;
        this.currentMoviesList = new ArrayList<>();
        this.currentUser = null;

    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ArrayList<Movie> getCurrentMoviesList() {
        return currentMoviesList;
    }

    public void setCurrentMoviesList(ArrayList<Movie> currentMovieList) {
        this.currentMoviesList = currentMovieList;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

}
