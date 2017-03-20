package com.lhd.duong.maps;

import java.util.ArrayList;

/**
 * Created by D on 15/01/2017.
 */

/**
 *
 */
public class GoogleMapAPI{
    @Override
    public String toString() {
        return "GoogleMapAPI{" +
                "start_address='" + start_address + '\'' +
                '}';
    }

    String status;
    String place_id="place_id:";
    String copyrights;
    String summary;
    String distanceText;
    String distanceValue;
    String durationText;
    String durationValue;
    String end_address;
    String start_address;
    String start_locationLat;
    String start_locationLng;
    String end_locationLat;
    String end_locationLng;
    ArrayList<StepsGoLocation> stepses;

    public void setStepses(ArrayList<StepsGoLocation> stepses) {
        this.stepses = stepses;
    }

    public ArrayList<StepsGoLocation> getStepses() {
        return stepses;
    }

    public GoogleMapAPI(String status, String copyrights, String summary, String distanceText, String distanceValue, String durationText, String durationValue, String end_address, String start_address, String start_locationLat, String start_locationLng, String end_locationLat, String end_locationLng, ArrayList<StepsGoLocation> stepses) {

        this.status = status;
        this.copyrights = copyrights;
        this.summary = summary;
        this.distanceText = distanceText;
        this.distanceValue = distanceValue;
        this.durationText = durationText;
        this.durationValue = durationValue;
        this.end_address = end_address;
        this.start_address = start_address;
        this.start_locationLat = start_locationLat;
        this.start_locationLng = start_locationLng;
        this.end_locationLat = end_locationLat;
        this.end_locationLng = end_locationLng;
        this.stepses = stepses;
    }

    public String getStatus() {
        return status;
    }

    public String getCopyrights() {
        return copyrights;
    }

    public String getSummary() {
        return summary;
    }

    public String getDistanceText() {
        return distanceText;
    }

    public String getDistanceValue() {
        return distanceValue;
    }

    public String getDurationText() {
        return durationText;
    }

    public String getDurationValue() {
        return durationValue;
    }

    public String getEnd_address() {
        return end_address;
    }

    public String getStart_address() {
        return start_address;
    }

    public String getStart_locationLat() {
        return start_locationLat;
    }

    public String getStart_locationLng() {
        return start_locationLng;
    }

    public String getEnd_locationLat() {
        return end_locationLat;
    }

    public String getEnd_locationLng() {
        return end_locationLng;
    }
}