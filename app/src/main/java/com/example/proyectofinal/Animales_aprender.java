package com.example.proyectofinal;

public class Animales_aprender {
    public int id, img;
    /*prueba git*/
    public String nombre, tamano, peso, peligrosidad, apariencia, habitat, dieta, generalidades;

    public Animales_aprender(int id, int img, String nombre, String tamano, String peso, String peligrosidad, String apariencia, String habitat, String dieta, String generalidades){
        this.id=id;
        this.img=img;
        this.nombre=nombre;
        this.tamano=tamano;
        this.peso=peso;
        this.peligrosidad=peligrosidad;
        this.apariencia=apariencia;
        this.habitat=habitat;
        this.dieta=dieta;
        this.generalidades=generalidades;
    }

    //getters
    public int getId(){
        return id;
    }
    public int getImg(){
        return img;
    }
    public String getNombre(){
        return nombre;
    }
    public String getTamano(){
        return tamano;
    }
    public String getPeso(){
        return peso;
    }
    public String getPeligrosidad(){
        return peligrosidad;
    }
    public String getApariencia(){
        return apariencia;
    }
    public String getHabitat(){
        return habitat;
    }
    public String getDieta(){
        return dieta;
    }
    public String getGeneralidades(){
        return generalidades;
    }
}