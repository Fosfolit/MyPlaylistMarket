package com.example.playlistmarket.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.example.playlistmarket.R
import com.example.playlistmarket.databinding.ActivitySettingsBinding
import com.example.playlistmarket.ui.viewModel.SettingsViewModel
import org.koin.android.ext.android.inject

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private val viewModel: SettingsViewModel by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeViewModel()
        setupThemeSwitch()
        setupBackButton()
        setupShareButton()
        setupUserAgreementLink()
        setupSupportEmail()

    }


    private fun observeViewModel(){
        viewModel.themeMode.observe(this) { nightMode ->
            AppCompatDelegate.setDefaultNightMode(nightMode)
            if (nightMode == 2) {
                binding.textViewStyle.isChecked = true
            } else {
                binding.textViewStyle.isChecked = false
            }
        }
    }



    private fun setupThemeSwitch() {
        binding.textViewStyle.setOnClickListener {
            viewModel.saveUpdateTheme(binding.textViewStyle.isChecked)
        }
    }

    private fun setupBackButton()  {
        binding.buttonBack.setOnClickListener {
            finish()
        }
        ViewCompat.setOnApplyWindowInsetsListener(binding.buttonBack) { view, insets ->
            val statusBar = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.updatePadding(top = statusBar.top)
            insets }
    }

    private fun setupShareButton() {
        binding.textViewShare.setOnClickListener {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, getString(R.string.titleYandex))
                type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent, getString(R.string.share)))
        }
    }

    private fun setupUserAgreementLink() {
        binding.buttonUserText.setOnClickListener {
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.titleYandexText)))
            startActivity(browserIntent)
        }
    }

    private fun setupSupportEmail() {
        binding.buttonHelper.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))
            emailIntent.putExtra(Intent.EXTRA_EMAIL, getString(R.string.mailMain))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.spsAdmin))
            emailIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.spsPeople))
            startActivity(emailIntent)
        }
    }
}
