package com.example.instalock.utils

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.instalock.ui.fragments.*

class TabAdapter(private val currentFrag: Fragment, fm: FragmentManager?, private val totalTabs: Int) : FragmentPagerAdapter(fm!!) {

    override fun getItem(position: Int): Fragment {
        return when (currentFrag) {
            is ChampionDetailFragment -> {
                when (position) {
                    0 -> {Log.d("CALLED", "HOI")
                        COverviewFragment()
                    }
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

    override fun getCount(): Int {
        return totalTabs
    }
}