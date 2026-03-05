package com.example.playlistmarket.ui.activity

import android.os.Bundle
    import androidx.activity.enableEdgeToEdge
    import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmarket.databinding.ActivityNewEmptyBinding

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