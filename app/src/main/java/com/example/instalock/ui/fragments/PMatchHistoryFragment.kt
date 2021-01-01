package com.example.instalock.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.instalock.R
import com.example.instalock.viewmodels.SummonerViewModel
import com.merakianalytics.orianna.types.core.match.Match
import com.merakianalytics.orianna.types.core.championmastery.ChampionMasteries;


class PMatchHistoryFragment : Fragment() {

    private val summonerViewModel: SummonerViewModel by activityViewModels()
    private val listOfMatches: ArrayList<List<Match>> = arrayListOf()
//    private val adapter: AbilityAdapter = AbilityAdapter(listOfAbilities)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_p_match_history, container, false)
    }




}