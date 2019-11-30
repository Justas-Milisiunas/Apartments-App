package com.apartmentslt.apartments.models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserRegistrationDto {
    private String vardas;
    private String pavarde;
    private String slaptazodis;
    private String elPastas;
    private int role;

    public UserRegistrationDto(String vardas, String pavarde, String slaptazodis, String elPastas, int role) {
        this.vardas = vardas;
        this.pavarde = pavarde;
        this.slaptazodis = slaptazodis;
        this.elPastas = elPastas;
        this.role = role;
    }

    /***
     * Validates object's data
     * @return
     */
    public boolean Valid() {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"; // Email validation regex
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);

        if (vardas == null || vardas.length() < 4 || vardas.length() > 255)
            return false;
        if (pavarde == null || pavarde.length() < 4 || pavarde.length() > 255)
            return false;
        if (slaptazodis == null || slaptazodis.length() < 4 || slaptazodis.length() > 255)
            return false;
        if (elPastas == null || elPastas.length() < 4 || elPastas.length() > 255)
            return false;
        if (role < 0 || role > 2)
            return false;

        Matcher matcher = pattern.matcher(elPastas);
        if (!matcher.matches())
            return false;

        return true;
    }

    public void setVardas(String vardas) {
        this.vardas = vardas;
    }

    public void setPavarde(String pavarde) {
        this.pavarde = pavarde;
    }

    public void setSlaptazodis(String slaptazodis) {
        this.slaptazodis = slaptazodis;
    }

    public void setElPastas(String elPastas) {
        this.elPastas = elPastas;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getVardas() {
        return vardas;
    }

    public String getPavarde() {
        return pavarde;
    }

    public String getSlaptazodis() {
        return slaptazodis;
    }

    public String getElPastas() {
        return elPastas;
    }

    public int getRole() {
        return role;
    }
}
