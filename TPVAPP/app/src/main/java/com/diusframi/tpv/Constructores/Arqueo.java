package com.diusframi.tpv.Constructores;

public class Arqueo {
    private Integer Orden;
    private String Fecha;
    private Double Descuadre;
    private Double total;
    private int I;

    public Arqueo(Integer orden, String fecha, Double descuadre, Double total, int i) {
        this.Orden = orden;
        this.Fecha = fecha;
        this.Descuadre = descuadre;
        this.total = total;
        this.I = i;
    }

    public Integer getOrden() {
        return Orden;
    }

    public void setOrden(Integer orden) {
        Orden = orden;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public Double getDescuadre() {
        return Descuadre;
    }

    public void setDescuadre(Double descuadre) {
        Descuadre = descuadre;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public int getI() {
        return I;
    }

    public void setI(int i) {
        I = i;
    }
}
