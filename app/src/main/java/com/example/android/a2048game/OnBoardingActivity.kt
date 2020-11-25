package com.example.android.a2048game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_onboarding.*

class OnBoardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        val sharedPref = getSharedPreferences(
            getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )
        with (sharedPref.edit()) {
            putBoolean(getString(R.string.saved_first_launch_key), false)
            apply()
        }

        viewPager.adapter = ViewPagerAdapter()
        TabLayoutMediator(tabLayout, viewPager) {tab, position ->  }.attach()

        val letsPlayButton : Button = findViewById(R.id.btnLetsPlay)
        letsPlayButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


}