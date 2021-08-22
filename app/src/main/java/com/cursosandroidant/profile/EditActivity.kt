package com.cursosandroidant.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.cursosandroidant.profile.databinding.ActivityEditBinding
import com.cursosandroidant.profile.databinding.ActivityMainBinding

class EditActivity : AppCompatActivity() {

    private lateinit var binding:ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Agregar flecha de retroceso
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Recibir el dato
        with(binding){
            etName.setText(intent.extras?.getString(getString(R.string.key_name)))
            etEmail.setText(intent.extras?.getString(getString(R.string.key_email)))
            etWebsite.setText(intent.extras?.getString(getString(R.string.key_website)))
            etPhone.setText(intent.extras?.getString(getString(R.string.key_phone)))
            etLat.setText(intent.extras?.getString(getString(R.string.key_lat)))
            etLong.setText(intent.extras?.getString(getString(R.string.key_long)))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_save) {
            // Destruye esta actividad
            finish()
        } else if(item.itemId == android.R.id.home) {
            onBackPressed() // en vez de finish
        }
        return super.onOptionsItemSelected(item)
    }
}