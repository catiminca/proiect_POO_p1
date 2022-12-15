package platform;

import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;
import java.util.Objects;
import java.lang.String;

public class CurrentPage {
    private User user;
    private String currentPage;
    private String name;
    private ArrayList<Movie> currentMovieList;
    private final CurrentMovieList currentList;
    private DataBase dataBase;
    private final GetCurrentPage getCurrentPage;
    private int succes;
    private User newuser;
    private String movie;
    private int succespage;
    private int purchaseOk;
    private int watchOk;
    public CurrentPage(String pageName, DataBase database, String nameprev, CurrentUser currentUser, CurrentMovieList currentMovieList) {

        if (currentUser != null) {
            this.user = new User(currentUser.user());
        } else
            this.user = null;
        if(this.dataBase == null)
            this.dataBase = new DataBase(database);
        this.name = pageName;
        this.getCurrentPage = new GetCurrentPage(nameprev);
        if(currentMovieList != null) {
            this.currentList = new CurrentMovieList(currentMovieList.getMovielist());
        } else {
            this.currentList = new CurrentMovieList();
        }
    }

    public void changePage(Actions actions, ArrayNode output, CurrentUser currentUser) {
        if (actions.getPage().equals("register")) {
            register(output, currentUser);
            return;
        }
        if (actions.getPage().equals("login")) {
           login(currentUser, output);
            return;
        }
        if (actions.getPage().equals("logout")) {
            logout(currentUser, output);
            return;
        }
        if (actions.getPage().equals("movies")) {
            movies(currentUser, output);
            return;
        }
        if (actions.getPage().equals("see details")) {
            seeDetails(actions, output);
            return;
        }
        if (actions.getPage().equals("upgrades")) {
            upgrades(output);
        }
    }

    public void login(CurrentUser currentUser, ArrayNode output) {
        if (currentUser == null && getCurrentPage.getCurrent().equals("homepage")) {
            this.getCurrentPage.setCurrent("login");
            this.getCurrentPage.setLastPage("homepage");
        } else {
            OutCommand outCommand = new OutCommand("Error");
            output.addPOJO(outCommand);
        }
    }

    public void logout(CurrentUser currentUser, ArrayNode output) {
        this.succespage = 0;
        if (currentUser != null) {
            this.currentPage = "homepage";
            this.user = null;
            this.succespage = 1;
        } else {
            OutCommand outCommand = new OutCommand("Error");
            output.addPOJO(outCommand);
        }
    }

    public void register(ArrayNode output, CurrentUser currentUser) {
        if (currentUser == null && getCurrentPage.getCurrent().equals("homepage")) {
            this.getCurrentPage.setCurrent("register");
            this.getCurrentPage.setLastPage("homepage");
        } else {
            OutCommand outCommand = new OutCommand("Error");
            output.addPOJO(outCommand);
        }
    }

    public void movies(CurrentUser currentUser, ArrayNode output) {
        this.succespage = 0;
        OutCommand outCommand;
        if (currentUser != null) {
            this.getCurrentPage.setCurrent("movies");
            this.getCurrentPage.setLastPage(this.currentPage);
            this.currentMovieList = new ArrayList<>();
            this.currentMovieList = sortByCountry(currentUser);
            this.succespage = 1;
            newuser = new User(user);
            outCommand = new OutCommand(null, currentMovieList, newuser);
        } else {
            outCommand = new OutCommand("Error");
        }
        output.addPOJO(outCommand);
    }

    public void seeDetails(Actions actions, ArrayNode output) {
        this.succespage = 0;
        OutCommand outCommand;
        if (getCurrentPage.getCurrent().equals("movies") && currentList.getMovielist().size() > 0) {
            this.currentPage = "see details";
            int ok = 0;
            if(checkMovie(actions))
                ok = 1;
            if(this.movie == null || ok == 0) {
                outCommand = new OutCommand("Error");
                output.addPOJO(outCommand);
                return;
            } else {
                this.currentMovieList = new ArrayList<>();
                for(int i = 0; i < currentList.getMovielist().size(); i++) {
                    if (currentList.getMovielist().get(i).getName().equals(this.movie)) {
                        this.currentMovieList.add(currentList.getMovielist().get(i));
                        break;
                    }
                }
                this.succespage = 1;
                this.getCurrentPage.setLastPage("movies");
                this.getCurrentPage.setCurrent("see details");
                newuser = new User(user);
                outCommand = new OutCommand(null, currentMovieList,user);
            }
        } else {
            outCommand = new OutCommand("Error");
        }
        output.addPOJO(outCommand);
    }

