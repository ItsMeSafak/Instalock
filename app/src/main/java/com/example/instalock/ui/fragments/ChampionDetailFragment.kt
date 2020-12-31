package com.example.instalock.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.instalock.R
import com.example.instalock.utils.TabAdapter
import com.example.instalock.viewmodels.ChampionViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_champion_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ChampionDetailFragment : Fragment() {

    private val championViewModel: ChampionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_champion_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener(KEY_REQUEST) { key, bundle ->
            observe(bundle.getString(KEY_BUNDLE)!!)
            ChampionViewModel.currentName = bundle.getString(KEY_BUNDLE)!!
        }
        initTabs()
    }

    private fun observe(championName: String) {
        championViewModel.getDetailChampion(championName)
        championViewModel.detailChampionData.observe(viewLifecycleOwner, Observer {
            GlobalScope.launch(Dispatchers.IO) {
                val splash = it.skins[0].splashImageURL
                val fullName = getString(R.string.champion_full, it.name, it.title)
                launch(Dispatchers.Main) {
                    Glide.with(requireContext()).load(splash).into(iv_splash)
                    tv_full_name.text = fullName
                }
            }
        })
    }

    private fun initTabs() {
        vp_content_champion.adapter = TabAdapter(this, activity?.supportFragmentManager, tab_layout_champion.tabCount)
        tab_layout_champion.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    vp_content_champion.currentItem = tab.position
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    vp_content_champion.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
        })
    }

}