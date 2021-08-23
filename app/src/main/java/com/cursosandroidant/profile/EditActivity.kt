package com.cursosandroidant.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.cursosandroidant.profile.databinding.ActivityEditBinding
import com.cursosandroidant.profile.databinding.ActivityMainBinding

class EditActivity : AppCompatActivity() {

    private lateinit var binding:ActivityEditBinding

    private lateinit var listObjects:ArrayList<com.google.android.material.textfield.TextInputEditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Agregar flecha de retroceso
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        listObjects = arrayListOf()
        with(binding){
            listObjects.addAll(listOf(etName, etEmail, etWebsite, etPhone, etLat, etLong))
            //listObjects.add(etName)
        }


        //Recibir el dato
        with(binding) {
            intent.extras?.let {
                etName.setText(it.getString(getString(R.string.key_name)))
                etEmail.setText(it.getString(getString(R.string.key_email)))
                etWebsite.setText(it.getString(getString(R.string.key_website)))
                etPhone.setText(it.getString(getString(R.string.key_phone)))
                etLat.setText(it.getString(getString(R.string.key_lat)))
                etLong.setText(it.getString(getString(R.string.key_long)))
            }
            // Poner el cursor al final de un campo que tiene datos
            /*etWebsite.setOnFocusChangeListener { v, hasFocus ->
                if(hasFocus){
                    binding.etWebsite.text?.let {
                        binding.etWebsite.setSelection(it.length)
                    }
                }
            }*/
            listObjects.forEach { item ->
                item.setOnFocusChangeListener { v, hasFocus ->
                    if(hasFocus){
                       item.text?.let {
                            item.setSelection(it.length)
                        }
                    }
                }
            }
        }

        /*with(binding){
            etName.setText(intent.extras?.getString(getString(R.string.key_name)))
            etEmail.setText(intent.extras?.getString(getString(R.string.key_email)))
            etWebsite.setText(intent.extras?.getString(getString(R.string.key_website)))
            etPhone.setText(intent.extras?.getString(getString(R.string.key_phone)))
            etLat.setText(intent.extras?.getString(getString(R.string.key_lat)))
            etLong.setText(intent.extras?.getString(getString(R.string.key_long)))
        }*/
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_save -> sendData()
            android.R.id.home -> onBackPressed()
        }
        /*
        if(item.itemId == R.id.action_save) {
            // Destruye esta actividad
            //finish()
            sendData()
        } else if(item.itemId == android.R.id.home) {
            onBackPressed() // en vez de finish
        }
         */
        return super.onOptionsItemSelected(item)
    }

    private fun sendData(){
        val intent = Intent()
        with(binding){
            intent.apply {
                putExtra(getString(R.string.key_name), etName.text.toString())
                putExtra(getString(R.string.key_email), etEmail.text.toString())
                putExtra(getString(R.string.key_website), etWebsite.text.toString())
                putExtra(getString(R.string.key_phone), etPhone.text.toString())
                putExtra(getString(R.string.key_lat), etLat.text.toString())
                putExtra(getString(R.string.key_long), etLong.text.toString())
            }
        }
        /*
        with(intent) {
            putExtra(getString(R.string.key_name), binding.etName.text.toString())
            putExtra(getString(R.string.key_email), binding.etEmail.text.toString())
            putExtra(getString(R.string.key_website), binding.etWebsite.text.toString())
            putExtra(getString(R.string.key_phone), binding.etPhone.text.toString())
            putExtra(getString(R.string.key_lat), binding.etLat.text.toString())
            putExtra(getString(R.string.key_long), binding.etLong.text.toString())
        }*/
        setResult(RESULT_OK, intent)
        finish()
    }
}