    public void upgrades(ArrayNode output ) {
        if (this.user != null) {
            this.getCurrentPage.setLastPage(this.currentPage);
            this.getCurrentPage.setCurrent("upgrades");
        } else {
            OutCommand outCommand = new OutCommand("Error");
            output.addPOJO(outCommand);
        }
    }

    public int contains(String sequence, String subsequence) {
         return sequence.indexOf(subsequence);
    }

    public boolean checkMovie(Actions actions) {
        int ok = 0;
        for (int i = 0; i < currentList.getMovielist().size(); i++) {
            if (currentList.getMovielist().get(i).getName().equals(actions.getMovie())) {
                this.movie = actions.getMovie();
                ok = 1;
                break;
            }
        }
        return ok == 1;
    }
    public void executePageCommand(Actions actions, ArrayNode output, CurrentUser currentUser) {
        if (actions.getFeature().equals("register")) {
            registerAction(actions, output);
            return;
        }
        if (actions.getFeature().equals("login")) {
           loginAction(actions, output);
            return;
        }
        if (actions.getFeature().equals("search")) {
            searchAction(currentUser, actions, output);
            return;
        }
        if (actions.getFeature().equals("filter")) {
            filterAction(actions, currentUser, output);
            return;
        }

        if (actions.getFeature().equals("buy tokens")) {
            buyTokens(actions, output);
            return;
        }
        if (actions.getFeature().equals("buy premium account")) {
            buyPremiumAcc(output);
            return;
        }
        if (actions.getFeature().equals("purchase")) {
            purchase(currentUser, output);
            return;
        }
        if (actions.getFeature().equals("watch")) {
            watch(output);
            return;
        }
        if (actions.getFeature().equals("like")) {
            like(output);
            return;
        }
        if (actions.getFeature().equals("rate")) {
            rate(actions, output);
        }
    }

    public void registerAction(Actions actions, ArrayNode output) {
        OutCommand outCommand = null;
        this.succes = 0;
        if (user == null) {
            int ok = 0;
            for (int i = 0; i < dataBase.getAllusers().size(); i++) {
                if (Objects.equals(actions.getCredentials().getName(), dataBase.getAllusers().get(i).getCredentials().getName())) {
                    ok = 1;
                    outCommand = new OutCommand("Error");
                    break;
                }
            }
            if (ok == 0) {
                this.newuser = new User(actions.getCredentials().getName(), actions.getCredentials().getPassword(),
                        actions.getCredentials().getAccountType(), actions.getCredentials().getCountry(),
                        actions.getCredentials().getBalance());
                this.user = new User(newuser);
                dataBase.addWhenRegister(user);
                this.getCurrentPage.setCurrent("register");
                this.getCurrentPage.setLastPage("homepage");
                this.succes = 1;
                newuser = new User(user);
                if (currentMovieList == null)
                    outCommand = new OutCommand(null, newuser);
            }
        } else {
            outCommand = new OutCommand("Error");
        }
        output.addPOJO(outCommand);
    }

    public void loginAction(Actions actions, ArrayNode output) {
        OutCommand outCommand = null;
        this.succes = 0;
        if (user == null) {
            int ok = 0;
            for (int i = 0; i < dataBase.getAllusers().size(); i++) {
                if (Objects.equals(actions.getCredentials().getName(), dataBase.getAllusers().get(i).getCredentials().getName()) &&
                        Objects.equals(actions.getCredentials().getPassword(), dataBase.getAllusers().get(i).getCredentials().getPassword())) {
                    this.newuser = new User(dataBase.getAllusers().get(i).getCredentials().getName(),
                            dataBase.getAllusers().get(i).getCredentials().getPassword(),
                            dataBase.getAllusers().get(i).getCredentials().getAccountType(),
                            dataBase.getAllusers().get(i).getCredentials().getCountry(),
                            dataBase.getAllusers().get(i).getCredentials().getBalance());
                    this.user = new User(newuser);
                    this.getCurrentPage.setCurrent("login");
                    this.getCurrentPage.setLastPage("homepage");
                    this.succes = 1;
                    if (currentMovieList == null) {
                        newuser = new User(user);
                        outCommand = new OutCommand(null,newuser);
                    }
                    ok = 1;
                    break;
                }
            }
            if (ok == 0) {
                outCommand = new OutCommand("Error");
            }
        } else {
            outCommand = new OutCommand("Error");
        }
        output.addPOJO(outCommand);
    }

