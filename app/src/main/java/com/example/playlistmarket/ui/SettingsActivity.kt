package com.example.playlistmarket.ui

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.net.Uri
import android.os.Bundle
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import com.example.playlistmarket.Creator.provideStorageInteractor
import com.example.playlistmarket.R
import com.example.playlistmarket.domain.api.StorageInteractor

class SettingsActivity : AppCompatActivity() {
    private lateinit var textViewStyle: Switch
    private lateinit var d : StorageInteractor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        textViewStyle = findViewById(R.id.textViewStyle)
        d = provideStorageInteractor(this)
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
            d.loadTheme(object : StorageInteractor.ThemeConsumer {
                override fun consume(theme: Boolean) {
                    runOnUiThread {
                        if (theme){
                            d.saveTheme(!theme)
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        } else{
                            d.saveTheme(!theme)
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                            }
                        }
                    }
                })

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