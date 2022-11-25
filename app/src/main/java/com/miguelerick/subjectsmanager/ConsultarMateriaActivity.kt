package com.miguelerick.subjectsmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class ConsultarMateriaActivity : AppCompatActivity() {
    private lateinit var rvListaMaterias : RecyclerView
    private lateinit var tvCargando : TextView
    private lateinit var listaMaterias : ArrayList<MateriaModel>
    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consultar_materia)
        rvListaMaterias = findViewById(R.id.rvListaMaterias)
        tvCargando = findViewById(R.id.tvCargando)

        rvListaMaterias.layoutManager = LinearLayoutManager(this)
        rvListaMaterias.setHasFixedSize(true)

        listaMaterias = arrayListOf<MateriaModel>()
        getMaterias()
    }

    private fun getMaterias() {
        rvListaMaterias.visibility = View.GONE
        tvCargando.visibility = View.VISIBLE

        database = FirebaseDatabase.getInstance().getReference("Materia")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listaMaterias.clear()
                if (snapshot.exists()) {
                    for(empSnap in snapshot.children) {
                        val empData = empSnap.getValue(MateriaModel::class.java)
                        listaMaterias.add(empData!!)
                    }
                    val adapter = MateriaAdapter(listaMaterias)
                    rvListaMaterias.adapter = adapter

                    adapter.setOnItemClickListener(object : MateriaAdapter.onItemClickListener {
                        override fun onItemClick(position : Int) {
                            val intent = Intent(this@ConsultarMateriaActivity, DetallesMateriaActivity::class.java)

                            //Agregamos los extras
                            intent.putExtra("id", listaMaterias[position].id)
                            intent.putExtra("nombre", listaMaterias[position].nombre)
                            intent.putExtra("profesor", listaMaterias[position].profesor)
                            intent.putExtra("descripcion", listaMaterias[position].descripcion)
                            intent.putExtra("dias", listaMaterias[position].dias)

                            startActivity(intent)
                        }
                    })
                    rvListaMaterias.visibility = View.VISIBLE
                    tvCargando.visibility = View.GONE
                }
            }
            override fun onCancelled(error : DatabaseError) {
                Toast.makeText(this@ConsultarMateriaActivity, "ERROR: ${error}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}