package com.example.playlistmarket.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.example.playlistmarket.databinding.ActivityNewEmptyBinding

class NewEmptyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewEmptyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityNewEmptyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBackButton()
    }

    private fun setupBackButton() {
        binding.buttonBack.setOnClickListener {
            finish()
        }
        ViewCompat.setOnApplyWindowInsetsListener(binding.buttonBack) { view, insets ->
            val statusBar = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.updatePadding(top = statusBar.top)
            insets }
    }
}