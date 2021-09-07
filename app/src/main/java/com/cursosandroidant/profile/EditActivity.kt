package com.cursosandroidant.profile

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.cursosandroidant.profile.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {

    private lateinit var binding:ActivityEditBinding
    private var imgUri: Uri? = null

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
            listObjects.forEach { item ->
                item.setOnFocusChangeListener { v, hasFocus ->
                    if(hasFocus){
                       item.text?.let {
                            item.setSelection(it.length)
                        }
                    }
                }
            }
            btnSelectPhoto.setOnClickListener {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    type = "image/jpeg"
                }
                startActivityForResult(intent, RC_GALLERY)
            }
        }
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
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK) {
            if(requestCode == RC_GALLERY) {
                imgUri = data?.data
                binding.imgProfile.setImageURI(imgUri)
            }
        }
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
        setResult(RESULT_OK, intent)
        finish()
    }

    companion object {
        private const val  RC_GALLERY = 22
    }
}