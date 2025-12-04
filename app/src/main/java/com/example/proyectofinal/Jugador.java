package com.example.proyectofinal;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


// Mapea esta clase a la tabla "tabla_jugadores" en SQLite
@Entity(tableName = "tabla_jugadores")
public class Jugador {

    // Clave primaria autogenerada
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nombreUsuario;
    private int puntaje;
    private long fechaRegistro;

    public Jugador(String nombreUsuario, int puntaje, long fechaRegistro) {
        this.nombreUsuario = nombreUsuario;
        this.puntaje = puntaje;
        this.fechaRegistro = fechaRegistro;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }


    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public long getFechaRegistro() {
        return fechaRegistro;
    }
}