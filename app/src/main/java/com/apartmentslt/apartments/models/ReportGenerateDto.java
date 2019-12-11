package com.apartmentslt.apartments.models;

import java.util.Date;

public class ReportGenerateDto {

    private Date From;
    private Date To;
    private int UserID;

    public ReportGenerateDto(Date from, Date to, int userID) {
        From = from;
        To = to;
        UserID = userID;
    }

    public Date getFrom() {
        return From;
    }

    public void setFrom(Date from) {
        From = from;
    }

    public Date getTo() {
        return To;
    }

    public void setTo(Date to) {
        To = to;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }
}
