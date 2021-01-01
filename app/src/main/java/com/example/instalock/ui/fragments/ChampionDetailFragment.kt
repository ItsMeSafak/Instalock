package com.example.instalock.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.instalock.R
import com.example.instalock.utils.TabAdapter
import com.example.instalock.viewmodels.ChampionViewModel
import com.google.android.material.tabs.TabLayoutMediator
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
        initBottomButton()
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
        vp_content_champion.adapter = TabAdapter(this, requireActivity(), tab_layout_champion.tabCount)
        val mediator = TabLayoutMediator(tab_layout_champion, vp_content_champion,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = getString(R.string.c_overview)
                    }
                    1 -> {
                        tab.text = getString(R.string.c_abilities)
                    }
                    2 -> {
                        tab.text = getString(R.string.c_lore)
                    }
                }
            })
        mediator.attach()
    }

    private fun initBottomButton() {
        println("test222222")
        btn_back.setOnNavigationItemSelectedListener {
            findNavController().popBackStack()
        }
    }

}