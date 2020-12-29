package com.example.instalock.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.instalock.ui.fragments.ChampionsFragment
import com.example.instalock.ui.fragments.MatchHistoryFragment


class TabAdapter(fm: FragmentManager?, private val totalTabs: Int) : FragmentPagerAdapter(fm!!) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                MatchHistoryFragment()
            }
            1 -> {
                ChampionsFragment()
            }
            else -> MatchHistoryFragment()
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}