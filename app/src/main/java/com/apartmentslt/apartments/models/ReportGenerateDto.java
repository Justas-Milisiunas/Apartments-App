package com.apartmentslt.apartments.models;

import java.util.Date;

public class ReportGenerateDto {

    private String From;
    private String To;
    private int UserID;
    private boolean email;

    public ReportGenerateDto(String from, String to, int userID) {
        From = from;
        To = to;
        UserID = userID;
    }
    public ReportGenerateDto(String from, String to, int userID, boolean email) {
        From = from;
        To = to;
        UserID = userID;
        this.email = email;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }
}
