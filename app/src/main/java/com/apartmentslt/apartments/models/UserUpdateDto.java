package com.apartmentslt.apartments.models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserUpdateDto {
    private int idIsNaudotojas;
    private String vardas;
    private String elPastas;
    private String pavarde;
    private String slaptazodis;
    private String naujasSlaptazodis;


    public UserUpdateDto() {

    }

    public UserUpdateDto(int idIsNaudotojas, String vardas, String elPastas, String pavarde, String slaptazodis, String naujasSlaptazodis) {
        this.idIsNaudotojas = idIsNaudotojas;
        this.vardas = vardas;
        this.elPastas = elPastas;
        this.pavarde = pavarde;
        this.slaptazodis = slaptazodis;
        this.naujasSlaptazodis = naujasSlaptazodis;
    }

    public boolean valid() {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"; // Email validation regex
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);

        if (vardas == null || vardas.length() < 4 || vardas.length() > 255)
            return false;
        if (pavarde == null || pavarde.length() < 4 || pavarde.length() > 255)
            return false;
        if (slaptazodis == null || slaptazodis.length() < 4 || slaptazodis.length() > 255)
            return false;
        if (naujasSlaptazodis != null && !naujasSlaptazodis.equals("") && naujasSlaptazodis.length() < 4 || naujasSlaptazodis.length() > 255)
            return false;
        if (elPastas == null || elPastas.length() < 4 || elPastas.length() > 255)
            return false;

        Matcher matcher = pattern.matcher(elPastas);
        if (!matcher.matches())
            return false;

        return true;
    }

    public void setIdIsNaudotojas(int idIsNaudotojas) {
        this.idIsNaudotojas = idIsNaudotojas;
    }

    public void setVardas(String vardas) {
        this.vardas = vardas;
    }

    public void setElPastas(String elPastas) {
        this.elPastas = elPastas;
    }

    public void setPavarde(String pavarde) {
        this.pavarde = pavarde;
    }

    public int getIdIsNaudotojas() {
        return idIsNaudotojas;
    }

    public String getVardas() {
        return vardas;
    }

    public String getElPastas() {
        return elPastas;
    }

    public String getPavarde() {
        return pavarde;
    }
}
