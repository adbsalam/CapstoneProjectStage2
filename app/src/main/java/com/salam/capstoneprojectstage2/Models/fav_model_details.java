package com.salam.capstoneprojectstage2.Models;

public class fav_model_details {


    public fav_model_details() {
    }

    public String getCaseid() {
        return caseid;
    }

    public void setCaseid(String caseid) {
        this.caseid = caseid;
    }

    public String getCite() {
        return cite;
    }

    public void setCite(String cite) {
        this.cite = cite;
    }

    public String getCourt() {
        return court;
    }

    public void setCourt(String court) {
        this.court = court;
    }

    public String getDetails_extra() {
        return details_extra;
    }

    public void setDetails_extra(String details_extra) {
        this.details_extra = details_extra;
    }

    public String getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public fav_model_details(String caseid, String cite, String court, String details_extra, String jurisdiction, String title) {
        this.caseid = caseid;
        this.cite = cite;
        this.court = court;
        this.details_extra = details_extra;
        this.jurisdiction = jurisdiction;
        this.title = title;
    }

    String caseid;
    String cite;
    String court;
    String details_extra;
    String jurisdiction;
    String title;




}
