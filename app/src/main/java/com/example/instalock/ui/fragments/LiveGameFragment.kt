package com.example.instalock.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.instalock.R
import com.example.instalock.utils.TeamAdapter
import com.example.instalock.viewmodels.LiveGameViewModel
import com.google.android.material.snackbar.Snackbar
import com.merakianalytics.orianna.types.core.spectator.Player
import kotlinx.android.synthetic.main.fragment_live_game.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LiveGameFragment : Fragment() {

    private val liveGameViewModel: LiveGameViewModel by activityViewModels()

    private val listOfYourTeam: ArrayList<Player> = arrayListOf()
    private val myTeamAdapter: TeamAdapter = TeamAdapter(listOfYourTeam)

    private val listOfEnemyTeam: ArrayList<Player> = arrayListOf()
    private val enemyTeamAdapter: TeamAdapter = TeamAdapter(listOfEnemyTeam)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_live_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observe()
    }

    private fun initView() {
        rv_your_team.adapter = myTeamAdapter
        rv_your_team.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        rv_enemy_team.adapter = enemyTeamAdapter
        rv_enemy_team.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        btn_refresh.setOnClickListener {
            liveGameViewModel.getLiveGame()
            pb_loading_live.visibility = View.VISIBLE
            layout_refresh.visibility = View.INVISIBLE
        }
    }

    private fun observe() {
        liveGameViewModel.getLiveGame()
        liveGameViewModel.liveGameData.observe(viewLifecycleOwner, Observer {
            GlobalScope.launch(Dispatchers.IO) {
                val imageUrl = it.yourchampion.skins[0].splashImageURL
                val championName = it.yourchampion.name

                val listOfTips = it.yourchampion.allyTips

                listOfYourTeam.clear()
                listOfEnemyTeam.clear()
                listOfEnemyTeam.addAll(it.enemyTeam)
                listOfYourTeam.addAll(it.yourTeam)
                launch(Dispatchers.Main) {
                    Glide.with(requireContext()).load(imageUrl)
                        .into(iv_my_champion)
                    tv_my_champion.text = championName
                    enemyTeamAdapter.notifyDataSetChanged()
                    myTeamAdapter.notifyDataSetChanged()

                    for (tip in listOfTips) {
                        val tipView = TextView(activity).apply {
                            layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )
                            text = "- $tip"
                        }
                        tv_tips.addView(tipView)
                    }
                    layout_game.visibility = View.VISIBLE
                    pb_loading_live.visibility = View.INVISIBLE
                }
            }
        })

        liveGameViewModel.failed.observe(viewLifecycleOwner, Observer {
            Snackbar.make(layout_refresh, it, Snackbar.LENGTH_LONG).show()
            pb_loading_live.visibility = View.INVISIBLE
            layout_refresh.visibility = View.VISIBLE
        })
    }
}