package com.diusframi.tpv.Constructores;

public class Carrito {
    //Declaraciones
    private String categoria;
    private String nombre;
    private Integer numero;
    private Double precio;
    private Integer iva;

    public Carrito(String categoria, String nombre, Integer numero, Double precio, Integer iva) {
        this.categoria = categoria;
        this.nombre = nombre;
        this.numero = numero;
        this.precio = precio;
        this.iva = iva;
    }

    private boolean isChecked = false;


    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String tipo) {
        this.categoria = tipo;
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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getIva() {
        return iva;
    }

    public void setIva(Integer iva) {
        this.iva = iva;
    }


}
