package com.miguelerick.subjectsmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var btnInsertar : Button
    private lateinit var btnConsultar : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnInsertar = findViewById(R.id.btnInsertar)
        btnConsultar = findViewById(R.id.btnConsultar)

        btnInsertar.setOnClickListener {
            val intent = Intent(this,  InsertarMateriaActivity::class.java)
            startActivity(intent)
        }

        btnConsultar.setOnClickListener {
            val intent = Intent(this,  ConsultarMateriaActivity::class.java)
            startActivity(intent)
        }
    }
}