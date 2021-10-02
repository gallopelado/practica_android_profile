package com.cursosandroidant.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            // otra manera distinta a poner el titulo en el android manifest
            it.title = getString(R.string.settings_title)
        }

        // ligar/añadir el fragment al activity
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container_main, SettingsFragment())
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}