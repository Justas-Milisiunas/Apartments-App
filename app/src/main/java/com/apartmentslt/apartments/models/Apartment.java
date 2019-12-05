package com.apartmentslt.apartments.models;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

// TODO: Fix issue with pridejimoData
public class Apartment implements Serializable {
    private int dydis;
    private int kambaruSkaicius;
    //    private Date pridejimoData;
    private double kainaUzNakti;
    private String adresas;
    private String nuotraukaUrl;
    private String aprasas;
    private String pavadinimas;
    private String miestas;
    private String šalis;
    private int busena;
    private int idButas;
    private int fkSavininkasidIsNaudotojas;
    private Status status;
    private Owner savininkas;
    private List<Work> darbas;
    private List<RentPeriod> nuomosLaikotarpis;
    private List<Advantage> privalumas;
    private List<Rating> reitingas;
    private List<Complaint> skundas;

    public Apartment(int dydis, int kambaruSkaicius, Date pridejimoData,
                     double kainaUzNakti, String adresas, String nuotraukaUrl,
                     String aprasas, String pavadinimas, String miestas, String šalis,
                     int busena, int idButas, int fkSavininkasidIsNaudotojas,
                     Status busenaNavigation, Owner savininkas, List<Work> darbas,
                     List<RentPeriod> nuomosLaikotarpis, List<Advantage> privalumas,
                     List<Rating> reitingas, List<Complaint> skundas) {
        this.dydis = dydis;
        this.kambaruSkaicius = kambaruSkaicius;
//        this.pridejimoData = pridejimoData;
        this.kainaUzNakti = kainaUzNakti;
        this.adresas = adresas;
        this.nuotraukaUrl = nuotraukaUrl;
        this.aprasas = aprasas;
        this.pavadinimas = pavadinimas;
        this.miestas = miestas;
        this.šalis = šalis;
        this.busena = busena;
        this.idButas = idButas;
        this.fkSavininkasidIsNaudotojas = fkSavininkasidIsNaudotojas;
        this.status = busenaNavigation;
        this.savininkas = savininkas;
        this.darbas = darbas;
        this.nuomosLaikotarpis = nuomosLaikotarpis;
        this.privalumas = privalumas;
        this.reitingas = reitingas;
        this.skundas = skundas;
    }

    public void setDydis(int dydis) {
        this.dydis = dydis;
    }

    public void setKambaruSkaicius(int kambaruSkaicius) {
        this.kambaruSkaicius = kambaruSkaicius;
    }

    public void setKainaUzNakti(double kainaUzNakti) {
        this.kainaUzNakti = kainaUzNakti;
    }

    public void setAdresas(String adresas) {
        this.adresas = adresas;
    }

    public void setNuotraukaUrl(String nuotraukaUrl) {
        this.nuotraukaUrl = nuotraukaUrl;
    }

    public void setAprasas(String aprasas) {
        this.aprasas = aprasas;
    }

    public void setPavadinimas(String pavadinimas) {
        this.pavadinimas = pavadinimas;
    }

    public void setMiestas(String miestas) {
        this.miestas = miestas;
    }

    public void setŠalis(String šalis) {
        this.šalis = šalis;
    }

    public void setBusena(int busena) {
        this.busena = busena;
    }

    public void setIdButas(int idButas) {
        this.idButas = idButas;
    }

    public void setFkSavininkasidIsNaudotojas(int fkSavininkasidIsNaudotojas) {
        this.fkSavininkasidIsNaudotojas = fkSavininkasidIsNaudotojas;
    }

    public void setSavininkas(Owner savininkas) {
        this.savininkas = savininkas;
    }

    public void setDarbas(List<Work> darbas) {
        this.darbas = darbas;
    }

    public void setNuomosLaikotarpis(List<RentPeriod> nuomosLaikotarpis) {
        this.nuomosLaikotarpis = nuomosLaikotarpis;
    }

    public void setPrivalumas(List<Advantage> privalumas) {
        this.privalumas = privalumas;
    }

    public void setReitingas(List<Rating> reitingas) {
        this.reitingas = reitingas;
    }

    public void setSkundas(List<Complaint> skundas) {
        this.skundas = skundas;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getDydis() {
        return dydis;
    }

    public int getKambaruSkaicius() {
        return kambaruSkaicius;
    }

    public double getKainaUzNakti() {
        return kainaUzNakti;
    }

    public String getAdresas() {
        return adresas;
    }

    public String getNuotraukaUrl() {
        return nuotraukaUrl;
    }

    public String getAprasas() {
        return aprasas;
    }

    public String getPavadinimas() {
        return pavadinimas;
    }

    public String getMiestas() {
        return miestas;
    }

    public String getŠalis() {
        return šalis;
    }

    public int getBusena() {
        return busena;
    }

    public int getIdButas() {
        return idButas;
    }

    public int getFkSavininkasidIsNaudotojas() {
        return fkSavininkasidIsNaudotojas;
    }

    public Status getStatus() {
        return status;
    }

    public Owner getSavininkas() {
        return savininkas;
    }

    public List<Work> getDarbas() {
        return darbas;
    }

    public List<RentPeriod> getNuomosLaikotarpis() {
        return nuomosLaikotarpis;
    }

    public List<Advantage> getPrivalumas() {
        return privalumas;
    }

    public List<Rating> getReitingas() {
        return reitingas;
    }

    public List<Complaint> getSkundas() {
        return skundas;
    }
}
