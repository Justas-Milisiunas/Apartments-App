package com.apartmentslt.apartments.models;

public class ApartmentDeleteDto {
    int IdButas;
    int fkSavininkasidIsNaudotojas;

    public ApartmentDeleteDto(int idButas, int fkSavininkasidIsNaudotojas) {
        IdButas = idButas;
        this.fkSavininkasidIsNaudotojas = fkSavininkasidIsNaudotojas;
    }

    public int getIdButas() {
        return IdButas;
    }

    public void setIdButas(int idButas) {
        IdButas = idButas;
    }

    public int getFkSavininkasidIsNaudotojas() {
        return fkSavininkasidIsNaudotojas;
    }

    public void setFkSavininkasidIsNaudotojas(int fkSavininkasidIsNaudotojas) {
        this.fkSavininkasidIsNaudotojas = fkSavininkasidIsNaudotojas;
    }
}