    public void searchAction(CurrentUser currentUser, Actions actions, ArrayNode output) {
        OutCommand outCommand;
        this.succes = 0;
        this.currentMovieList = new ArrayList<>();
        if (getCurrentPage.getCurrent().equals("movies")) {
            ArrayList<Movie> allMovies = new ArrayList<>();
            for (int i = 0; i < dataBase.getAllmovies().size(); i++) {
                if (contains(dataBase.getAllmovies().get(i).getName(), actions.getStartWith()) == 0) {
                    if(countryInvertCheck(currentUser, i))
                        allMovies.add(dataBase.getAllmovies().get(i));
                }
            }
            this.currentMovieList.addAll(allMovies);
            this.succes = 1;
            newuser = new User(user);
            outCommand = new OutCommand(null, currentMovieList, newuser);

        } else {
            outCommand = new OutCommand("Error");
        }
        output.addPOJO(outCommand);
    }

    public void filterAction(Actions actions, CurrentUser currentUser, ArrayNode output) {
        OutCommand outCommand = null;
        this.succes = 0;
        this.currentMovieList = new ArrayList<>();
        if (getCurrentPage.getCurrent().equals("movies")) {
            this.succes = 1;
            this.currentMovieList = sortByCountry(currentUser);
            Filter filter = actions.getFilters();
            if (filter.getContains() != null) {
                currentMovieList = filter.sortedByContains(currentMovieList);
            }
            if (filter.getSort() != null) {
                if (filter.getSort().getRating() != null && filter.getSort().getDuration() != null) {
                    currentMovieList = filter.sortByBoth(currentMovieList);
                } else if (filter.getSort().getRating() != null) {
                    currentMovieList = filter.sortByRating(currentMovieList, filter.getSort().getRating());
                } else if (filter.getSort().getDuration() != null) {
                    filter.sortByDuration(currentMovieList, filter.getSort().getDuration());
                }
            }
            if (filter.getSort() != null || filter.getContains() != null) {
                newuser = new User(user);
//                if (currentMovieList == null) {
//                    outCommand = new OutCommand(null, newuser);
//                } else {
                outCommand = new OutCommand(null, currentMovieList, newuser);
            } else if (filter.getSort() == null || filter.getContains() == null) {
                outCommand = new OutCommand("Error");
            }
        } else {
            outCommand = new OutCommand("Error");
        }
        output.addPOJO(outCommand);
    }

    public void buyTokens(Actions actions, ArrayNode output) {
        OutCommand outCommand;
        this.succes = 0;
        if (getCurrentPage.getCurrent().equals("upgrades")) {
            user.setTokensCount(Integer.parseInt(user.getTokensCount() + actions.getCount()));
            int balance = Integer.parseInt(user.getCredentials().getBalance()) - Integer.parseInt(actions.getCount());
            user.getCredentials().setBalance(String.valueOf(balance));
            this.succes = 1;
        } else {
            outCommand = new OutCommand("Error");
            output.addPOJO(outCommand);
        }
    }

    public void buyPremiumAcc(ArrayNode output) {
        OutCommand outCommand;
        this.succes = 0;
        if (getCurrentPage.getCurrent().equals("upgrades")) {
            user.getCredentials().setAccountType("premium");
            user.setTokensCount(user.getTokensCount() - 10);
            this.succes = 1;
        } else {
            outCommand = new OutCommand("Error");
            output.addPOJO(outCommand);
        }
    }

