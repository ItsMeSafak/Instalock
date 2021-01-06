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
import com.example.instalock.models.MYMastery
import com.example.instalock.utils.MyMasteryAdapter
import com.example.instalock.viewmodels.SummonerViewModel
import com.google.android.material.snackbar.Snackbar
import com.merakianalytics.orianna.types.core.championmastery.ChampionMastery
import kotlinx.android.synthetic.main.fragment_p_champions.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PChampionsFragment : Fragment() {

    private val summonerViewModel: SummonerViewModel by activityViewModels()
    private val listofMastery: ArrayList<MYMastery> = arrayListOf()
    private val adapter: MyMasteryAdapter = MyMasteryAdapter(listofMastery)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_p_champions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observe()
    }

    private fun initView() {
        rv_masteries.adapter = adapter
        rv_masteries.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun observe() {
        summonerViewModel.getMasteries()
        summonerViewModel.masteryData.observe(viewLifecycleOwner, Observer {
            GlobalScope.launch(Dispatchers.IO) {
                listofMastery.clear()
                listofMastery.addAll(it)
                launch(Dispatchers.Main) {
                    adapter.notifyDataSetChanged()
                    pb_loading.visibility = View.INVISIBLE
                }
            }
        })

        summonerViewModel.failed.observe(viewLifecycleOwner, Observer {
            pb_loading.visibility = View.INVISIBLE
            Snackbar.make(rv_masteries, it, Snackbar.LENGTH_LONG).show()
        })
    }

}