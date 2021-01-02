package com.example.instalock.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.navigation.findNavController
import com.example.instalock.R
import kotlinx.android.synthetic.main.activity_main.*

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
                    finishAffinity()
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }
            drawer_layout.closeDrawers()
            true
        }
        toggle.syncState()
    }
}