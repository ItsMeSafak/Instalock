package com.example.instalock.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instalock.R
import com.example.instalock.utils.AbilityAdapter
import com.example.instalock.viewmodels.ChampionViewModel
import com.merakianalytics.orianna.types.core.staticdata.ChampionSpell
import kotlinx.android.synthetic.main.fragment_c_abilities.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CAbilitiesFragment : Fragment() {

    private val championViewModel: ChampionViewModel by activityViewModels()
    private val listOfAbilities: ArrayList<ChampionSpell> = arrayListOf()
    private val adapter: AbilityAdapter = AbilityAdapter(listOfAbilities)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_c_abilities, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observe()
    }

    private fun initView() {
        rv_abilities.adapter = adapter
        rv_abilities.layoutManager = LinearLayoutManager(requireContext(),  LinearLayoutManager.VERTICAL, false)
        rv_abilities.addItemDecoration(DividerItemDecoration(rv_abilities.context, DividerItemDecoration.VERTICAL))

    }

    private fun observe() {
        championViewModel.getDetailChampion(ChampionViewModel.currentName)
        championViewModel.detailChampionData.observe(viewLifecycleOwner, Observer {
            GlobalScope.launch(Dispatchers.IO) {
                listOfAbilities.clear()
                listOfAbilities.addAll(it.spells)
                launch(Dispatchers.Main) {
                    adapter.notifyDataSetChanged()
                }
            }
        })
    }
}