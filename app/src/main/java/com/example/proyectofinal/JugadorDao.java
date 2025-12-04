package com.example.proyectofinal;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.lifecycle.LiveData; //agarrar datos de forma reactiva

import java.util.List;

@Dao
public interface JugadorDao {

    // insercion en hilo secundario
    @Insert
    void insertar(Jugador jugador);

    // obtener todos los jugadores
    @Query("SELECT * FROM tabla_jugadores ORDER BY puntaje DESC")
    LiveData<List<Jugador>> obtenerTodosLosJugadores();

    /*
    @Update
    void actualizar(Jugador jugador);

    @Delete
    void eliminar(Jugador jugador);
    */
}