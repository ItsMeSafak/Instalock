package com.example.instalock.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.instalock.R
import com.example.instalock.utils.AllChampionsAdapter
import com.example.instalock.viewmodels.ChampionViewModel
import com.google.android.material.snackbar.Snackbar
import com.merakianalytics.orianna.types.core.staticdata.Champion
import kotlinx.android.synthetic.main.fragment_all_champions.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

const val KEY_BUNDLE = "KEY_NAME"
const val KEY_REQUEST = "KEY_REQUEST"
class AllChampionsFragment : Fragment() {

    private val championViewModel: ChampionViewModel by activityViewModels()
    private val listOfChampions: ArrayList<Champion> = arrayListOf()
    private val adapter: AllChampionsAdapter = AllChampionsAdapter(listOfChampions, ::navigateToDetail)

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

        search_champion.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
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

        championViewModel.failed.observe(viewLifecycleOwner, Observer {
            Snackbar.make(search_champion, it, Snackbar.LENGTH_LONG).show()
        })
    }

    private fun navigateToDetail(championName: String) {
        setFragmentResult(KEY_REQUEST, bundleOf(Pair(KEY_BUNDLE, championName)))
        findNavController().navigate(R.id.championDetailFragment)
    }
}