package com.apartmentslt.apartments.models;

import java.io.Serializable;
import java.sql.Date;
import java.time.Instant;

public class Apartment implements Serializable {
    private int size;
    private int roomsCount;
    private Date addedDate;
    private double price;
    private String address;
    private ApartmentStatus status;
    private String description;

    public Apartment(int size, int roomsCount, double price, String address, ApartmentStatus status,
                     String description) {
        this.size = size;
        this.roomsCount = roomsCount;
        this.price = price;
        this.address = address;
        this.status = status;
        this.description = description;
        this.addedDate = new Date(System.currentTimeMillis());
    }

    public int getSize() {
        return size;
    }

    public int getRoomsCount() {
        return roomsCount;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public double getPrice() {
        return price;
    }

    public String getAddress() {
        return address;
    }

    public ApartmentStatus getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }
}
