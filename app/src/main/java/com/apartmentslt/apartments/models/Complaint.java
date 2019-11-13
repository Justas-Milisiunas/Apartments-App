package com.apartmentslt.apartments.models;

public class Complaint {
    private String name;
    private String address;
    private String complaint;

    public Complaint(String name, String address, String complaint) {
        this.name = name;
        this.address = address;
        this.complaint = complaint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }
}
