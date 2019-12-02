package com.apartmentslt.apartments.models;

import java.io.Serializable;

class Status implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
