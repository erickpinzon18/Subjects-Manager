package com.miguelerick.subjectsmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase

class DetallesMateriaActivity : AppCompatActivity() {
    private lateinit var tvIdMateria : TextView
    private lateinit var tvNombreMateria : TextView
    private lateinit var tvProfesorMateria : TextView
    private lateinit var tvDescripcionMateria : TextView
    private lateinit var tvDiasMateria : TextView
    private lateinit var btnActualizar : Button
    private lateinit var btnEliminar : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_materia)
        tvIdMateria = findViewById(R.id.tvIdMateria)
        tvNombreMateria = findViewById(R.id.tvNombreMateria)
        tvProfesorMateria = findViewById(R.id.tvProfesorMateria)
        tvDescripcionMateria = findViewById(R.id.tvDescripcionMateria)
        tvDiasMateria = findViewById(R.id.tvDiasMateria)
        btnActualizar = findViewById(R.id.btnActualizar)
        btnEliminar = findViewById(R.id.btnEliminar)

        tvIdMateria.text = intent.getStringExtra("id")
        tvNombreMateria.text = intent.getStringExtra("nombre")
        tvProfesorMateria.text = intent.getStringExtra("profesor")
        tvDescripcionMateria.text = intent.getStringExtra("descripcion")
        tvDiasMateria.text = intent.getStringExtra("dias")

        btnActualizar.setOnClickListener {
            openUpdateDialog(intent.getStringExtra("id").toString(), intent.getStringExtra("nombre").toString())
        }

        btnEliminar.setOnClickListener {
            deleteMateria(intent.getStringExtra("id").toString())
        }
    }

    private fun openUpdateDialog(id: String, nombre: String) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)

        val etNombreMateria = mDialogView.findViewById<EditText>(R.id.etNombreMateria)
        val etProfesorMateria = mDialogView.findViewById<EditText>(R.id.etProfesorMateria)
        val etDescripcionMateria = mDialogView.findViewById<EditText>(R.id.etDescripcionMateria)
        val cbLunes = mDialogView.findViewById<CheckBox>(R.id.cbLunes)
        val cbMartes = mDialogView.findViewById<CheckBox>(R.id.cbMartes)
        val cbMiercoles = mDialogView.findViewById<CheckBox>(R.id.cbMiercoles)
        val cbJueves = mDialogView.findViewById<CheckBox>(R.id.cbJueves)
        val cbViernes = mDialogView.findViewById<CheckBox>(R.id.cbViernes)
        val btnGuardar = mDialogView.findViewById<Button>(R.id.btnGuardar)

        etNombreMateria.setText(intent.getStringExtra("nombre").toString())
        etProfesorMateria.setText(intent.getStringExtra("profesor").toString())
        etDescripcionMateria.setText(intent.getStringExtra("descripcion").toString())

        val strDias = intent.getStringExtra("dias").toString()
        val sbstrDias = strDias.split(",")
        sbstrDias.forEach {
            if (it == "Lunes") cbLunes.isChecked = true
            if (it == "Martes") cbMartes.isChecked = true
            if (it == "Miercoles") cbMiercoles.isChecked = true
            if (it == "Jueves") cbJueves.isChecked = true
            if (it == "Viernes") cbViernes.isChecked = true
        }

        mDialog.setTitle("Actualizar los datos de ${nombre}")
        val alertDialog = mDialog.create()
        alertDialog.show()

        // Código dle botón de guardar
        btnGuardar.setOnClickListener {
            var dias : String = ""

            if (cbLunes.isChecked) dias += "Lunes,"
            if (cbMartes.isChecked) dias += "Martes,"
            if (cbMiercoles.isChecked) dias += "Miercoles,"
            if (cbJueves.isChecked) dias += "Jueves,"
            if (cbViernes.isChecked) dias += "Viernes,"
            updateMateria(id, etNombreMateria.text.toString(), etProfesorMateria.text.toString(), etDescripcionMateria.text.toString(), dias)
            Toast.makeText(applicationContext, "Se actualizó correctamente", Toast.LENGTH_SHORT).show()

            tvNombreMateria.text = etNombreMateria.text.toString()
            tvProfesorMateria.text = etProfesorMateria.text.toString()
            tvDescripcionMateria.text = etDescripcionMateria.text.toString()
            tvDiasMateria.text = dias.toString()

            // Cerrar el dialog
            alertDialog.dismiss()
        }
    }

    private fun updateMateria(id: String, nombre: String, profesor: String, descripcion: String, dias: String) {
        val database = FirebaseDatabase.getInstance().getReference("Materia").child((id))
        val infoMateria = MateriaModel(id, nombre, profesor, descripcion, dias)
        database.setValue(infoMateria)
    }

    private fun deleteMateria(id: String) {
        val database = FirebaseDatabase.getInstance().getReference("Materia").child((id))
        val mTask = database.removeValue()
        mTask.addOnSuccessListener {
            Toast.makeText(this, "Materia eliminado correctamente", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ConsultarMateriaActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener { e ->
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}