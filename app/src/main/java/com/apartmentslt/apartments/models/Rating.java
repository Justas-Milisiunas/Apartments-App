package com.apartmentslt.apartments.models;

import java.io.Serializable;

class Rating implements Serializable {
    private int ivertinimas;
    private int fkButasidButas;
    private int fkNuomininkasidIsNaudotojas;

    public Rating(int invertinimas, int fkButasidButas, int fkNuomininkasidIsNaudotojas) {
        this.ivertinimas = invertinimas;
        this.fkButasidButas = fkButasidButas;
        this.fkNuomininkasidIsNaudotojas = fkNuomininkasidIsNaudotojas;
    }

    public void setIvertinimas(int invertinimas) {
        this.ivertinimas = invertinimas;
    }

    public void setFkButasidButas(int fkButasidButas) {
        this.fkButasidButas = fkButasidButas;
    }

    public void setFkNuomininkasidIsNaudotojas(int fkNuomininkasidIsNaudotojas) {
        this.fkNuomininkasidIsNaudotojas = fkNuomininkasidIsNaudotojas;
    }

    public int getIvertinimas() {
        return ivertinimas;
    }

    public int getFkButasidButas() {
        return fkButasidButas;
    }

    public int getFkNuomininkasidIsNaudotojas() {
        return fkNuomininkasidIsNaudotojas;
    }
}
