package com.infy.telstraassignment_1kotlin.mvp.canadaslist

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.infy.telstraassignment_1kotlin.R


class CanadasListActivity : AppCompatActivity(), View {


    internal lateinit var frameLayout: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        frameLayout = findViewById(R.id.framelayout)
        replaceFragment()
    }


    override fun replaceFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.framelayout, MainFragment())
            .addToBackStack(null).commit()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.framelayout)
        if (fragment is MainFragment) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}