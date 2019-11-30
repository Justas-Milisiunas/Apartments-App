package com.apartmentslt.apartments.models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserLoginDto {
    private String elPastas;
    private String slaptazodis;

    public UserLoginDto(String elPastas, String slaptazodis) {
        this.elPastas = elPastas;
        this.slaptazodis = slaptazodis;
    }

    /***
     * Validates object's data
     * @return
     */
    public boolean valid() {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"; // Email validation regex
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);

        if (elPastas == null || elPastas.length() < 4 || elPastas.length() > 255)
            return false;

        Matcher matcher = pattern.matcher(elPastas);
        if (!matcher.matches())
            return false;

        return slaptazodis != null && slaptazodis.length() >= 4 && slaptazodis.length() <= 255;
    }

    public String getElPastas() {
        return elPastas;
    }

    public String getSlaptazodis() {
        return slaptazodis;
    }

    public void setElPastas(String elPastas) {
        this.elPastas = elPastas;
    }

    public void setSlaptazodis(String slaptazodis) {
        this.slaptazodis = slaptazodis;
    }
}
