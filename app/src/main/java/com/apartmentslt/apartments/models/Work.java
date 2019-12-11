package com.apartmentslt.apartments.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Work implements Serializable {
    private int idDarbas;
    private Date sukurimoData;
    private Date ivykdymoData;
    private double uzmokestis;
    private int busena;
    private int fkButasidButas;
    private int fkSavininkasidIsNaudotojas;
    private int fkValytojasidIsNaudotojas;
    private Status darboBusena;
    private Apartment butas;

    public Work (int idDarbas, Date ivykdymoData, double uzmokestis, int busenaID, Status darboBusena,
                 int fk_butasID, int fk_savininkasID, int fk_valytojasID, Apartment butas){
        this.idDarbas = idDarbas;
        this.ivykdymoData = ivykdymoData;
        this.uzmokestis = uzmokestis;
        this.busena = busenaID;
        this.fkButasidButas = fk_butasID;
        this.fkSavininkasidIsNaudotojas = fk_savininkasID;
        this.fkValytojasidIsNaudotojas = fk_valytojasID;
        this.darboBusena = darboBusena;
        this.butas = butas;
    }

    public int getIdDarbas() {
        return idDarbas;
    }

    public void setIdDarbas(int idDarbas) {
        this.idDarbas = idDarbas;
    }

    public Date getSukurimoData() {
        return sukurimoData;
    }

    public void setSukurimoData(Date sukurimoData) {
        this.sukurimoData = sukurimoData;
    }

    public Date getIvykdymoData() {
        return ivykdymoData;
    }

    public void setIvykdymoData(Date ivykdymoData) {
        this.ivykdymoData = ivykdymoData;
    }

    public double getUzmokestis() {
        return uzmokestis;
    }

    public void setUzmokestis(double uzmokestis) {
        this.uzmokestis = uzmokestis;
    }

    public int getBusena() {
        return busena;
    }

    public void setBusena(int busena) {
        this.busena = busena;
    }

    public int getFkButasidButas() {
        return fkButasidButas;
    }

    public void setFkButasidButas(int fkButasidButas) {
        this.fkButasidButas = fkButasidButas;
    }

    public int getFkSavininkasidIsNaudotojas() {
        return fkSavininkasidIsNaudotojas;
    }

    public void setFkSavininkasidIsNaudotojas(int fkSavininkasidIsNaudotojas) {
        this.fkSavininkasidIsNaudotojas = fkSavininkasidIsNaudotojas;
    }

    public int getFkValytojasidIsNaudotojas() {
        return fkValytojasidIsNaudotojas;
    }

    public void setFkValytojasidIsNaudotojas(int fkValytojasidIsNaudotojas) {
        this.fkValytojasidIsNaudotojas = fkValytojasidIsNaudotojas;
    }

    public Status getDarboBusena() {
        return darboBusena;
    }

    public void setDarboBusena(Status darboBusena) {
        this.darboBusena = darboBusena;
    }

    public Apartment getButas() {
        return butas;
    }

    public void setButas(Apartment butas) {
        this.butas = butas;
    }
}
