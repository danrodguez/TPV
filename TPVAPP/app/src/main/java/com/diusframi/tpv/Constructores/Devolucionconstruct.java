package com.diusframi.tpv.Constructores;

public class Devolucionconstruct {
     Integer Orden;
    String articulo;
    Integer unidad;
    Double precio;
    boolean chequeado;



    public Devolucionconstruct(String articulo, Integer unidad, Double precio, Integer orden, boolean chequeado) {
        this.articulo = articulo;
        this.unidad = unidad;
        this.precio = precio;
        this.Orden = orden;
        this.chequeado = chequeado;
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

    public Integer getOrden() {
        return Orden;
    }

    public void setOrden(Integer orden) {
        Orden = orden;
    }
    public boolean isChequeado() {
        return chequeado;
    }

    public void setChequeado(boolean chequeado) {
        this.chequeado = chequeado;
    }

}
