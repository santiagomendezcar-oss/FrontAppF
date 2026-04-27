package com.example.myapplication.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.model.ResulPartido

class PartidoAdapter : ListAdapter<ResulPartido, PartidoAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        

        val fechaCorta = if (item.fecha_partido.length >= 10) item.fecha_partido.substring(0, 10) else item.fecha_partido
        
        // campos del backend para los datos del historial de partidos
        holder.text1.text = "${item.nombreEquipoLocal} ${item.goles_local} - ${item.goles_visita} ${item.nombreEquipoVisita}"
        holder.text2.text = "$fechaCorta @ ${item.estadio}"
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text1: TextView = view.findViewById(android.R.id.text1)
        val text2: TextView = view.findViewById(android.R.id.text2)
    }

    class DiffCallback : DiffUtil.ItemCallback<ResulPartido>() {
        override fun areItemsTheSame(oldItem: ResulPartido, newItem: ResulPartido): Boolean =
            oldItem.id_partido == newItem.id_partido

        override fun areContentsTheSame(oldItem: ResulPartido, newItem: ResulPartido): Boolean =
            oldItem == newItem
    }
}
