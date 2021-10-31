package com.cursosandroidant.profile

import android.os.Bundle
import androidx.core.content.edit
import androidx.preference.*

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val deleteUserDataPreference = findPreference<Preference>(getString(R.string.preferences_key_delete_data))
        deleteUserDataPreference?.setOnPreferenceClickListener {
            val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
            sharedPreference.edit {
                putString(getString(R.string.key_image), null)
                putString(getString(R.string.key_name), null)
                putString(getString(R.string.key_email), null)
                putString(getString(R.string.key_website), null)
                putString(getString(R.string.key_phone), null)
                putString(getString(R.string.key_lat), null)
                putString(getString(R.string.key_long), null)
                apply()
            }
            true
        }

        val switchPreferenceCompat = findPreference<SwitchPreferenceCompat>(getString(R.string.preferences_key_enable_clicks))
        val listPreference = findPreference<ListPreference>(getString(R.string.preferences_key_ui_img_size))

        // borra todos los datos de preferences
        val restoreAllPreference = findPreference<Preference>(getString(R.string.preferences_key_restore_data))
        restoreAllPreference?.setOnPreferenceClickListener {
            val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
            sharedPreference.edit().clear().apply()

            switchPreferenceCompat?.isChecked = true
            listPreference?.value = getString(R.string.preferences_key_img_size_large)

            true
        }

        val defaultPreferences = findPreference<Preference>(getString(R.string.preferences_key_set_default_settings))
        defaultPreferences?.setOnPreferenceClickListener {
            switchPreferenceCompat?.isChecked = true
            listPreference?.value = getString(R.string.preferences_key_img_size_large)
            true
        }
    }
}