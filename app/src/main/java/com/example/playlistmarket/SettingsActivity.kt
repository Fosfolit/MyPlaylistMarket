package com.example.playlistmarket

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import com.google.gson.Gson

class SettingsActivity : AppCompatActivity() {
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var textViewStyle: Switch
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        textViewStyle = findViewById(R.id.textViewStyle)
        sharedPrefs = getSharedPreferences(PRACTICUM_EXAMPLE_PREFERENCES, MODE_PRIVATE)
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            textViewStyle.isChecked = true
        } else {
            textViewStyle.isChecked = false
        }
        myStyle()
        myBack()
        myShare()
        myHelper()
        myUserText()
    }
    private fun myStyle(){
        textViewStyle.setOnClickListener {
            if (textViewStyle.isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                sharedPrefs.edit()
                    .remove("style")
                    .putBoolean("style", true)
                    .apply()
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPrefs.edit()
                    .remove("style")
                    .putBoolean("style", false)
                    .apply()
            }
        }
    }
    private fun myBack(){
        val toolbar: Toolbar = findViewById(R.id.buttonBack)
        toolbar.setOnClickListener {
            finish()
        }}
    private fun myShare(){
        val textViewShare: TextView = findViewById(R.id.textViewShare)
        textViewShare.setOnClickListener {
            val shareIntent = Intent().apply{
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, getString(R.string.titleYandex))
                type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent,getString(R.string.share)))
        }
    }
    private fun myUserText(){
        val buttonUserText: TextView = findViewById(R.id.buttonUserText)
        buttonUserText.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.titleYandexText)))
            startActivity(browserIntent)
        }
    }
    private fun myHelper(){
        val buttonHelper: TextView = findViewById(R.id.buttonHelper)
        buttonHelper.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))
            emailIntent.putExtra(Intent.EXTRA_EMAIL, getString(R.string.mailMain))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.spsAdmin))
            emailIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.spsPeople))
            startActivity(emailIntent)
        }
    }
}