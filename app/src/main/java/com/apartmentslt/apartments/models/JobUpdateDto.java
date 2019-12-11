package com.apartmentslt.apartments.models;

public class JobUpdateDto {
    private int IsUserID;
    private int JobID;

    public JobUpdateDto(int isUserID, int jobID) {
        IsUserID = isUserID;
        JobID = jobID;
    }

    public int getIsUserID() {
        return IsUserID;
    }

    public void setIsUserID(int isUserID) {
        IsUserID = isUserID;
    }

    public int getJobID() {
        return JobID;
    }

    public void setJobID(int jobID) {
        JobID = jobID;
    }
}
