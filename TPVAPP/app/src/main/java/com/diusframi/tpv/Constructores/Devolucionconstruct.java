package com.diusframi.tpv.Constructores;

public class Devolucionconstruct {
    Integer Orden;
     Integer Ordenticket;
    String articulo;
    Integer unidad;
    Double precio;
    boolean chequeado;


    public Devolucionconstruct(Integer orden, Integer ordenticket, String articulo, Integer unidad, Double precio, boolean chequeado) {
        Orden = orden;
        Ordenticket = ordenticket;
        this.articulo = articulo;
        this.unidad = unidad;
        this.precio = precio;
        this.chequeado = chequeado;
    }

    public Integer getOrden() {
        return Orden;
    }

    public void setOrden(Integer orden) {
        Orden = orden;
    }

    public Integer getOrdenticket() {
        return Ordenticket;
    }

    public void setOrdenticket(Integer ordenticket) {
        Ordenticket = ordenticket;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    public Integer getUnidad() {
        return unidad;
    }

    public void setUnidad(Integer unidad) {
        this.unidad = unidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public boolean isChequeado() {
        return chequeado;
    }

    public void setChequeado(boolean chequeado) {
        this.chequeado = chequeado;
    }
}
