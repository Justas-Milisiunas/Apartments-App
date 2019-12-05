package com.apartmentslt.apartments.models;

import java.io.Serializable;

public class Complaint implements Serializable {
    private String pranesimas;
    private int FkButasidButas;
    private int FkNuomininkasidIsNaudotojas;

    public Complaint(String pranesimas, int fkButasidButas, int fkNuomininkasidIsNaudotojas) {
        this.pranesimas = pranesimas;
        FkButasidButas = fkButasidButas;
        FkNuomininkasidIsNaudotojas = fkNuomininkasidIsNaudotojas;
    }

    public void setPranesimas(String pranesimas) {
        this.pranesimas = pranesimas;
    }

    public void setFkButasidButas(int fkButasidButas) {
        FkButasidButas = fkButasidButas;
    }

    public void setFkNuomininkasidIsNaudotojas(int fkNuomininkasidIsNaudotojas) {
        FkNuomininkasidIsNaudotojas = fkNuomininkasidIsNaudotojas;
    }

    public String getPranesimas() {
        return pranesimas;
    }

    public int getFkButasidButas() {
        return FkButasidButas;
    }

    public int getFkNuomininkasidIsNaudotojas() {
        return FkNuomininkasidIsNaudotojas;
    }
}
