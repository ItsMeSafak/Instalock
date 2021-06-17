package com.example.instalock.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.instalock.R
import com.example.instalock.viewmodels.SummonerViewModel
import com.google.android.material.snackbar.Snackbar
import com.merakianalytics.orianna.Orianna
import com.merakianalytics.orianna.types.common.Region
import kotlinx.android.synthetic.main.activity_login.*
import java.lang.Exception

const val KEY_SUMM_NAME = "KEY_SUMM_NAME"
const val KEY_REGION = "KEY_REGION"

class LoginActivity : AppCompatActivity() {

    private val summonerViewModel: SummonerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Orianna.setRiotAPIKey("KEY")
        initView()
    }

    private fun initView() {
        var selectedItem = "nothing"
        et_region_tv.setAdapter(
            ArrayAdapter<String>(
                this,
                R.layout.spinner_item_selected,
                Region.values().map { it.tag })
        )

        et_region_tv.setOnItemClickListener { parent, _, position, _ ->
            selectedItem = parent.getItemAtPosition(position) as String
        }

        btn_login.setOnClickListener {
            pb_searching.visibility = View.VISIBLE
            iv_heimerdonger.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotation))
            Log.d("hoi", selectedItem)
            if (!et_summoner_name_text.text.isNullOrBlank() && selectedItem != "nothing") {
                summonerViewModel.getSummoner(
                    et_summoner_name_text.text.toString(),
                    selectedItem
                )
            } else {
                clearSearching()
                if (et_summoner_name_text.text.isNullOrBlank()) {
                    Snackbar.make(btn_login, getString(R.string.fill_name), Snackbar.LENGTH_LONG)
                        .show()
                } else {
                    Snackbar.make(btn_login, getString(R.string.fill_region), Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        }

        summonerViewModel.failed.observe(this, Observer {
            clearSearching()
            Snackbar.make(btn_login, it, Snackbar.LENGTH_LONG)
                .show()
        })

        summonerViewModel.succes.observe(this, Observer {
            if (it) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(KEY_SUMM_NAME, et_summoner_name_text.text.toString())
                intent.putExtra(KEY_REGION, selectedItem)
                startActivity(intent)
            }
        })
    }

    override fun onStop() {
        super.onStop()
        clearSearching()
    }

    private fun clearSearching() {
        pb_searching.visibility = View.INVISIBLE
        iv_heimerdonger.clearAnimation()
    }
}