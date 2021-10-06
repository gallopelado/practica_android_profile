package com.cursosandroidant.profile

import android.os.Bundle
import androidx.core.content.edit
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager

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
    }
}