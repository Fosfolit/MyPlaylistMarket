package com.example.playlistmarket.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.playlistmarket.ui.fragment.MediaLbrary.MediaLibraryFavoriteTracksFragment
import com.example.playlistmarket.ui.fragment.MediaLbrary.MediaLibraryPlaylistFragment
import com.example.playlistmarket.ui.viewModel.fragment.MediaLibraryFavoriteTracksViewModel
import com.example.playlistmarket.ui.viewModel.fragment.MediaLibraryPlaylistViewModel

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    // Названия вкладок
    private val tabTitles = arrayOf("Избранные треки", "Плейлисты")

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MediaLibraryFavoriteTracksFragment()
            1 -> MediaLibraryPlaylistFragment()
            else -> MediaLibraryFavoriteTracksFragment()
        }
    }

    override fun getCount(): Int = tabTitles.size

    override fun getPageTitle(position: Int): CharSequence = tabTitles[position]
}