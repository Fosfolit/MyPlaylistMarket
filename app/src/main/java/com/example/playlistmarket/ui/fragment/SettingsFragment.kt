package com.example.playlistmarket.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.example.playlistmarket.R
import com.example.playlistmarket.databinding.FragmentSettingsBinding
import com.example.playlistmarket.ui.viewModel.SettingsViewModel
import org.koin.android.ext.android.inject


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingsViewModel by inject()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupThemeSwitch()
        setupBackButton()
        setupShareButton()
        setupUserAgreementLink()
        setupSupportEmail()
    }


    private fun observeViewModel(){
        viewModel.themeMode.observe(viewLifecycleOwner) { nightMode ->
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
            parentFragmentManager.popBackStackImmediate()
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