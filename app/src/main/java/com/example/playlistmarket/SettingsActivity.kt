package com.example.playlistmarket

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val toolbar: Toolbar = findViewById(R.id.buttonBackMain)
        setSupportActionBar(toolbar);
        toolbar.setOnClickListener {
            finish()
        }
        val textViewStyle: Switch = findViewById(R.id.textViewStyle)
        textViewStyle.setOnClickListener {
            if (textViewStyle.isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
          }
        val textViewShare: TextView = findViewById(R.id.textViewShare)
        textViewShare.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SENDTO)
            startActivity(Intent.createChooser(shareIntent,"@string/titleYandex"))
        }
        val buttonHelper: TextView = findViewById(R.id.buttonHelper)
        buttonHelper.setOnClickListener {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))
        emailIntent.putExtra(Intent.EXTRA_EMAIL, "@string/mailMain")
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "@string/spsAdmin")
        emailIntent.putExtra(Intent.EXTRA_TEXT, "@string/spsPeople")
        startActivity(emailIntent)
        }
        val buttonUserText: TextView = findViewById(R.id.buttonUserText)
        buttonUserText.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.titleYandexText)))
            startActivity(browserIntent)
        }
    }
}