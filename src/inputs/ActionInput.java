package inputs;

import platform.Credentials;
import platform.Filter;

public class ActionInput {
    private String type;
    private String page;
    private Credentials credentials;
    private String feature;
    private String startsWith;
    private Filter filters;
    private String objectType;
    private String count;
    private String movie;
    private Double rate;

//    public ActionInput(String startsWith, Filter filters, String objectType, String count,
//                       Double rate) {
//        this.startsWith = startsWith;
//        this.filters = filters;
//        this.objectType = objectType;
//        this.count = count;
//        this.rate = rate;
//    }
    public ActionInput() { }
    /**
     *
     */
    public String getType() {
        return this.type;
    }

    /**
     *
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     */
    public String getPage() {
        return this.page;
    }

    /**
     *
     */
    public void setPage(String page) {
        this.page = page;
    }

    /**
     *
     */
    public Credentials getCredentials() {
        return this.credentials;
    }

    /**
     *
     */
    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    /**
     *
     */
    public String getFeature() {
        return this.feature;
    }

    /**
     *
     */
    public void setFeature(String feature) {
        this.feature = feature;
    }

    /**
     *
     */
    public String getStartsWith() {
        return this.startsWith;
    }

    /**
     *
     */
    public Filter getFilters() {
        return this.filters;
    }

    /**
     *
     */
    public String getObjectType() {
        return this.objectType;
    }

    /**
     *
     */
    public String getCount() {
        return this.count;
    }

    /**
     *
     */
    public Double getRate() {
        return this.rate;
    }

    /**
     *
     */
    public String getMovie() {
        return this.movie;
    }

    /**
     *
     */
    public void setMovie(String movie) {
        this.movie = movie;
    }
}
