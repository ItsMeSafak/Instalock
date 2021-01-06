package com.example.instalock.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instalock.R
import com.example.instalock.models.MYMatch
import com.example.instalock.utils.MyMatchHistoryAdapter
import com.example.instalock.viewmodels.SummonerViewModel
import com.google.android.material.snackbar.Snackbar
import com.merakianalytics.orianna.types.core.match.Match
import kotlinx.android.synthetic.main.fragment_p_match_history.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class PMatchHistoryFragment : Fragment() {

    private val summonerViewModel: SummonerViewModel by activityViewModels()
    private val listOfMatches: ArrayList<MYMatch> = arrayListOf()
    private val adapter: MyMatchHistoryAdapter = MyMatchHistoryAdapter(listOfMatches)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_p_match_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observe()
    }

    private fun initView() {
        rv_matches.adapter = adapter
        rv_matches.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun observe() {
        summonerViewModel.getMatches()
        summonerViewModel.matchData.observe(viewLifecycleOwner, Observer {
            GlobalScope.launch(Dispatchers.IO) {
                listOfMatches.clear()
                listOfMatches.addAll(it)
                launch(Dispatchers.Main) {
                    adapter.notifyDataSetChanged()
                    pb_loading_matches.visibility = View.INVISIBLE
                }
            }
        })

        summonerViewModel.failed.observe(viewLifecycleOwner, Observer {
            pb_loading_matches.visibility = View.INVISIBLE
            Snackbar.make(rv_matches, it, Snackbar.LENGTH_LONG).show()
        })
    }

}