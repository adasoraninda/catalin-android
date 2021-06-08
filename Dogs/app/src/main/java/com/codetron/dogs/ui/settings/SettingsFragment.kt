package com.codetron.dogs.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.codetron.dogs.R
import com.codetron.dogs.ui.MainActivity
import kotlinx.serialization.ExperimentalSerializationApi

class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var MODE: String

    private lateinit var modePreference: SwitchPreferenceCompat

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        init()
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    private fun init() {
        MODE = resources.getString(R.string.key_mode)

        modePreference = findPreference<SwitchPreferenceCompat>(MODE) as SwitchPreferenceCompat

    }

    @ExperimentalSerializationApi
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == MODE) {
            AppCompatDelegate.setDefaultNightMode(
                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.MODE_NIGHT_NO
                } else {
                    AppCompatDelegate.MODE_NIGHT_YES
                }
            )

            val pref = (activity as MainActivity).sharedPref

            pref.edit().putBoolean(
                MainActivity.KEY_MODE, !pref.getBoolean(MainActivity.KEY_MODE, false)
            ).apply()
        }
    }

}