package com.example.playlistmarket.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmarket.R
import com.example.playlistmarket.databinding.ActivitySettingsBinding
import com.example.playlistmarket.ui.viewModel.SettingsViewModel

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var viewModel: SettingsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setViewModel()
        myStyle()
        myBack()
        myShare()
        myHelper()
        myUserText()
    }
    private fun setViewModel(){
        viewModel = ViewModelProvider(this)[SettingsViewModel::class.java]
        viewModel.setContext(this)
        viewModel.observeTheme.observe(this) { nightMode ->
            AppCompatDelegate.setDefaultNightMode(nightMode)
            if (nightMode==2){
                binding.textViewStyle.isChecked = true
            }else{
                binding.textViewStyle.isChecked = false
            }
        }
    }
    private fun myStyle(){
        binding.textViewStyle.setOnClickListener {
            viewModel.settheme(binding.textViewStyle.isChecked)
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