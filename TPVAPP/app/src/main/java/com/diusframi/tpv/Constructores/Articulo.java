package com.diusframi.tpv.Constructores;

public class Articulo {
    private String categoria;
    private String nombre;
    private Integer favorito;
    private Double precio;
    private Integer iva;
    private Double base;

    public Articulo(String categoria, String nombre, Integer favorito, Double precio, Integer iva, Double base) {
        this.categoria = categoria;
        this.nombre = nombre;
        this.favorito = favorito;
        this.precio = precio;
        this.iva = iva;
        this.base = base;
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

    public Integer getFavorito() {
        return favorito;
    }

    public void setFavorito(Integer favorito) {
        this.favorito = favorito;
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

    public Double getBase() {
        return base;
    }

    public void setBase(Double base) {
        this.base = base;
    }


}
