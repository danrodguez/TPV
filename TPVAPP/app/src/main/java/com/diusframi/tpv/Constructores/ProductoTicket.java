package com.diusframi.tpv.Constructores;

public class ProductoTicket {
    Integer unidad;
    String articulos;
    Double preciou;
    Double importe;

    public ProductoTicket(Integer unidad, String articulos, Double preciou, Double importe) {
        this.unidad = unidad;
        this.articulos = articulos;
        this.preciou = preciou;
        this.importe = importe;
    }

    public Integer getUnidad() {
        return unidad;
    }

    public void setUnidad(Integer unidad) {
        this.unidad = unidad;
    }

    public String getArticulos() {
        return articulos;
    }

    public void setArticulos(String articulos) {
        this.articulos = articulos;
    }

    public Double getPreciou() {
        return preciou;
    }

    public void setPreciou(Double preciou) {
        this.preciou = preciou;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }
}
