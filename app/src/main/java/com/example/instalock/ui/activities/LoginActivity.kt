package com.example.instalock.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.instalock.BuildConfig
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
        Orianna.setRiotAPIKey(BuildConfig.RIOT_API_KEY)
        initView()
    }

    private fun initView() {
        val arrayAdapter = ArrayAdapter<String>(
            this,
            R.layout.spinner_item_selected,
            Region.values().map { it.tag })
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item)
        et_region.adapter = arrayAdapter

        btn_login.setOnClickListener {
            if (et_summoner_name.text.isNotBlank()) {
                try {
                    summonerViewModel.getSummoner(et_summoner_name.text.toString(), et_region.selectedItem.toString())
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            } else {
                Snackbar.make(btn_login, "Please fill in your summoner name", Snackbar.LENGTH_LONG)
                    .show()
            }

            summonerViewModel.failed.observe(this, Observer {
                Snackbar.make(btn_login, it, Snackbar.LENGTH_LONG)
                    .show()
            })

            summonerViewModel.succes.observe(this, Observer {
                if (it) {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra(KEY_SUMM_NAME, et_summoner_name.text.toString())
                    intent.putExtra(KEY_REGION, et_region.selectedItem.toString())
                    startActivity(intent)
                }
            })
        }
    }
}