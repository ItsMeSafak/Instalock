package com.example.instalock.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.instalock.R
import com.example.instalock.ui.activities.KEY_REGION
import com.example.instalock.ui.activities.KEY_SUMM_NAME
import com.example.instalock.utils.TabAdapter
import com.example.instalock.viewmodels.SummonerViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
 import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeFragment : Fragment() {

    private val summonerViewModel: SummonerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTabs()
        try {
            summonerViewModel.getSummoner(activity?.intent?.getStringExtra(KEY_SUMM_NAME)!!, activity?.intent?.getStringExtra(KEY_REGION)!!)
            observe()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private fun observe() {
        summonerViewModel.summonerData.observe(viewLifecycleOwner, Observer {
            GlobalScope.launch(Dispatchers.IO) {
                val url = it.profileIcon.image.url
                val summonerName = it.name
                val region = it.region.tag
                val level = it.level
                SummonerViewModel.region = it.region
                SummonerViewModel.summonerName = it.name
                launch(Dispatchers.Main) {
                    Glide.with(requireContext()).load(url).into(iv_profile_icon)
                    tv_summoner_name.text = summonerName
                    tv_region.text = getString(R.string.p_region, region)
                    tv_level.text = level.toString()
                    pb_loading.visibility = View.INVISIBLE
                }
            }
        })

        summonerViewModel.failed.observe(viewLifecycleOwner, Observer {
            Snackbar.make(iv_profile_icon, it, Snackbar.LENGTH_LONG)
                .setBackgroundTint(getColor(requireContext(), R.color.colorRed))
                .show()
        })
    }

    private fun initTabs() {
        vp_content.adapter = TabAdapter(this, requireActivity(), tab_layout.tabCount)
        val mediator = TabLayoutMediator(tab_layout, vp_content,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = getString(R.string.p_matches)
                    }
                    1 -> {
                        tab.text = getString(R.string.p_champions)
                    }
                }
            })
        mediator.attach()
    }
}