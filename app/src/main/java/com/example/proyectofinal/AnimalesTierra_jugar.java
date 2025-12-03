package com.example.proyectofinal;

public class AnimalesTierra_jugar {
    public int id;
    public String nombre, caption;

    public AnimalesTierra_jugar(int id, String nombre, String caption){
        this.id=id;
        this.nombre=nombre;
        this.caption=caption;
    }

    public int getId(){
        return id;
    }
    public String getNombre(){
        return nombre;
    }
    public String getCaption() {
        return caption;
    }
}