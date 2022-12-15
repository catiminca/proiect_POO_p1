package inputs;

import java.util.ArrayList;


public class DataBaseInput {
    private ArrayList<UserInput> users;
    private ArrayList<MovieInput> movies;
    private ArrayList<ActionInput> actions;

    private static DataBaseInput instance = null;

    private DataBaseInput() {}

    public static DataBaseInput getInstance() {
        if (instance == null) {
            instance = new DataBaseInput();
        }
        return instance;
    }

    public ArrayList<UserInput> getUsers() {
        return this.users;
    }

    public void setUsers(ArrayList<UserInput> users) {
        this.users = users;
    }

    public ArrayList<MovieInput> getMovies() {
        return this.movies;
    }

    public void setMovies(ArrayList<MovieInput> movies) {
        this.movies = movies;
    }

    public ArrayList<ActionInput> getActions() {
        return this.actions;
    }

    public void setActions(ArrayList<ActionInput> actions) {
        this.actions = actions;
    }
}

