package com.example.frc_elo_app

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.PrintWriter

class Team (var number: Int,
            var name: String,
            var rating: Double = 1000.0)

val teamsByRank = mutableListOf<Team>()
val emptyTeam = Team(-1, "0")
val listOfUpsets = mutableListOf<String>()
var upsetThreshold = .48
var count = 0

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        count = 0

        sb_upsetThreshold.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                upsetThreshold = .5 - (i / 100.0)
                tv_threshold.text = "Upset Threshold : $upsetThreshold"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
    }

    fun runMe(view: View) {
        loadAll()
        val runIntent = Intent(this, runMatch1::class.java)
        startActivity(runIntent)
    }

    fun displayRatingsMe(view: View) {
        loadAll()
        val displayIntent = Intent(this, DisplayRatingsActivity::class.java)
        startActivity(displayIntent)
    }

    fun upsetMe(view: View) {
        loadAll()
        val upsetIntent = Intent(this, DisplayUpsetsActivity::class.java)
        startActivity(upsetIntent)
    }

    fun backMeUp(view: View) {
        loadAll()
        saveBackups()
    }

    fun addTeams(view: View) {
        loadAll()
        val addTeamsIntent = Intent(this, NewTeams::class.java)
        startActivity(addTeamsIntent)
    }

    fun countMe(view: View) {
        count++
        if (count > 9) {
            val eggIntent = Intent(this, activity_easter_egg::class.java)
            startActivity(eggIntent)
        }
    }
}
