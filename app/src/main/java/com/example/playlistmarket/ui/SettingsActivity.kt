package com.example.playlistmarket.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmarket.Creator.provideThemeInteractor
import com.example.playlistmarket.R
import com.example.playlistmarket.databinding.ActivitySettingsBinding
import com.example.playlistmarket.domain.api.theme.ThemeInteractor

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var d : ThemeInteractor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        d = provideThemeInteractor(this)
       if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
           binding.textViewStyle.isChecked = true
        } else {
           binding.textViewStyle.isChecked = false
        }
        myStyle()
        myBack()
        myShare()
        myHelper()
        myUserText()
    }
    private fun myStyle(){
        binding.textViewStyle.setOnClickListener {
            d.loadTheme(object : ThemeInteractor.ThemeConsumer {
                override fun consume(theme: Boolean) {
                    runOnUiThread {
                        if (theme){
                            d.saveTheme(false)
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        } else{
                            d.saveTheme(true)
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                            }
                        }
                    }
                })

        }
    }
    private fun myBack(){
        binding.buttonBack.setOnClickListener {
            finish()
        }}
    private fun myShare(){
        binding.textViewShare.setOnClickListener {
            val shareIntent = Intent().apply{
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, getString(R.string.titleYandex))
                type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent,getString(R.string.share)))
        }
    }
    private fun myUserText(){
       binding.buttonUserText.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.titleYandexText)))
            startActivity(browserIntent)
        }
    }
    private fun myHelper(){
       binding.buttonHelper.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))
            emailIntent.putExtra(Intent.EXTRA_EMAIL, getString(R.string.mailMain))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.spsAdmin))
            emailIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.spsPeople))
            startActivity(emailIntent)
        }
    }
}