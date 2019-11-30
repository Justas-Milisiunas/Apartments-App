package com.apartmentslt.apartments.models;

import android.content.SharedPreferences;

import com.google.gson.Gson;

public class User {
    public static final String USER_DATA_FILE = "com.apartmentslt.USER_DATA_FILE";
    public static final String USER_DATA = USER_DATA_FILE + ".USER_DATA";
    private static final User instance = new User();

    private int idIsNaudotojas;
    private String vardas;
    private String elPastas;
    private String pavarde;
    private double balansas;
    private int role;

    public User() {
    }

    public User(int idIsNaudotojas, String vardas, String elPastas, String pavarde, double balansas, int role) {
        changeData(idIsNaudotojas, vardas, elPastas, pavarde, balansas, role);
//        this.idIsNaudotojas = idIsNaudotojas;
//        this.vardas = vardas;
//        this.elPastas = elPastas;
//        this.pavarde = pavarde;
//        this.balansas = balansas;
//        this.role = role;
    }

    public User(User data) {
//        this(data.getIdIsNaudotojas(), data.getVardas(), data.getElPastas(), data.getPavarde(), data.getBalansas(), data.getRole());
        changeData(data.getIdIsNaudotojas(), data.getVardas(), data.getElPastas(), data.getPavarde(), data.getBalansas(), data.getRole());
    }

    public boolean isOwner() {
        return role == 0;
    }

    public boolean isTenant() {
        return role == 1;
    }

    public boolean isWorker() {
        return role == 2;
    }

    public static void saveData(SharedPreferences sp) {
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();

        String jsonData = gson.toJson(instance);
        editor.putString(USER_DATA, jsonData);
        editor.apply();
    }

    public static void loadData(SharedPreferences sp) {
        Gson gson = new Gson();
        String jsonData = sp.getString(USER_DATA, null);

        User data = gson.fromJson(jsonData, User.class);
        instance.setIdIsNaudotojas(data.getIdIsNaudotojas());
        instance.setVardas(data.getVardas());
        instance.setElPastas(data.getElPastas());
        instance.setPavarde(data.getPavarde());
        instance.setBalansas(data.getBalansas());
        instance.setRole(data.getRole());
    }

    public static void changeData(User user) {
        changeData(user.getIdIsNaudotojas(), user.getVardas(), user.getElPastas(), user.getPavarde(), user.getBalansas(), user.getRole());
    }

    public static void changeData(int idIsNaudotojas, String vardas, String elPastas, String pavarde, double balansas, int role) {
        instance.setIdIsNaudotojas(idIsNaudotojas);
        instance.setVardas(vardas);
        instance.setElPastas(elPastas);
        instance.setPavarde(pavarde);
        instance.setBalansas(balansas);
        instance.setRole(role);
    }

    public static User getInstance() {
        return instance;
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

    public double getBalansas() {
        return balansas;
    }

    public int getRole() {
        return role;
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

    public void setBalansas(double balansas) {
        this.balansas = balansas;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
