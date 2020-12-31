package com.example.instalock.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.instalock.R
import com.example.instalock.viewmodels.ChampionViewModel
import kotlinx.android.synthetic.main.fragment_c_overview.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class COverviewFragment : Fragment() {

    private val championViewModel: ChampionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_c_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
    }

    private fun observe() {
        disableSliders()
        championViewModel.getDetailChampion(ChampionViewModel.currentName)
        championViewModel.detailChampionData.observe(viewLifecycleOwner, Observer {
            GlobalScope.launch(Dispatchers.IO) {
                val magic = it.magicRating
                val physical = it.physicalRating
                val defense = it.defenseRating
                val difficulty = it.difficultyRating
                launch(Dispatchers.Main) {
                    rating_defense.value = defense.toFloat() * 10F
                    rating_difficulty.value = difficulty.toFloat() * 10F
                    rating_magic.value = magic.toFloat() * 10F
                    rating_physical.value = physical.toFloat() * 10F
                }
            }
        })
    }

    private fun disableSliders() {
        rating_defense.isEnabled = false
        rating_difficulty.isEnabled = false
        rating_magic.isEnabled = false
        rating_physical.isEnabled = false
    }
}