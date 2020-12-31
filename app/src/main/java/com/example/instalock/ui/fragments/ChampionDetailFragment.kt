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
import com.example.instalock.viewmodels.ChampionViewModel
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
        }
    }

    private fun observe(championName: String) {
        championViewModel.getDetailChampion(championName)
        championViewModel.detailChampionData.observe(viewLifecycleOwner, Observer {
            GlobalScope.launch(Dispatchers.IO) {
                val splash = it.skins[0].splashImageURL
                launch(Dispatchers.Main) {
                    Glide.with(requireContext()).load(splash).into(iv_splash)
                }
            }
        })
    }

}