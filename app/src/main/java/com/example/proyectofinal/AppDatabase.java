package com.example.proyectofinal;
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Jugador.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    // expone la referencia al DAO
    public abstract JugadorDao jugadorDao();

    // singleton Pattern
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "jugadores_database").build();
                }
            }
        }
        return INSTANCE;
    }
}