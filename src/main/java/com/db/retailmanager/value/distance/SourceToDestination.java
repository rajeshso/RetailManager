package com.db.retailmanager.value.distance;

/**
 * Created by Rajesh on 16-May-17.
 */
public class SourceToDestination {
    private static final String LAT_LANG_DELIMITER = ",";
    private static final String PLACE_DELIMITER = "|";
    private String source = "";
    private String destination = "";

    public String getSource() {
        return source;
    }

    public void addSource(String latitude, String longitude) {
        if (source.length()!=0)  this.source = this.source+PLACE_DELIMITER;
        this.source = this.source+latitude+ LAT_LANG_DELIMITER + longitude;
    }

    public void addDestination(String latitude, String longitude) {
        if (destination.length()!=0)  this.destination = this.destination+PLACE_DELIMITER;
        this.destination = this.destination+latitude+ LAT_LANG_DELIMITER + longitude;
    }

    public String getDestination() {
        return destination;
    }
}
