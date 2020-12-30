package com.example.instalock.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.instalock.R
import com.example.instalock.utils.AllChampionsAdapter
import com.example.instalock.viewmodels.ChampionViewModel
import com.merakianalytics.orianna.types.core.staticdata.Champion
import kotlinx.android.synthetic.main.fragment_all_champions.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AllChampionsFragment : Fragment() {

    private val championViewModel: ChampionViewModel by activityViewModels()
    private val listOfChampions: ArrayList<Champion> = arrayListOf()
    private val adapter: AllChampionsAdapter = AllChampionsAdapter(listOfChampions)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_champions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observe()
    }

    private fun initView() {
        rv_champions.adapter = adapter
        rv_champions.layoutManager = GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
    }

    private fun observe() {
        championViewModel.getChampions()
        championViewModel.championData.observe(viewLifecycleOwner, Observer {
            GlobalScope.launch(IO) {
                listOfChampions.clear()
                listOfChampions.addAll(it)
                launch(Dispatchers.Main) {
                    adapter.notifyDataSetChanged()
                    pb_loading.visibility = View.INVISIBLE
                }
            }
        })
    }
}