package com.diusframi.tpv.Constructores;

public class EditarLinea {

    private String nombre;
    private Integer numero;

    public EditarLinea(String nombre, Integer numero) {
        this.nombre = nombre;
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }
}