    public void purchase(CurrentUser currentUser, ArrayNode output) {
        OutCommand outCommand;
        this.purchaseOk = 0;
        this.succes = 0;
        if(getCurrentPage.getCurrent().equals("see details")) {
            if(user.getTokensCount() >= 0) {
                int ok = 0;
                this.currentMovieList = new ArrayList<>();
                for (int i = 0; i < dataBase.getAllmovies().size(); i++) {
                    if (dataBase.getAllmovies().get(i).getName().equals(this.movie)) {
                        if (countryInvertCheck(currentUser, i)) {
                            this.currentMovieList.add(dataBase.getAllmovies().get(i));
                            ok = 1;
                            break;
                        }
                    }
                }
                if (ok == 1) {
                    this.purchaseOk = 1;
                    this.succes = 1;
                    if (this.user.getPurchasedMovies().size() == 0) {
                        this.user.setPurchasedMovies(currentMovieList);
                        for (int i = 0; i < dataBase.getAllusers().size(); i++) {
                            if (dataBase.getAllusers().get(i).getCredentials().getName().equals(this.user.getCredentials().getName())) {
                                dataBase.getAllusers().get(i).setPurchasedMovies(currentMovieList);
                                setTokensCountByUser(i);
                            }

                        }
                    } else {
                        this.user.getPurchasedMovies().add(currentMovieList.get(0));
                        for (int i = 0; i < dataBase.getAllusers().size(); i++) {
                            if (dataBase.getAllusers().get(i).getCredentials().getName().equals(this.user.getCredentials().getName())) {
                                dataBase.getAllusers().get(i).getPurchasedMovies().add(currentMovieList.get(0));
                                setTokensCountByUser(i);
                            }
                        }
                    }
                    if (user.getCredentials().getAccountType().equals("premium")) {
                        if (user.getNumFreePremiumMovies() == 0) {
                            user.setTokensCount(Math.max(user.getTokensCount() - 2, 0));
                        }
                        user.setNumFreePremiumMovies(Math.max(user.getNumFreePremiumMovies() - 1, 0));
                    } else {
                        user.setTokensCount(Math.max(user.getTokensCount() - 2, 0));
                    }
                    newuser = new User(user);
                    outCommand = new OutCommand(null, currentMovieList, newuser);

                } else {
                    outCommand = new OutCommand("Error");
                }
            } else {
                outCommand = new OutCommand("Error");
            }
        } else {
            outCommand = new OutCommand("Error");
        }
        output.addPOJO(outCommand);
    }

    public void watch(ArrayNode output) {
        OutCommand outCommand;
        int ok = 0;
        this.watchOk = 0;
        this.succes = 0;
        if(this.purchaseOk == 1) {
            if (user.getPurchasedMovies().size() > 0) {
                for (int i = 0; i < user.getPurchasedMovies().size(); i++) {
                    if (user.getPurchasedMovies().get(i).getName().equals(this.movie)) {
                        ok = 1;
                        break;
                    }
                }
                if (ok == 1) {
                    this.watchOk = 1;
                    if (this.user.getWatchedMovies().size() == 0) {
                        this.user.setWatchedMovies(currentMovieList);
                        for (int i = 0; i < dataBase.getAllusers().size(); i++) {
                            if (dataBase.getAllusers().get(i).getCredentials().getName().equals(this.user.getCredentials().getName())) {
                                dataBase.getAllusers().get(i).setWatchedMovies(currentMovieList);
                            }
                        }
                    } else {
                        this.user.getWatchedMovies().add(currentMovieList.get(0));
                        for (int i = 0; i < dataBase.getAllusers().size(); i++) {
                            if (dataBase.getAllusers().get(i).getCredentials().getName().equals(this.user.getCredentials().getName())) {
                                dataBase.getAllusers().get(i).getWatchedMovies().add(currentMovieList.get(0));
                            }
                        }
                    }
                    this.succes = 1;
                    newuser = new User(user);
//                    if (currentMovieList == null) {
//                        outCommand = new OutCommand(null, newuser);
//                    } else {
                    outCommand = new OutCommand(null, currentMovieList, newuser);
                } else {
                    outCommand = new OutCommand("Error");
                }

            } else {
                outCommand = new OutCommand("Error");
            }
        } else {
            outCommand = new OutCommand("Error");
        }
        output.addPOJO(outCommand);
    }

