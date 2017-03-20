package com.lhd.duong.maps;

import android.text.Html;
import android.text.Spanned;

/**
 * Created by D on 15/01/2017.
 */

public class StepsGoLocation {
    String distanceTextSteps;
    String distanceValueSteps;
    String durationTextSteps;
    String durationValueSteps;
    String start_locationLatSteps;
    String start_locationLngSteps;
    String end_locationLatSteps;
    String end_locationLngSteps;
    String html_instructions;
    String travel_mode;

    public Spanned getString() {
        return Html.fromHtml("<strong>"
                +getHtml_instructions()
                +"</strong><br>Đi: " +
                "<em>"+
                getDistanceTextSteps()
                +"</em><br> Thời gian đi: <em>"+
                getDurationTextSteps()+"</em></font> " +
                " <i>"+
                getTravel_mode()
                +"</i>");
    }

    public String getDistanceTextSteps() {
        return distanceTextSteps;
    }

    public String getDistanceValueSteps() {
        return distanceValueSteps;
    }

    public String getDurationTextSteps() {
        return durationTextSteps;
    }

    public String getDurationValueSteps() {
        return durationValueSteps;
    }

    public String getStart_locationLatSteps() {
        return start_locationLatSteps;
    }

    public String getStart_locationLngSteps() {
        return start_locationLngSteps;
    }

    public String getEnd_locationLatSteps() {
        return end_locationLatSteps;
    }

    public String getEnd_locationLngSteps() {
        return end_locationLngSteps;
    }

    public String getHtml_instructions() {
        return html_instructions;
    }

    public String getTravel_mode() {
        return travel_mode;
    }

    public StepsGoLocation(String distanceTextSteps,
                           String distanceValueSteps,
                           String durationTextSteps,
                           String durationValueSteps,
                           String start_locationLatSteps,
                           String start_locationLngSteps,
                           String end_locationLatSteps,
                           String end_locationLngSteps,
                           String html_instructions,
                           String travel_mode) {
        this.distanceTextSteps = distanceTextSteps;
        this.distanceValueSteps = distanceValueSteps;
        this.durationTextSteps = durationTextSteps;
        this.durationValueSteps = durationValueSteps;
        this.start_locationLatSteps = start_locationLatSteps;
        this.start_locationLngSteps = start_locationLngSteps;
        this.end_locationLatSteps = end_locationLatSteps;
        this.end_locationLngSteps = end_locationLngSteps;
        this.html_instructions = html_instructions;
        this.travel_mode = travel_mode;

    }
}