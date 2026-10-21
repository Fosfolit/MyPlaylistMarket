package com.example.playlistmarket.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.playlistmarket.R
import com.example.playlistmarket.ui.fragment.MediaLbrary.MediaLibraryFavoriteTracksFragment
import com.example.playlistmarket.ui.fragment.MediaLbrary.MediaLibraryPlaylistFragment
import com.example.playlistmarket.ui.viewModel.fragment.MediaLibraryFavoriteTracksViewModel
import com.example.playlistmarket.ui.viewModel.fragment.MediaLibraryPlaylistViewModel

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    // Названия вкладок
    private val tabTitles = arrayOf(
       R.string.selectedTracks ,
        R.string.playlists)

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MediaLibraryFavoriteTracksFragment.newInstance()
            1 -> MediaLibraryPlaylistFragment.newInstance()
            else -> MediaLibraryFavoriteTracksFragment.newInstance()
        }
    }

    override fun getCount(): Int = tabTitles.size


}