    public void like(ArrayNode output) {
        this.succes = 0;
        OutCommand outCommand;
        if(this.purchaseOk == 1 && this.watchOk == 1) {
            int ok = 1;
            if (user.getPurchasedMovies().size() > 0 && user.getWatchedMovies().size() > 0) {
                for (int i = 0; i < user.getPurchasedMovies().size(); i++) {
                    if (user.getPurchasedMovies().get(i).getName().equals(this.movie)) {
                        if (user.getWatchedMovies().get(i).getName().equals(this.movie)) {
                            ok = 0;
                            break;
                        }
                    }
                }
                if (ok == 0) {
                    if (this.user.getLikedMovies().size() == 0) {
                        this.user.setLikedMovies(currentMovieList);
                        for (int i = 0; i < dataBase.getAllusers().size(); i++) {
                            if (dataBase.getAllusers().get(i).getCredentials().getName().equals(this.user.getCredentials().getName())) {
                                dataBase.getAllusers().get(i).setLikedMovies(currentMovieList);
                            }
                        }
                        setNumLikes();
                    } else {
                        this.user.getLikedMovies().add(currentMovieList.get(0));
                        for (int i = 0; i < dataBase.getAllusers().size(); i++) {
                            if (dataBase.getAllusers().get(i).getCredentials().getName().equals(this.user.getCredentials().getName())) {
                                dataBase.getAllusers().get(i).getLikedMovies().add(currentMovieList.get(0));
                            }
                        }
                        setNumLikes();
                    }
                    this.succes = 1;
                    newuser = new User(user);
//                    if (currentMovieList == null) {
//                        outCommand = new OutCommand(null, newuser);
//                    } else {
                    outCommand = new OutCommand(null, currentMovieList, newuser);

                } else {
                    outCommand = new OutCommand("Error");
                }

            } else {
                outCommand = new OutCommand("Error");
            }
        } else {
            outCommand = new OutCommand("Error");
        }
        output.addPOJO(outCommand);
    }

    public void rate(Actions actions, ArrayNode output) {
        OutCommand outCommand;
        this.succes = 0;
        if(this.purchaseOk == 1 && this.watchOk == 1 && actions.getRate() <= 5 && actions.getRate() >= 1) {
            int ok = 1;
            if (user.getPurchasedMovies().size() > 0 && user.getWatchedMovies().size() > 0) {
                for (int i = 0; i < user.getPurchasedMovies().size(); i++) {
                    if (user.getPurchasedMovies().get(i).getName().equals(this.movie)) {
                        for (int j = 0; j < user.getWatchedMovies().size(); i++)
                            if (user.getWatchedMovies().get(i).getName().equals(this.movie)) {
                                ok = 0;
                                break;
                            }
                    }
                }
                if (ok == 0) {
                    if (user.getRatedMovies().size() == 0) {
                        this.user.setRatedMovies(currentMovieList);
                        for (int i = 0; i < dataBase.getAllusers().size(); i++) {
                            if (dataBase.getAllusers().get(i).getCredentials().getName().equals(this.user.getCredentials().getName())) {
                                dataBase.getAllusers().get(i).setRatedMovies(currentMovieList);
                                setRatingByUser(actions, i);
                            }
                        }
                    } else {
                        this.user.getRatedMovies().add(currentMovieList.get(0));
                        for (int i = 0; i < dataBase.getAllusers().size(); i++) {
                            if (dataBase.getAllusers().get(i).getCredentials().getName().equals(this.user.getCredentials().getName())) {
                                dataBase.getAllusers().get(i).getRatedMovies().add(currentMovieList.get(0));
                                setRatingByUser(actions, i);
                            }
                        }
                    }
                    for (int i = 0; i < dataBase.getAllmovies().size(); i++) {
                        if (dataBase.getAllmovies().get(i).getName().equals(this.movie)) {
                            dataBase.getAllmovies().get(i).setNumRatings(dataBase.getAllmovies().get(i).getNumRatings() + 1);
                            Double rating = calculateRating(this.movie);
                            dataBase.getAllmovies().get(i).setRating(rating);
                            for (Movie value : this.currentMovieList)
                                value.setNumLikes(dataBase.getAllmovies().get(i).getNumLikes());
                        }
                    }
                    this.succes = 1;
                    newuser = new User(user);
                    if (currentMovieList == null) {
                        outCommand = new OutCommand(null, newuser);
                    } else {
                        outCommand = new OutCommand(null, currentMovieList, newuser);
                    }
                } else {
                    outCommand = new OutCommand("Error");
                }

            } else {
                outCommand = new OutCommand("Error");
            }
        } else {
            outCommand = new OutCommand("Error");
        }
        output.addPOJO(outCommand);
    }

