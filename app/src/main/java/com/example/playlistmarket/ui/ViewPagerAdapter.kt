package com.example.playlistmarket.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    Fragment: FragmentActivity,
    private val fragments: List<Fragment>,
    private val titles: List<String>
) : FragmentStateAdapter(Fragment) {

    override fun createFragment(position: Int): Fragment = fragments[position]

    fun getPageTitle(position: Int): String = titles[position]


    override fun getItemCount(): Int = fragments.size
}