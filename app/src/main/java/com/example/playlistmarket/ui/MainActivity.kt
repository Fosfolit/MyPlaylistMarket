package com.example.playlistmarket.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.playlistmarket.R
import com.example.playlistmarket.databinding.ActivityMainBinding
import com.example.playlistmarket.ui.viewModel.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject


private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by inject()
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        var vis: Boolean = false
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        viewModel.themeMode.observe(this) { nightMode ->
            AppCompatDelegate.setDefaultNightMode(nightMode)
        }
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { view, insets ->
            val imeVisible = insets.isVisible(WindowInsetsCompat.Type.ime())
            if(!vis){binding.linearLayoutNavigation.isVisible = !imeVisible}
            insets
        }

        bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.audioPlayerFragment -> {
                    vis = true
                    binding.linearLayoutNavigation.visibility = View.GONE
                }
                else -> {
                    vis = false
                    binding.linearLayoutNavigation.visibility = View.VISIBLE
                }
            }
        }
    }


}