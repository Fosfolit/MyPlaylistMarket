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
        myBack()
        myStyle()
        myShare()
        myHelper()
        myUserText()
    }
    private fun myStyle(){
        val textViewStyle: Switch = findViewById(R.id.textViewStyle)
        textViewStyle.setOnClickListener {
            if (textViewStyle.isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
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