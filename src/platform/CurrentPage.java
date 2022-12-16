package platform;

import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;
import java.util.Objects;

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

    public CurrentPage(final String pageName, final DataBase database, final String nameprev,
                       final CurrentUser currentUser, final CurrentMovieList currentMovieList) {

        if (currentUser != null) {
            this.user = new User(currentUser.user());
        } else {
            this.user = null;
        }
        if (this.dataBase == null) {
            this.dataBase = new DataBase(database);
        }
        this.name = pageName;
        this.getCurrentPage = new GetCurrentPage(nameprev);
        if (currentMovieList != null) {
            this.currentList = new CurrentMovieList(currentMovieList.getMovielist());
        } else {
            this.currentList = new CurrentMovieList();
        }
    }

    /**
     * functie care schimba pagina in functie de actiunea data
     *
     * @param actions
     * @param output
     * @param currentUser
     */
    public void changePage(final Actions actions, final ArrayNode output,
                           final CurrentUser currentUser) {
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

    /**
     * functia care schimba pagina de login
     *
     * @param currentUser
     * @param output
     */
    public void login(final CurrentUser currentUser, final ArrayNode output) {
        if (currentUser == null && getCurrentPage.getCurrent().equals("homepage")) {
            this.getCurrentPage.setCurrent("login");
            this.getCurrentPage.setLastPage("homepage");
        } else {
            OutCommand outCommand = new OutCommand("Error");
            output.addPOJO(outCommand);
        }
    }

    /**
     * functia care imi face logout
     *
     * @param currentUser
     * @param output
     */
    public void logout(final CurrentUser currentUser, final ArrayNode output) {
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

    /**
     * functie pentru inregistrarea unui nou user
     *
     * @param output
     * @param currentUser
     */
    public void register(final ArrayNode output, final CurrentUser currentUser) {
        if (currentUser == null && getCurrentPage.getCurrent().equals("homepage")) {
            this.getCurrentPage.setCurrent("register");
            this.getCurrentPage.setLastPage("homepage");
        } else {
            OutCommand outCommand = new OutCommand("Error");
            output.addPOJO(outCommand);
        }
    }

    /**
     * schimba pagina actuala in cea de filme
     *
     * @param currentUser
     * @param output
     */
    public void movies(final CurrentUser currentUser, final ArrayNode output) {
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

    /**
     * schimba pagina actuala in cea de see details
     *
     * @param actions
     * @param output
     */
    public void seeDetails(final Actions actions, final ArrayNode output) {
        this.succespage = 0;
        OutCommand outCommand;
        if (getCurrentPage.getCurrent().equals("movies") && currentList.getMovielist().size() > 0) {
            this.currentPage = "see details";
            int ok = 0;
            if (checkMovie(actions)) {
                ok = 1;
            }
            if (this.movie == null || ok == 0) {
                outCommand = new OutCommand("Error");
                output.addPOJO(outCommand);
                return;
            } else {
                this.currentMovieList = new ArrayList<>();
                for (int i = 0; i < currentList.getMovielist().size(); i++) {
                    if (currentList.getMovielist().get(i).getName().equals(this.movie)) {
                        this.currentMovieList.add(currentList.getMovielist().get(i));
                        break;
                    }
                }
                this.succespage = 1;
                this.getCurrentPage.setLastPage("movies");
                this.getCurrentPage.setCurrent("see details");
                newuser = new User(user);
                outCommand = new OutCommand(null, currentMovieList, user);
            }
        } else {
            outCommand = new OutCommand("Error");
        }
        output.addPOJO(outCommand);
    }

    /**
     * schimba pagina in upgrades
     *
     * @param output
     */
    public void upgrades(final ArrayNode output) {
        if (this.user != null) {
            this.getCurrentPage.setLastPage(this.currentPage);
            this.getCurrentPage.setCurrent("upgrades");
        } else {
            OutCommand outCommand = new OutCommand("Error");
            output.addPOJO(outCommand);
        }
    }

    /**
     * verifica daca un subsir este intr-un sir
     *
     * @param sequence
     * @param subsequence
     * @return
     */
    public int contains(final String sequence, final String subsequence) {
        return sequence.indexOf(subsequence);
    }

    /**
     * verifica daca un film este in lista de filme
     *
     * @param actions
     * @return
     */
    public boolean checkMovie(final Actions actions) {
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

    /**
     * functie care trateaza cazurile in care vine o comanda de a
     * executa o actiune pe o anumita pagina
     *
     * @param actions
     * @param output
     * @param currentUser
     */
    public void executePageCommand(final Actions actions, final ArrayNode output,
                                   final CurrentUser currentUser) {
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

    /**
     * actiunea de register
     *
     * @param actions
     * @param output
     */
    public void registerAction(final Actions actions, final ArrayNode output) {
        OutCommand outCommand = null;
        this.succes = 0;
        if (user == null) {
            int ok = 0;
            for (int i = 0; i < dataBase.getAllusers().size(); i++) {
                if (Objects.equals(actions.getCredentials().getName(),
                        dataBase.getAllusers().get(i).getCredentials().getName())) {
                    ok = 1;
                    outCommand = new OutCommand("Error");
                    break;
                }
            }
            if (ok == 0) {
                this.newuser = new User(actions.getCredentials().getName(),
                        actions.getCredentials().getPassword(),
                        actions.getCredentials().getAccountType(), actions.getCredentials()
                        .getCountry(),
                        actions.getCredentials().getBalance());
                this.user = new User(newuser);
                dataBase.addWhenRegister(user);
                this.getCurrentPage.setCurrent("register");
                this.getCurrentPage.setLastPage("homepage");
                this.succes = 1;
                newuser = new User(user);
                if (currentMovieList == null) {
                    outCommand = new OutCommand(null, newuser);
                }
            }
        } else {
            outCommand = new OutCommand("Error");
        }
        output.addPOJO(outCommand);
    }

    /**
     * actiunea de login
     *
     * @param actions
     * @param output
     */
    public void loginAction(final Actions actions, final ArrayNode output) {
        OutCommand outCommand = null;
        this.succes = 0;
        if (user == null) {
            int ok = 0;
            for (int i = 0; i < dataBase.getAllusers().size(); i++) {
                if (Objects.equals(actions.getCredentials().getName(),
                        dataBase.getAllusers().get(i).getCredentials().getName())
                        && Objects.equals(actions.getCredentials().getPassword(),
                                dataBase.getAllusers().get(i).getCredentials().getPassword())) {
                    this.newuser = new User(dataBase.getAllusers().get(i).getCredentials()
                            .getName(),
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
                        outCommand = new OutCommand(null, newuser);
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

    /**
     * actiunea de search in care se cauta un film in lista
     * de filme curente
     *
     * @param currentUser
     * @param actions
     * @param output
     */
    public void searchAction(final CurrentUser currentUser, final Actions actions,
                             final ArrayNode output) {
        OutCommand outCommand;
        this.succes = 0;
        this.currentMovieList = new ArrayList<>();
        if (getCurrentPage.getCurrent().equals("movies")) {
            ArrayList<Movie> allMovies = new ArrayList<>();
            for (int i = 0; i < dataBase.getAllmovies().size(); i++) {
                if (contains(dataBase.getAllmovies().get(i).getName(), actions.getStartWith())
                        == 0) {
                    if (countryInvertCheck(currentUser, i)) {
                        allMovies.add(dataBase.getAllmovies().get(i));
                    }
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

    /**
     * actiunea de filter in care se va actualiza lista de
     * filme curente in functie de preferinta
     *
     * @param actions
     * @param currentUser
     * @param output
     */
    public void filterAction(final Actions actions, final CurrentUser currentUser,
                             final ArrayNode output) {
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
                if (filter.getSort().getRating() != null && filter.getSort()
                        .getDuration() != null) {
                    currentMovieList = filter.sortByBoth(currentMovieList);
                } else if (filter.getSort().getRating() != null) {
                    currentMovieList = filter.sortByRating(currentMovieList, filter.getSort()
                            .getRating());
                } else if (filter.getSort().getDuration() != null) {
                    filter.sortByDuration(currentMovieList, filter.getSort().getDuration());
                }
            }
            if (filter.getSort() != null || filter.getContains() != null) {
                newuser = new User(user);
                outCommand = new OutCommand(null, currentMovieList, newuser);
            } else if (filter.getSort() == null || filter.getContains() == null) {
                outCommand = new OutCommand("Error");
            }
        } else {
            outCommand = new OutCommand("Error");
        }
        output.addPOJO(outCommand);
    }

    /**
     * actiunea in care se cumpara tokens
     *
     * @param actions
     * @param output
     */
    public void buyTokens(final Actions actions, final ArrayNode output) {
        OutCommand outCommand;
        this.succes = 0;
        if (getCurrentPage.getCurrent().equals("upgrades")) {
            user.setTokensCount(Integer.parseInt(user.getTokensCount() + actions.getCount()));
            int balance = Integer.parseInt(user.getCredentials().getBalance())
                    - Integer.parseInt(actions.getCount());
            user.getCredentials().setBalance(String.valueOf(balance));
            this.succes = 1;
        } else {
            outCommand = new OutCommand("Error");
            output.addPOJO(outCommand);
        }
    }

    /**
     * se cumpara cont de premium
     *
     * @param output
     */
    public void buyPremiumAcc(final ArrayNode output) {
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

    /**
     * se cumpara filmul ales
     * @param currentUser
     * @param output
     */
    public void purchase(final CurrentUser currentUser, final ArrayNode output) {
        OutCommand outCommand;
        this.purchaseOk = 0;
        this.succes = 0;
        if (getCurrentPage.getCurrent().equals("see details")) {
            if (user.getTokensCount() >= 0) {
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
                            if (dataBase.getAllusers().get(i).getCredentials().getName()
                                    .equals(this.user.getCredentials().getName())) {
                                dataBase.getAllusers().get(i).setPurchasedMovies(currentMovieList);
                                setTokensCountByUser(i);
                            }
                        }
                    } else {
                        this.user.getPurchasedMovies().add(currentMovieList.get(0));
                        for (int i = 0; i < dataBase.getAllusers().size(); i++) {
                            if (dataBase.getAllusers().get(i).getCredentials().getName()
                                    .equals(this.user.getCredentials().getName())) {
                                dataBase.getAllusers().get(i).getPurchasedMovies()
                                        .add(currentMovieList.get(0));
                                setTokensCountByUser(i);
                            }
                        }
                    }
                    if (user.getCredentials().getAccountType().equals("premium")) {
                        if (user.getNumFreePremiumMovies() == 0) {
                            user.setTokensCount(Math.max(user.getTokensCount() - 2, 0));
                        }
                        user.setNumFreePremiumMovies(Math.max(user
                                .getNumFreePremiumMovies() - 1, 0));
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

    /**
     * se adauga in lista de watch filmul ales
     *
     * @param output
     */
    public void watch(final ArrayNode output) {
        OutCommand outCommand;
        int ok = 0;
        this.watchOk = 0;
        this.succes = 0;
        if (this.purchaseOk == 1) {
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
                            if (dataBase.getAllusers().get(i).getCredentials().getName()
                                    .equals(this.user.getCredentials().getName())) {
                                dataBase.getAllusers().get(i).setWatchedMovies(currentMovieList);
                            }
                        }
                    } else {
                        this.user.getWatchedMovies().add(currentMovieList.get(0));
                        for (int i = 0; i < dataBase.getAllusers().size(); i++) {
                            if (dataBase.getAllusers().get(i).getCredentials().getName()
                                    .equals(this.user.getCredentials().getName())) {
                                dataBase.getAllusers().get(i).getWatchedMovies()
                                        .add(currentMovieList.get(0));
                            }
                        }
                    }
                    this.succes = 1;
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

    /**
     * daca filmul a fost cumparat si vazut, se poate da
     * like la film si acesta va fi adaugat in vectorul
     * asociat al utilizatorului
     *
     * @param output
     */
    public void like(final ArrayNode output) {
        this.succes = 0;
        OutCommand outCommand;
        if (this.purchaseOk == 1 && this.watchOk == 1) {
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
                            if (dataBase.getAllusers().get(i).getCredentials().getName()
                                    .equals(this.user.getCredentials().getName())) {
                                dataBase.getAllusers().get(i).setLikedMovies(currentMovieList);
                            }
                        }
                        setNumLikes();
                    } else {
                        this.user.getLikedMovies().add(currentMovieList.get(0));
                        for (int i = 0; i < dataBase.getAllusers().size(); i++) {
                            if (dataBase.getAllusers().get(i).getCredentials().getName()
                                    .equals(this.user.getCredentials().getName())) {
                                dataBase.getAllusers().get(i).getLikedMovies()
                                        .add(currentMovieList.get(0));
                            }
                        }
                        setNumLikes();
                    }
                    this.succes = 1;
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

    /**
     * Se fixeaza rating la fiecare film in functie de ce rating
     * a dat fiecare utilizator
     *
     * @param actions
     * @param output
     */
    public void rate(final Actions actions, final ArrayNode output) {
        OutCommand outCommand;
        this.succes = 0;
        if (this.purchaseOk == 1 && this.watchOk == 1 && actions.getRate() <= 5
                && actions.getRate() >= 1) {
            int ok = 1;
            if (user.getPurchasedMovies().size() > 0 && user.getWatchedMovies().size() > 0) {
                for (int i = 0; i < user.getPurchasedMovies().size(); i++) {
                    if (user.getPurchasedMovies().get(i).getName().equals(this.movie)) {
                        for (int j = 0; j < user.getWatchedMovies().size(); i++) {
                            if (user.getWatchedMovies().get(i).getName().equals(this.movie)) {
                                ok = 0;
                                break;
                            }
                        }
                    }
                }
                if (ok == 0) {
                    if (user.getRatedMovies().size() == 0) {
                        this.user.setRatedMovies(currentMovieList);
                        for (int i = 0; i < dataBase.getAllusers().size(); i++) {
                            if (dataBase.getAllusers().get(i).getCredentials().getName()
                                    .equals(this.user.getCredentials().getName())) {
                                dataBase.getAllusers().get(i).setRatedMovies(currentMovieList);
                                setRatingByUser(actions, i);
                            }
                        }
                    } else {
                        this.user.getRatedMovies().add(currentMovieList.get(0));
                        for (int i = 0; i < dataBase.getAllusers().size(); i++) {
                            if (dataBase.getAllusers().get(i).getCredentials().getName()
                                    .equals(this.user.getCredentials().getName())) {
                                dataBase.getAllusers().get(i).getRatedMovies()
                                        .add(currentMovieList.get(0));
                                setRatingByUser(actions, i);
                            }
                        }
                    }
                    for (int i = 0; i < dataBase.getAllmovies().size(); i++) {
                        if (dataBase.getAllmovies().get(i).getName().equals(this.movie)) {
                            dataBase.getAllmovies().get(i).setNumRatings(dataBase
                                    .getAllmovies().get(i).getNumRatings() + 1);
                            Double rating = calculateRating(this.movie);
                            dataBase.getAllmovies().get(i).setRating(rating);
                            for (Movie value : this.currentMovieList) {
                                value.setNumLikes(dataBase.getAllmovies().get(i).getNumLikes());
                            }
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

    private void setRatingByUser(final Actions actions, final int i) {
        if (dataBase.getAllusers().get(i).getRatedMovies().size() > 0) {
            for (int j = 0; j < dataBase.getAllusers().get(i).getRatedMovies().size(); j++) {
                dataBase.getAllusers().get(i).getRatedMovies().get(j).setRating(actions.getRate());
            }
        } else {
            dataBase.getAllusers().get(i).getRatedMovies().get(0).setRating(actions.getRate());
        }
    }

    /**
     * se va schimba in baza de date curenta numarul de tokeni
     * ai utilizatorului in functie de tipul de cont
     *
     * @param i
     */
    private void setTokensCountByUser(final int i) {
        if (dataBase.getAllusers().get(i).getCredentials().getAccountType().equals("premium")) {
            if (dataBase.getAllusers().get(i).getNumFreePremiumMovies() == 0) {
                dataBase.getAllusers().get(i).setTokensCount(Math.max(dataBase
                        .getAllusers().get(i).getTokensCount() - 2, 0));
            }
            dataBase.getAllusers().get(i).setNumFreePremiumMovies(Math.max(dataBase
                    .getAllusers().get(i).getNumFreePremiumMovies() - 1, 0));
        } else {
            dataBase.getAllusers().get(i).setTokensCount(Math.max(dataBase
                    .getAllusers().get(i).getTokensCount() - 2, 0));
        }
    }

    /**
     * se seteaza in baza date actuala numarul de aprecieri al
     * filmului
     */
    private void setNumLikes() {
        for (int i = 0; i < dataBase.getAllmovies().size(); i++) {
            if (dataBase.getAllmovies().get(i).getName().equals(this.movie)) {
                dataBase.getAllmovies().get(i).setNumLikes(dataBase
                        .getAllmovies().get(i).getNumLikes() + 1);
                for (Movie value : this.currentMovieList) {
                    value.setNumLikes(dataBase.getAllmovies().get(i).getNumLikes());
                }
            }
        }
    }

    /**
     * se verifica daca tara lui este o tara din care nu se
     * poate vedea filmul  repectiv
     */
    private boolean countryInvertCheck(final CurrentUser currentUser, final int index) {
        for (int j = 0; j < dataBase.getAllmovies().get(index).getCountriesBanned().size(); j++) {
            if (currentUser.user().getCredentials().getCountry().contains(dataBase
                    .getAllmovies().get(index).getCountriesBanned().get(j))) {
                return false;
            }
        }
        return true;
    }

    /**
     * se calculeaza rating-ul
     */
    private Double calculateRating(final String name) {
        int ratings = 0, numrating = 0;
        double total = 0.0;
        for (int i = 0; i < dataBase.getAllusers().size(); i++) {
            for (int j = 0; j < dataBase.getAllusers().get(i).getRatedMovies().size(); j++) {
                if (dataBase.getAllusers().get(i).getRatedMovies().get(j).getName().equals(name)) {
                    ratings += dataBase.getAllusers().get(i).getRatedMovies().get(j).getRating();
                    numrating++;
                }
            }
        }
        if (numrating != 0) {
            total = (double) ratings / numrating;
        }
        return total;
    }

    /**
     * se sorteaza dupa tara pe care o are utilizatorul
     *
     * @param currentUser
     * @return
     */
    private ArrayList<Movie> sortByCountry(final CurrentUser currentUser) {
        ArrayList<Movie> allmovies = new ArrayList<>();
        for (int i = 0; i < dataBase.getAllmovies().size(); i++) {
            if (countryInvertCheck(currentUser, i)) {
                Movie movie = new Movie(dataBase.getAllmovies().get(i));
                allmovies.add(movie);
            }
        }
        return allmovies;
    }

    /**
     * @return
     */
    public ArrayList<Movie> getCurrentMovieList() {
        return currentMovieList;
    }

    /**
     * @return
     */
    public DataBase getDataBase() {
        return dataBase;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return
     */
    public User getUser() {
        return user;
    }

    /**
     * @return
     */
    public GetCurrentPage getGetCurrentPage() {
        return getCurrentPage;
    }

    /**
     * @return
     */
    public int getSucces() {
        return succes;
    }

    /**
     * @return
     */
    public String getMovie() {
        return movie;
    }

    /**
     * @param movie
     */
    public void setMovie(final String movie) {
        this.movie = movie;
    }

    /**
     * @return
     */
    public int getSuccespage() {
        return succespage;
    }
}

