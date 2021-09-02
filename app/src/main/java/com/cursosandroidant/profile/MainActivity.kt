package com.cursosandroidant.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.cursosandroidant.profile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var lat: Double = 0.0
    private var long: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateUI()

        binding.tvLocation.setOnClickListener {
            binding.tvLocation.text = "Lat: $lat, Long: $long"
        }
    }

    private fun updateUI(name:String="Cursos Android ANT"
                         , email:String="cursosandroidant@gmail.com"
                         , website:String="https://github.com/gallopelado"
                         , phone:String="+52 555 673", lat:Double=-25.3448, long:Double=-57.5813) {
        binding.tvName.text = name
        binding.tvEmail.text = email
        binding.tvWebsite.text = website
        binding.tvPhone.text = phone
        this.lat = lat
        this.long = long
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_edit){
            // Como agregar otra actividad a través de intents
            val intent = Intent(this, EditActivity::class.java)
            // Pasarle datos a esa activity
            intent.putExtra(getString(R.string.key_name), binding.tvName.text)
            intent.putExtra(getString(R.string.key_email), binding.tvEmail.text.toString())
            intent.putExtra(getString(R.string.key_website), binding.tvWebsite.text.toString())
            intent.putExtra(getString(R.string.key_phone), binding.tvPhone.text)
            intent.putExtra(getString(R.string.key_lat), lat.toString())
            intent.putExtra(getString(R.string.key_long), long.toString())

            //startActivity(intent) <- Solo lanzamiento
            startActivityForResult(intent, RC_EDIT) // <- lanzamiento y espera de respuesta
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == RESULT_OK){
            if(requestCode == RC_EDIT){
                val name = data?.getStringExtra(getString(R.string.key_name)) ?: ""
                val email = data?.getStringExtra(getString(R.string.key_email)) ?: ""
                val website = data?.getStringExtra(getString(R.string.key_website)) ?: ""
                val phone = data?.getStringExtra(getString(R.string.key_phone)) ?: ""
                val lat = data?.getStringExtra(getString(R.string.key_lat))?.toDouble() ?: 0.0
                val long = data?.getStringExtra(getString(R.string.key_long))?.toDouble() ?: 0.0

                updateUI(name, email, website, phone, lat, long)
            }
        }
    }

    companion object {
        private const val RC_EDIT = 21
    }
}