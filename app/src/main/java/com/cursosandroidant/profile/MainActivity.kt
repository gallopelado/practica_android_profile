package com.cursosandroidant.profile

import android.app.SearchManager
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.cursosandroidant.profile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var imgUri: Uri? = null

    private var lat: Double = 0.0
    private var long: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicialización
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        updateUI()
        setupIntents()
    }

    private fun setupIntents() {
        /*
        * Asignar evento click al título, crear un intent y configurar para que este reciba
        * el texto del título y pueda ser buscado a través de algún browser instalado
        * en Android
        * */
        binding.tvName.setOnClickListener {
            val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
                putExtra(SearchManager.QUERY, binding.tvName.text.toString())
            }
            launchIntent(intent)
        }
        /*
        * Asignar evento click al email, crear un intent y configurar cree la accion de enviar
        * email a través del sistema
        * */
        binding.tvEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(binding.tvEmail.text.toString()))
                putExtra(Intent.EXTRA_SUBJECT, "From Kotlin course")
                putExtra(Intent.EXTRA_TEXT, "Hi I'm Android developer")
            }
            launchIntent(intent)
        }
        binding.tvWebsite.setOnClickListener {
            val webPage = Uri.parse(binding.tvWebsite.text.toString())
            val intent = Intent(Intent.ACTION_VIEW, webPage)
            launchIntent(intent)
        }
        binding.tvPhone.setOnClickListener {
//            val intent = Intent(Intent.ACTION_DIAL).apply {
//                val phone = (it as TextView).text
//                data = Uri.parse("tel:$phone")
//            }
            // Para cuando se desee llamar directamente
            // Se agrega el permiso en el manifest
            // luego se agrega el permiso manualmente en settings del sistema
            val intent = Intent(Intent.ACTION_CALL).apply {
                val phone = (it as TextView).text
                data = Uri.parse("tel:$phone")
            }
            launchIntent(intent)
        }
        binding.tvLocation.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("geo:0,0?q=$lat,$long(Cursos Android ANT)")
                `package` = "com.google.android.apps.maps"
            }
            launchIntent(intent)
        }
        binding.tvSettings.setOnClickListener {
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            launchIntent(intent)
        }
    }

    /* Desde el API 30 es preferible validar los intents, para saber si están disponibles en el
    * dispositivo */
    /*
    * También se añaden las queries en el AndroidManifest.xml para indicar permisos
    * que utilizamos en el cada intent, se recomiendo no abusar de ellos.
    * */
    private fun launchIntent(intent: Intent) {
        if(intent.resolveActivity(packageManager) != null){
            startActivity(intent)
        } else {
            Toast.makeText(this, getString(R.string.profile_error_no_resolve), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(name:String="Cursos Android ANT"
                         , email:String="cursosandroidant@gmail.com"
                         , website:String="https://github.com/gallopelado"
                         , phone:String="+52 555 673", lat:Double=-25.3448, long:Double=-57.5813) {
        binding.imgProfile.setImageURI(imgUri)
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
            intent.putExtra(getString(R.string.key_image), imgUri.toString())
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

    // Gestiona los datos provenientes de la otra activity, editactivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == RESULT_OK){
            if(requestCode == RC_EDIT){
                imgUri = Uri.parse(data?.getStringExtra(getString(R.string.key_image))) ?: null
                val name = data?.getStringExtra(getString(R.string.key_name)) ?: ""
                val email = data?.getStringExtra(getString(R.string.key_email)) ?: ""
                val website = data?.getStringExtra(getString(R.string.key_website)) ?: ""
                val phone = data?.getStringExtra(getString(R.string.key_phone)) ?: ""
                val lat = data?.getStringExtra(getString(R.string.key_lat))?.toDouble() ?: 0.0
                val long = data?.getStringExtra(getString(R.string.key_long))?.toDouble() ?: 0.0

                //updateUI(name, email, website, phone, lat, long)
                saveUserData(name, email, website, phone, lat, long)
            }
        }
    }

    private fun saveUserData(name: String?, email: String?, website: String?, phone: String?, lat: Double?, long: Double?) {
        sharedPreferences.edit {
            putString(getString(R.string.key_image), imgUri.toString())
            putString(getString(R.string.key_name), name)
            putString(getString(R.string.key_email), email)
            putString(getString(R.string.key_website), website)
            putString(getString(R.string.key_phone), phone)
            putString(getString(R.string.key_lat), lat.toString())
            putString(getString(R.string.key_long), long.toString())
            apply() // guardar estos datos
        }
        updateUI(name!!, email!!, website!!, phone!!, lat!!, long!!)
    }

    companion object {
        private const val RC_EDIT = 21
    }
}