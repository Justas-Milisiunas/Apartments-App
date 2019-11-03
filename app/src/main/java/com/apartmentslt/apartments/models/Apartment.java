package com.apartmentslt.apartments.models;

import java.sql.Date;
import java.time.Instant;

public class Apartment {
    private int size;
    private int roomsCount;
    private Date addedDate;
    private double price;
    private String address;
    private ApartmentStatus status;

    public Apartment(int size, int roomsCount, double price, String address, ApartmentStatus status) {
        this.size = size;
        this.roomsCount = roomsCount;
        this.price = price;
        this.address = address;
        this.status = status;
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
}
