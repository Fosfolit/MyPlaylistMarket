package com.example.playlistmarket.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmarket.R
import com.example.playlistmarket.databinding.FragmentMainMenuBinding
import com.example.playlistmarket.ui.viewModel.MainViewModel
import org.koin.android.ext.android.inject

class MainMenuFragment : Fragment() {

    private  var _binding: FragmentMainMenuBinding? = null
    private  val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainMenuBinding.inflate(inflater,container,false)


        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonSearch()
        buttonMedia()
        buttonSetting()
    }


    private fun buttonSearch(){
        binding.buttonSearch.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SearchFragment())
                .addToBackStack("my_backstack")
                .setReorderingAllowed(true)
                .commit()
        }
    }
    private fun buttonMedia(){
        binding.buttonMedia.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MediaLibraryFragment())
                .addToBackStack("my_backstack")
                .setReorderingAllowed(true)
                .commit()
        }
    }
    private fun buttonSetting(){
        binding.buttonSetting.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SettingsFragment())
                .addToBackStack("my_backstack")
                .setReorderingAllowed(true)
                .commit()
        }
    }
}