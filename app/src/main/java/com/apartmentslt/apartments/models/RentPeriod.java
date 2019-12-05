package com.apartmentslt.apartments.models;

import java.io.Serializable;
import java.util.Date;

public class RentPeriod implements Serializable {
    private Date nuo;
    private Date iki;
    private int busena;
    private int idNuomosLaikotarpis;
    private int fkNuomininkasidIsNaudotojas;
    private int fkButasidButas;

    public RentPeriod(Date nuo, Date iki, int fkNuomininkasidIsNaudotojas, int fkButasidButas) {
        this.nuo = nuo;
        this.iki = iki;
        this.fkNuomininkasidIsNaudotojas = fkNuomininkasidIsNaudotojas;
        this.fkButasidButas = fkButasidButas;
    }

    public RentPeriod(Date nuo, Date iki, int busena, int idNuomosLaikotarpis, int fkNuomininkasidIsNaudotojas) {
        this.nuo = nuo;
        this.iki = iki;
        this.busena = busena;
        this.idNuomosLaikotarpis = idNuomosLaikotarpis;
        this.fkNuomininkasidIsNaudotojas = fkNuomininkasidIsNaudotojas;
    }

    public void setNuo(Date nuo) {
        this.nuo = nuo;
    }

    public void setIki(Date iki) {
        this.iki = iki;
    }

    public void setBusena(int busena) {
        this.busena = busena;
    }

    public void setIdNuomosLaikotarpis(int idNuomosLaikotarpis) {
        this.idNuomosLaikotarpis = idNuomosLaikotarpis;
    }

    public void setFkNuomininkasidIsNaudotojas(int fkNuomininkasidIsNaudotojas) {
        this.fkNuomininkasidIsNaudotojas = fkNuomininkasidIsNaudotojas;
    }

    public Date getNuo() {
        return nuo;
    }

    public Date getIki() {
        return iki;
    }

    public int getBusena() {
        return busena;
    }

    public int getIdNuomosLaikotarpis() {
        return idNuomosLaikotarpis;
    }

    public int getFkNuomininkasidIsNaudotojas() {
        return fkNuomininkasidIsNaudotojas;
    }
}
