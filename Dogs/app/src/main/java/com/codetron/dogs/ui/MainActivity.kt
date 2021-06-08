package com.codetron.dogs.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.codetron.dogs.R
import com.codetron.dogs.databinding.ActivityMainBinding
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding

    val sharedPref: SharedPreferences by lazy {
        getSharedPreferences(
            KEY_PREFS,
            Context.MODE_PRIVATE
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fcv_main) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)

        if (savedInstanceState == null) {
            val stateMode = sharedPref.getBoolean(KEY_MODE, false)
            AppCompatDelegate.setDefaultNightMode(
                if (stateMode) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    companion object {
        const val KEY_PREFS = "KEY_PREFS"
        const val KEY_MODE = "KEY_MODE"
    }

}