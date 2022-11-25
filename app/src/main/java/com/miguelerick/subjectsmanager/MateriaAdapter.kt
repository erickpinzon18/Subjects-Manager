package com.miguelerick.subjectsmanager

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.FieldPosition

class MateriaAdapter(private val listaMaterias : ArrayList<MateriaModel>) : RecyclerView.Adapter<MateriaAdapter.ViewHolder>() {
    private lateinit var mListener : onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position : Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener) {
        mListener = clickListener
    }

    class ViewHolder (itemView : View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val tvNombreMateria : TextView = itemView.findViewById(R.id.tvNombreMateria)
        val tvNombreProfesor : TextView = itemView.findViewById(R.id.tvNombreProfesor)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.lista_materia_item, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val materiaSeleccionada = listaMaterias[position]
        holder.tvNombreMateria.text = materiaSeleccionada.nombre
        holder.tvNombreProfesor.text = materiaSeleccionada.profesor
    }

    override fun getItemCount(): Int {
        return listaMaterias.size
    }
}