    private void setRatingByUser(Actions actions, int i) {
        if (dataBase.getAllusers().get(i).getRatedMovies().size() > 0) {
            for (int j = 0; j < dataBase.getAllusers().get(i).getRatedMovies().size(); j++)
                dataBase.getAllusers().get(i).getRatedMovies().get(j).setRating(actions.getRate());
        } else {
            dataBase.getAllusers().get(i).getRatedMovies().get(0).setRating(actions.getRate());
        }
    }

    private void setTokensCountByUser(int i) {
        if (dataBase.getAllusers().get(i).getCredentials().getAccountType().equals("premium")) {
            if (dataBase.getAllusers().get(i).getNumFreePremiumMovies() == 0) {
                dataBase.getAllusers().get(i).setTokensCount(Math.max(dataBase.getAllusers().get(i).getTokensCount() - 2, 0));
            }
            dataBase.getAllusers().get(i).setNumFreePremiumMovies(Math.max(dataBase.getAllusers().get(i).getNumFreePremiumMovies() - 1, 0));
        } else {
            dataBase.getAllusers().get(i).setTokensCount(Math.max(dataBase.getAllusers().get(i).getTokensCount() - 2, 0));
        }
    }

    private void setNumLikes() {
        for (int i = 0; i < dataBase.getAllmovies().size(); i++) {
            if (dataBase.getAllmovies().get(i).getName().equals(this.movie)) {
                dataBase.getAllmovies().get(i).setNumLikes(dataBase.getAllmovies().get(i).getNumLikes() + 1);
                for (Movie value : this.currentMovieList)
                    value.setNumLikes(dataBase.getAllmovies().get(i).getNumLikes());
            }
        }
    }

    private boolean countryInvertCheck(CurrentUser currentUser, int index) {
        for (int j = 0; j < dataBase.getAllmovies().get(index).getCountriesBanned().size(); j++) {
            if (currentUser.user().getCredentials().getCountry().contains(dataBase.getAllmovies().get(index).getCountriesBanned().get(j))) {
                return false;
            }
        }
        return true;
    }

    private Double calculateRating(String name) {
        int ratings = 0, numrating = 0;
        double total = 0.0;
        for(int i = 0; i < dataBase.getAllusers().size(); i++) {
            for (int j = 0; j < dataBase.getAllusers().get(i).getRatedMovies().size(); j++) {
                if (dataBase.getAllusers().get(i).getRatedMovies().get(j).getName().equals(name)) {
                    ratings += dataBase.getAllusers().get(i).getRatedMovies().get(j).getRating();
                    numrating++;
                }
            }
        }
        if(numrating != 0)
            total = (double) ratings / numrating;
        return total;
    }
    private ArrayList<Movie> sortByCountry(CurrentUser currentUser) {
        ArrayList<Movie> allmovies = new ArrayList<>();
        for(int i = 0; i < dataBase.getAllmovies().size(); i++) {
                if (countryInvertCheck(currentUser, i)) {
                    Movie movie = new Movie(dataBase.getAllmovies().get(i));
                    allmovies.add(movie);
                }
        }
        return allmovies;
    }

    public ArrayList<Movie> getCurrentMovieList() {
        return currentMovieList;
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public GetCurrentPage getGetCurrentPage() {
        return getCurrentPage;
    }

    public int getSucces() {
        return succes;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public int getSuccespage() {
        return succespage;
    }

}
