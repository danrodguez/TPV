package com.diusframi.tpv.Constructores;

public class MiVenta {
    private Integer Orden;
    private String Fecha;
    private Double Total;
    private int I;
    private boolean Devolucion;




    public MiVenta(Integer orden, String fecha, Double total, int i, boolean devolucion) {
        this.Orden = orden;
        this.Fecha = fecha;
        this.Total = total;
        this.I = i;
        this.Devolucion = devolucion;
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

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double total) {
        Total = total;
    }

    public int getI() {
        return I;
    }

    public void setI(int i) {
        I = i;
    }
    public boolean isDevolucion() {
        return Devolucion;
    }

    public void setDevolucion(boolean devolucion) {
        Devolucion = devolucion;
    }
}
