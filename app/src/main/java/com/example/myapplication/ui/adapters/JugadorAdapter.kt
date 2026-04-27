package com.example.myapplication.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.model.Jugador

class JugadorAdapter : ListAdapter<Jugador, JugadorAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.text1.text = "${item.nombre} (${item.posicion})"
        holder.text2.text = "Dorsal: ${item.dorsal} - ${item.nombreEquipo ?: ""}"
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text1: TextView = view.findViewById(android.R.id.text1)
        val text2: TextView = view.findViewById(android.R.id.text2)
    }

    class DiffCallback : DiffUtil.ItemCallback<Jugador>() {
        override fun areItemsTheSame(oldItem: Jugador, newItem: Jugador): Boolean =
            oldItem.id_jugador == newItem.id_jugador

        override fun areContentsTheSame(oldItem: Jugador, newItem: Jugador): Boolean =
            oldItem == newItem
    }
}
