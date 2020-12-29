package com.example.instalock.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.instalock.R
import com.example.instalock.utils.TabAdapter
import com.example.instalock.viewmodels.SummonerViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
//        initTabs()
//
//        try {
//            summonerViewModel.getSummoner(intent.getStringExtra(KEY_SUMM_NAME)!!, intent.getStringExtra(KEY_REGION)!!)
//            observe()
//        } catch (ex: Exception) {
//            finish()
//        }

    }

//    private fun observe() {
//        summonerViewModel.summonerData.observe(this, Observer {
//            GlobalScope.launch(Dispatchers.IO) {
//                val url = it.profileIcon.image.url
//                val summonerName = it.name
//                val region = it.region.tag
//                val level = it.level
//                launch(Dispatchers.Main) {
//                    pb_loading.visibility = View.INVISIBLE
//                    Glide.with(this@MainActivity).load(url).into(iv_profile_icon)
//                    tv_summoner_name.text = summonerName
//                    tv_region.text = getString(R.string.p_region, region)
//                    tv_level.text = level.toString()
//                }
//            }
//        })
//
//        summonerViewModel.failed.observe(this, Observer {
//            Snackbar.make(btn_login, it, Snackbar.LENGTH_LONG)
//                .setBackgroundTint(getColor(R.color.colorRed))
//                .show()
//        })
//
//        summonerViewModel.succes.observe(this, Observer {
//            val intent = Intent(this, MainActivity::class.java)
//            if (it) startActivity(intent)
//        })
//    }

//    private fun initTabs() {
//        vp_content.adapter = TabAdapter(this, supportFragmentManager, tab_layout.tabCount)
//        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                if (tab != null) {
//                    vp_content.currentItem = tab.position
//                }
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//                // Handle tab reselect
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                // Handle tab unselect
//            }
//        })
//    }
}