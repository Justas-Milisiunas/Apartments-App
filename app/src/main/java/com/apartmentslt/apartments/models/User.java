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
    }

    public User(User data) {
        changeData(data.getIdIsNaudotojas(), data.getVardas(), data.getElPastas(), data.getPavarde(), data.getBalansas(), data.getRole());
    }

    /**
     * Checks if user is owner
     *
     * @return True if owner, false if not
     */
    public boolean isOwner() {
        return role == 0;
    }

    /**
     * Checks if user is tenant
     *
     * @return True if tenant, false if not
     */
    public boolean isTenant() {
        return role == 1;
    }

    /**
     * Checks if user is worker
     *
     * @return True if worker, false if not
     */
    public boolean isWorker() {
        return role == 2;
    }

    /**
     * Logouts user by removing its data from local storage
     *
     * @param sp Shared Preferences
     */
    public static void logout(SharedPreferences sp) {
        sp.edit().remove(USER_DATA).apply();
    }

    /**
     * Saves user's data to the local storage
     *
     * @param sp Shared Preferences
     */
    public static void saveData(SharedPreferences sp) {
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();

        String jsonData = gson.toJson(instance);
        editor.putString(USER_DATA, jsonData);
        editor.apply();
    }

    /**
     * Loads user's data from the local storage
     *
     * @param sp Shared Preferences
     * @return true if user exists, false if not
     */
    public static boolean loadData(SharedPreferences sp) {
        Gson gson = new Gson();
        String jsonData = sp.getString(USER_DATA, null);

        if (jsonData == null)
            return false;

        User data = gson.fromJson(jsonData, User.class);
        instance.setIdIsNaudotojas(data.getIdIsNaudotojas());
        instance.setVardas(data.getVardas());
        instance.setElPastas(data.getElPastas());
        instance.setPavarde(data.getPavarde());
        instance.setBalansas(data.getBalansas());
        instance.setRole(data.getRole());

        return true;
    }

    /**
     * Changes local user's data
     *
     * @param user New user data
     */
    public static void changeData(User user) {
        changeData(user.getIdIsNaudotojas(), user.getVardas(), user.getElPastas(), user.getPavarde(), user.getBalansas(), user.getRole());
    }

    /**
     * Changes local user's data
     *
     * @param idIsNaudotojas id
     * @param vardas         first name
     * @param elPastas       email
     * @param pavarde        last name
     * @param balansas       balance
     * @param role           role
     */
    public static void changeData(int idIsNaudotojas, String vardas, String elPastas, String pavarde, double balansas, int role) {
        instance.setIdIsNaudotojas(idIsNaudotojas);
        instance.setVardas(vardas);
        instance.setElPastas(elPastas);
        instance.setPavarde(pavarde);
        instance.setBalansas(balansas);
        instance.setRole(role);
    }

    /**
     * Local user instance (For singleton)
     *
     * @return Local user
     */
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
