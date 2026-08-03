package com.example.playlistmarket.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.playlistmarket.R
import com.example.playlistmarket.databinding.FragmentMediaLibraryBinding
import com.example.playlistmarket.ui.viewModel.MediaLibraryViewModel
import com.google.android.material.tabs.TabLayout
import org.koin.android.ext.android.inject

class MediaLibraryFragment: Fragment()   {
    private  var _binding: FragmentMediaLibraryBinding? = null
    private  val binding get() = _binding!!
    private val viewModel: MediaLibraryViewModel by inject()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMediaLibraryBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment = childFragmentManager.findFragmentById(R.id.fragment_container_ml) as NavHostFragment
        navController = navHostFragment.navController

        val tabLayout: TabLayout = binding.tabLayout
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val position = tab?.position ?: 0
                viewModel.switchTabs(position)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        observeMLState()
    }


    private fun observeMLState(){
        viewModel.observeMediaLibraryState.observe(viewLifecycleOwner) {
            when(it.tabsStatus){
                0 -> {
                    navController.navigate(R.id.favoriteTracksFragment)
                }

                1 -> {
                    navController.navigate(R.id.playlistFragment)
                }
                else->{}
            }
        }
    }


}