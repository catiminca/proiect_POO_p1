package platform;

public class GetCurrentPage {
    private String current;

    public GetCurrentPage(String nameCurrent) {
        this.current = nameCurrent;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public void setLastPage(String lastPage) {
    }
}
