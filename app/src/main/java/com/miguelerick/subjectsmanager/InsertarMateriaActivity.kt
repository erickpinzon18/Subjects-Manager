package com.miguelerick.subjectsmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class InsertarMateriaActivity : AppCompatActivity() {

    private lateinit var etNombreMateria : EditText
    private lateinit var etProfesorMateria : EditText
    private lateinit var etDescripcionMateria : EditText
    private lateinit var cbLunes : CheckBox
    private lateinit var cbMartes : CheckBox
    private lateinit var cbMiercoles : CheckBox
    private lateinit var cbJueves : CheckBox
    private lateinit var cbViernes : CheckBox
    private lateinit var btnGuardar : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertar_materia)

        etNombreMateria = findViewById(R.id.etNombreMateria)
        etProfesorMateria = findViewById(R.id.etProfesorMateria)
        etDescripcionMateria = findViewById(R.id.etDescripcionMateria)
        cbLunes = findViewById(R.id.cbLunes)
        cbMartes = findViewById(R.id.cbMartes)
        cbMiercoles = findViewById(R.id.cbMiercoles)
        cbJueves = findViewById(R.id.cbJueves)
        cbViernes = findViewById(R.id.cbViernes)
        btnGuardar = findViewById(R.id.btnGuardar)

        val database = FirebaseDatabase.getInstance().getReference("Materia")

        btnGuardar.setOnClickListener {
            if (etNombreMateria.text.isEmpty()) Toast.makeText(this, "Rellene el campo materia", Toast.LENGTH_SHORT).show()
            else if (etProfesorMateria.text.isEmpty()) Toast.makeText(this, "Rellene el campo profesor", Toast.LENGTH_SHORT).show()
            else if (etDescripcionMateria.text.isEmpty()) Toast.makeText(this, "Rellene el campo descripción", Toast.LENGTH_SHORT).show()

            else if (!cbLunes.isChecked && !cbMartes.isChecked && !cbMiercoles.isChecked && !cbJueves.isChecked && !cbViernes.isChecked)
                Toast.makeText(this, "Seleccione al menos un día", Toast.LENGTH_SHORT).show()

            else {
                val nombre = etNombreMateria.text.toString()
                val profesor = etProfesorMateria.text.toString()
                val descripcion = etDescripcionMateria.text.toString()
                var dias : String = ""

                if (cbLunes.isChecked) dias += "Lunes,"
                if (cbMartes.isChecked) dias += "Martes,"
                if (cbMiercoles.isChecked) dias += "Miercoles,"
                if (cbJueves.isChecked) dias += "Jueves,"
                if (cbViernes.isChecked) dias += "Viernes,"

                dias = dias.substring(0, dias.length-1)

                val id = database.push().key!!
                val materia = MateriaModel(id, nombre, profesor, descripcion, dias)

                database.child(id).setValue(materia)
                    .addOnCompleteListener {
                        Toast.makeText(this, "Materia insertada", Toast.LENGTH_SHORT).show()
                        etNombreMateria.text.clear()
                        etProfesorMateria.text.clear()
                        etDescripcionMateria.text.clear()

                        cbLunes.isChecked = false
                        cbMartes.isChecked = false
                        cbMiercoles.isChecked = false
                        cbJueves.isChecked = false
                        cbViernes.isChecked = false
                    }.addOnFailureListener { e ->
                        Toast.makeText(this, "ERROR ${e.message}", Toast.LENGTH_SHORT).show()
                        etNombreMateria.text.clear()
                        etProfesorMateria.text.clear()
                        etDescripcionMateria.text.clear()

                        cbLunes.isChecked = false
                        cbMartes.isChecked = false
                        cbMiercoles.isChecked = false
                        cbJueves.isChecked = false
                        cbViernes.isChecked = false
                    }
            }
        }
    }
}