package com.example.playlistmarket

import android.os.Bundle
    import androidx.activity.enableEdgeToEdge
    import androidx.appcompat.app.AppCompatActivity
    import androidx.appcompat.widget.Toolbar

class NewEmptyActivity : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(R.layout.activity_new_empty)
            val toolbar: Toolbar = findViewById(R.id.buttonBack)
            toolbar.setOnClickListener {
                finish()
            }
        }


    }