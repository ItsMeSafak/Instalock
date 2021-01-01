package com.example.instalock.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.instalock.ui.fragments.*

class TabAdapter(private val currentFrag: Fragment, fragmentActivity: FragmentActivity, private val totalTabs: Int) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return totalTabs
    }

    override fun createFragment(position: Int): Fragment {
        return when (currentFrag) {
            is ChampionDetailFragment -> {
                when (position) {
                    0 -> COverviewFragment()
                    1 -> CAbilitiesFragment()
                    2 -> CLoreFragment()
                    else -> COverviewFragment()
                }
            }
            is HomeFragment -> {
                when (position) {
                    0 -> PMatchHistoryFragment()
                    1 -> PChampionsFragment()
                    else -> PMatchHistoryFragment()
                }
            }
            else -> PChampionsFragment()
        }
    }

}