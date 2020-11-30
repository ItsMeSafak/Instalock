package com.example.instalock.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.instalock.R
import com.google.android.material.snackbar.Snackbar
import com.merakianalytics.orianna.types.common.Region
import kotlinx.android.synthetic.main.activity_login.*

const val KEY_SUMM_NAME = "KEY_SUMM_NAME"
const val KEY_REGION = "KEY_REGION"
class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
    }

    private fun initView() {
        val arrayAdapter = ArrayAdapter<String>(this, R.layout.spinner_item_selected, Region.values().map { it.tag })
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item)
        et_region.adapter = arrayAdapter

        btn_login.setOnClickListener {
            if (et_summoner_name.text.isNotBlank()) {
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra(KEY_SUMM_NAME, et_summoner_name.text.toString())
                //intent.putExtra(KEY_REGION, et_region.text.toString())
                startActivity(intent)
            } else {
               Snackbar.make(btn_login, "Please fill in your summoner name", Snackbar.LENGTH_LONG)
                   .setBackgroundTint(getColor(R.color.colorRed))
                   .show()
            }
        }
    }
}