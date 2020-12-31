package com.example.instalock.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.instalock.R
import com.example.instalock.utils.TabAdapter
import com.example.instalock.viewmodels.SummonerViewModel
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.nav_open, R.string.nav_close)
        drawer_layout.addDrawerListener(toggle)

        nav_view.setCheckedItem(R.id.nav_profile)
        nav_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_profile -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.homeFragment)
                }
                R.id.nav_champions -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.allChampionsFragment)
                }
                R.id.nav_live_game -> {

                }
                R.id.nav_log_out -> {
                    finish()
                }
            }
            drawer_layout.closeDrawers()
            true
        }
        toggle.syncState()
    }
}