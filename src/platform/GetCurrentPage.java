package platform;

public class GetCurrentPage {
    private String current;

    /**
     * @param nameCurrent
     */
    public GetCurrentPage(final String nameCurrent) {
        this.current = nameCurrent;
    }

    /**
     * @return
     */
    public String getCurrent() {
        return current;
    }

    /**
     * @param current
     */
    public void setCurrent(final String current) {
        this.current = current;
    }

    /**
     * @param lastPage
     */
    public void setLastPage(final String lastPage) {
    }
}
