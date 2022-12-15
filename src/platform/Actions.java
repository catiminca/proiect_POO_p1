package platform;
import inputs.ActionInput;
public class Actions {
    private String type;
    private String page;
    private String feature;
    private final String startWith;
    private Credentials credentials;
    private final Filter filters;
    private final String objectType;
    private final String count;
    private String movie;
    private final Double rate;

    public Actions(ActionInput actionInput) {
        this.type = actionInput.getType();
        this.page = actionInput.getPage();
        this.feature = actionInput.getFeature();
        this.credentials = actionInput.getCredentials();
        this.startWith = actionInput.getStartsWith();
        this.filters = actionInput.getFilters();
        this.objectType = actionInput.getObjectType();
        this.count = actionInput.getCount();
        this.movie = actionInput.getMovie();
        this.rate = actionInput.getRate();
    }

    public Actions(Actions actions) {
        this.type = actions.getType();
        this.page = actions.getPage();
        this.feature = actions.getFeature();
        this.credentials = actions.getCredentials();
        this.startWith = actions.getStartWith();
        this.filters = actions.getFilters();
        this.objectType = actions.getObjectType();
        this.count = actions.getCount();
        this.movie = actions.getMovie();
        this.rate = actions.getRate();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getStartWith() {
        return startWith;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public Filter getFilters() {
        return filters;
    }

    public String getObjectType() {
        return objectType;
    }

    public String getCount() {
        return count;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public Double getRate() {
        return rate;
    }

}

