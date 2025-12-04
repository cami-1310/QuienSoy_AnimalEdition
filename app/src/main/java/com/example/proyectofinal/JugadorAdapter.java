package com.example.proyectofinal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinal.R;
import com.example.proyectofinal.Jugador;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class JugadorAdapter extends RecyclerView.Adapter<JugadorAdapter.JugadorViewHolder> {

    private List<Jugador> jugadores = new ArrayList<>();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    public void setJugadores(List<Jugador> nuevosJugadores) {
        this.jugadores = nuevosJugadores;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public JugadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.scores, parent, false);
        return new JugadorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull JugadorViewHolder holder, int position) {
        Jugador jugadorActual = jugadores.get(position);

        holder.tvNombreUsuario.setText(jugadorActual.getNombreUsuario());
        holder.tvPuntaje.setText(String.valueOf(jugadorActual.getPuntaje()));

        String fechaFormateada = dateFormat.format(new Date(jugadorActual.getFechaRegistro()));
        holder.tvFechaRegistro.setText(fechaFormateada);
    }

    @Override
    public int getItemCount() {
        return jugadores.size();
    }

    static class JugadorViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvNombreUsuario;
        private final TextView tvPuntaje;
        private final TextView tvFechaRegistro;

        private JugadorViewHolder(View itemView) {
            super(itemView);
            tvNombreUsuario = itemView.findViewById(R.id.tvNombreUsuario);
            tvPuntaje = itemView.findViewById(R.id.tvPuntaje);
            tvFechaRegistro = itemView.findViewById(R.id.tvFechaRegistro);
        }
    }
}