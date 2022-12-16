package platform;

import java.util.ArrayList;
import inputs.DataBaseInput;
public class DataBase {
    private final ArrayList<User> allusers;
    private final ArrayList<Movie> allmovies;
    private final ArrayList<Actions> allActions;
    public DataBase(final DataBaseInput dataBaseInput) {
        this.allusers = new ArrayList<>();
        for (int i = 0; i < dataBaseInput.getUsers().size(); i++) {
            User user = new User(dataBaseInput.getUsers().get(i));
            this.allusers.add(user);
        }
        this.allmovies = new ArrayList<>();
        for (int i = 0; i < dataBaseInput.getMovies().size(); i++) {
            Movie movie = new Movie(dataBaseInput.getMovies().get(i));
            this.allmovies.add(movie);
        }
        this.allActions = new ArrayList<>();
        for (int i = 0; i < dataBaseInput.getActions().size(); i++) {
            Actions actions = new Actions(dataBaseInput.getActions().get(i));
                this.allActions.add(actions);
        }
    }

    public DataBase(final DataBase dataBase) {
        this.allusers = new ArrayList<>();
        for (int i = 0; i < dataBase.getAllusers().size(); i++) {
            User user = new User(dataBase.getAllusers().get(i));
            this.allusers.add(user);
        }
        this.allmovies = new ArrayList<>();
        for (int i = 0; i < dataBase.getAllmovies().size(); i++) {
            Movie movie = new Movie(dataBase.getAllmovies().get(i));
            this.allmovies.add(movie);
        }
        this.allActions = new ArrayList<>();
        for (int i = 0; i < dataBase.getAllActions().size(); i++) {
            Actions actions = new Actions(dataBase.getAllActions().get(i));
            this.allActions.add(actions);
        }
    }

    /**
     * @return
     */
    public ArrayList<User> getAllusers() {
        return allusers;
    }

    /**
     * @return
     */
    public ArrayList<Movie> getAllmovies() {
        return allmovies;
    }

    /**
     * @return
     */
    public ArrayList<Actions> getAllActions() {
        return allActions;
    }

    /**
     * functie pentru adaugarea unui nou utilizator in momentul
     * creearii contului sau
     * @param user
     */
    public void addWhenRegister(final User user) {
        this.allusers.add(user);
    }
}
