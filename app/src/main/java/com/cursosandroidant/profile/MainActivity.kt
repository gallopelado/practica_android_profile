package com.cursosandroidant.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
            //binding.tvLocation.text = "Lat: $lat, Long: $long"
            // Como agregar otra actividad a través de intents
            startActivity(Intent(this, EditActivity::class.java))
        }
    }

    private fun updateUI(name:String="Cursos Android ANT"
                         , email:String="cursosandroidant@gmail.com"
                         , website:String="https://github.com/gallopelado"
                         , phone:String="+52 555 673") {
        binding.tvName.text = name
        binding.tvEmail.text = email
        binding.tvWebsite.text = website
        binding.tvPhone.text = phone
        lat = -25.3448
        long = -57.5813
    }
}