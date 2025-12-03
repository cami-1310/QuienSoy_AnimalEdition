package com.example.proyectofinal;

public class Animales_jugar {
    public int id;
    public String nombre, caption;

    public Animales_jugar(int id, String nombre, String caption){
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