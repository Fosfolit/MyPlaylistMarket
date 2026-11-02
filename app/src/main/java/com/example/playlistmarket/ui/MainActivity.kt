package com.example.playlistmarket.ui

import android.os.Bundle
import android.view.MenuItem
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
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        viewModel.themeMode.observe(this) { nightMode ->
            AppCompatDelegate.setDefaultNightMode(nightMode)
        }

        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { view, insets ->
            val imeVisible = insets.isVisible(WindowInsetsCompat.Type.ime())
            bottomNavigationView.isVisible = !imeVisible
            insets
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        bottomNavigationView.setupWithNavController(navController)

    }

    fun animBottomNavigation(){
        bottomNavigationView.setOnItemSelectedListener { menuItem: MenuItem ->
            var newIndex = getItemIndex(menuItem.itemId)
            var oldIndex = getItemIndex(bottomNavigationView.selectedItemId)
            when {
                newIndex > oldIndex -> {
                    navController.navigate(menuItem.itemId, null)
                }
                newIndex < oldIndex -> {
                    navController.navigate(menuItem.itemId, null)
                }
                else -> {}
            }
            true
        }
    }

    private fun getItemIndex(itemId: Int): Int {
        val menu = bottomNavigationView.menu
        for (i in 0 until menu.size()) {
            if (menu.getItem(i).itemId == itemId) {
                return i
            }
        }
        return -1 // если не найден
    }

}