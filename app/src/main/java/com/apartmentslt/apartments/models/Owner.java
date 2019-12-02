package com.apartmentslt.apartments.models;

import java.io.Serializable;

class Owner implements Serializable {
    private int idIsNaudotojas;
    private User idIsNaudotojasNavigation;

    public void setIdIsNaudotojas(int idIsNaudotojas) {
        this.idIsNaudotojas = idIsNaudotojas;
    }

    public void setIdIsNaudotojasNavigation(User idIsNaudotojasNavigation) {
        this.idIsNaudotojasNavigation = idIsNaudotojasNavigation;
    }

    public int getIdIsNaudotojas() {
        return idIsNaudotojas;
    }

    public User getIdIsNaudotojasNavigation() {
        return idIsNaudotojasNavigation;
    }
}
