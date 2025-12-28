package com.example.playlistmarket.ui

import android.os.Bundle
    import androidx.activity.enableEdgeToEdge
    import androidx.appcompat.app.AppCompatActivity
    import androidx.appcompat.widget.Toolbar
import com.example.playlistmarket.R
import com.example.playlistmarket.databinding.ActivityMainBinding
import com.example.playlistmarket.databinding.ActivityNewEmptyBinding
import com.example.playlistmarket.databinding.ActivitySettingsBinding

class NewEmptyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewEmptyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            binding = ActivityNewEmptyBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.buttonBack.setOnClickListener {
                finish()
            }
        }


    }