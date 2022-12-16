package platform;

import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;

public class AllActions {
    private ArrayList<Actions> actions;
    private GetCurrentPage getCurrentPage;
    private CurrentUser currentUser;
    private String current;
    private CurrentMovieList currentMovieList;

    public AllActions(final DataBase base, final String curr, final ArrayNode output) {
        this.actions = new ArrayList<>();
        this.actions.addAll(base.getAllActions());
        this.getCurrentPage = new GetCurrentPage(curr);
        CurrentPage currentPage = null;
        this.current = curr;
        this.currentUser = null;
        this.currentMovieList = null;
        actions(currentPage, base, output);
    }

    /**
     * functie cu ajutorul careia tratez fiecare tip de actiune, aceasta fiind ori
     * schimb pagina ori execut o actiune asupra unei pagini
     * @param currentPage
     * @param base
     * @param output
     */

    public void actions(CurrentPage currentPage, DataBase base, final ArrayNode output) {
        int ok = 0;
        for (Actions action : actions) {
            switch (action.getType()) {
                case "change page" -> {
                    if ((actions.get(0).getPage().equals("register")
                            || actions.get(0).getPage().equals("login")) && ok == 0) {
                        currentPage = new CurrentPage(action.getPage(), base, this.current,
                                this.currentUser, this.currentMovieList);
                        ok = 1;
                    } else if (ok == 1) {
                        if (currentPage.getUser() == null) {
                            this.currentUser = null;
                        } else {
                            this.currentUser = new CurrentUser(currentPage.getUser());
                        }
                        currentPage = new CurrentPage(action.getPage(), base, this.current,
                                this.currentUser, this.currentMovieList);
                    }
                    assert currentPage != null;
                    currentPage.changePage(action, output, this.currentUser);
                    if (action.getPage().equals("logout") && currentPage.getSuccespage() == 1) {
                        this.current = "homepage";
                        this.currentUser = null;
                    }
                    if ((action.getPage().equals("movies")
                            || action.getPage().equals("see details"))
                            && currentPage.getSuccespage() == 1) {
                        this.current = currentPage.getGetCurrentPage().getCurrent();
                        if (currentPage.getCurrentMovieList() != null) {
                            currentMovieList = new CurrentMovieList(currentPage
                                    .getCurrentMovieList());
                        }
                    }
                }
                case "on page" -> {
                    if (currentPage != null) {
                        currentPage.executePageCommand(action, output, this.currentUser);
                    }
                    assert currentPage != null;
                    base = new DataBase(currentPage.getDataBase());
                    if (currentPage.getSucces() == 1) {
                        this.current = currentPage.getGetCurrentPage().getCurrent();
                        if (currentPage.getUser() != null) {
                            this.currentUser = new CurrentUser(currentPage.getUser());
                        } else {
                            this.currentUser = null;
                        }
                        if (currentPage.getCurrentMovieList() != null) {
                            currentMovieList = new CurrentMovieList(currentPage
                                    .getCurrentMovieList());
                        }
                    }
                }
            };
        }
        currentUser = null;
        current = "homepage";
    }

    /**
     */
    public ArrayList<Actions> getActions() {
        return actions;
    }
    /**
     */
    public void setActions(final ArrayList<Actions> actions) {
        this.actions = actions;
    }
    /**
     */
    public GetCurrentPage getGetCurrentPage() {
        return getCurrentPage;
    }
    /**
     */
    public void setGetCurrentPage(final GetCurrentPage getCurrentPage) {
        this.getCurrentPage = getCurrentPage;
    }
    /**
     */
    public CurrentUser getCurrentUser() {
        return currentUser;
    }
    /**
     */
    public void setCurrentUser(final CurrentUser currentUser) {
        this.currentUser = currentUser;
    }
    /**
     */
    public String getCurrent() {
        return current;
    }
    /**
     */
    public void setCurrent(final String current) {
        this.current = current;
    }
    /**
     */
    public CurrentMovieList getCurrentMovieList() {
        return currentMovieList;
    }
    /**
     */
    public void setCurrentMovieList(final CurrentMovieList currentMovieList) {
        this.currentMovieList = currentMovieList;
    }
}
