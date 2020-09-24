package com.diusframi.tpv.Constructores;

public class Categoria {
    //Declaraciones
    String Nombre;
    String Precio;

    public Categoria(String nombre, String precio) {
        Nombre = nombre;
        Precio = precio;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String precio) {
        Precio = precio;
    }
}
