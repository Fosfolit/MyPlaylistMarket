package com.example.playlistmarket.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.playlistmarket.R
import com.example.playlistmarket.databinding.MediaLibraryScreenBinding
import com.example.playlistmarket.ui.ViewPagerAdapter
import com.example.playlistmarket.ui.fragment.MediaLbrary.MediaLibraryFavoriteTracksFragment
import com.example.playlistmarket.ui.fragment.MediaLbrary.MediaLibraryPlaylistFragment
import com.example.playlistmarket.ui.viewModel.MediaLibraryViewModel
import com.google.android.material.tabs.TabLayout
import org.koin.android.ext.android.inject

class MediaLibraryFragment: Fragment()   {
    private  var _binding: MediaLibraryScreenBinding? = null
    private  val binding get() = _binding!!
    private val viewModel: MediaLibraryViewModel by inject()

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var adapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MediaLibraryScreenBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Инициализация
        tabLayout = view.findViewById(R.id.tab_layout)
        viewPager = view.findViewById(R.id.fragment_container)

        // Создаем адаптер (ВАЖНО: используем childFragmentManager)
        adapter = ViewPagerAdapter(childFragmentManager)

        // Устанавливаем адаптер
        viewPager.adapter = adapter

        // Связываем TabLayout с ViewPager
        tabLayout.setupWithViewPager(viewPager)







        startingFragment(savedInstanceState)
        //setTabLayout()
        observeMLState()
        setupBackButton()
    }

    private fun startingFragment(savedInstanceState: Bundle?){
        if(savedInstanceState == null){
            childFragmentManager.beginTransaction()
                .add(R.id.fragment_container, MediaLibraryFavoriteTracksFragment())
                .commit()
        }
    }

    private fun setTabLayout(){
        val tabLayout = binding.tabLayout
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val position = tab?.position ?: -1
                viewModel.switchTabs(position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    fun observeMLState(){
        viewModel.observeMediaLibraryState.observe(viewLifecycleOwner) {
            when(it.tabsStatus){
                0 -> {
                    childFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, MediaLibraryFavoriteTracksFragment())
                        .addToBackStack("my_backstack")
                        .setReorderingAllowed(true)
                        .commit()
                }

                1 -> {
                    childFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, MediaLibraryPlaylistFragment())
                        .addToBackStack("my_backstack")
                        .setReorderingAllowed(true)
                        .commit()
                }

                else->{}
            }
        }
    }
    private fun setupBackButton() {
        val toolbar: Toolbar = binding.buttonBack
        toolbar.setOnClickListener {
            parentFragmentManager.popBackStackImmediate()
        }
        ViewCompat.setOnApplyWindowInsetsListener(toolbar) { view, insets ->
            val statusBar = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.updatePadding(top = statusBar.top)
            insets }
    }

}