package com.example.proyectofinal;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

// Importaciones requeridas para Room, DAO y el Adaptador
import com.example.proyectofinal.AppDatabase;
import com.example.proyectofinal.JugadorDao;
import com.example.proyectofinal.JugadorAdapter; // La clase Adaptador clave

public class scores_activity extends AppCompatActivity {

    private JugadorDao jugadorDao;
    private JugadorAdapter jugadorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.scores); // Layout principal: scores.xml

        // Configuración de insets, apuntando al ID main
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1. Inicializar Room Database y DAO
        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
        jugadorDao = db.jugadorDao();

        // 2. Configurar RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerViewJugadores);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar y asignar el Adaptador
        jugadorAdapter = new JugadorAdapter();
        recyclerView.setAdapter(jugadorAdapter);

        // 3. Observar los datos (LiveData)
        jugadorDao.obtenerTodosLosJugadores().observe(this, jugadores -> {
            jugadorAdapter.setJugadores(jugadores);
        });
    }
}