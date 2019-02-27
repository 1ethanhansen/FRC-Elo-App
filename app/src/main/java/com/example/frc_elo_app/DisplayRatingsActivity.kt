package com.example.frc_elo_app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.text.method.ScrollingMovementMethod
import android.widget.TextView
import android.widget.Toast
import java.io.File
import java.io.FileInputStream
import kotlin.math.roundToInt

class DisplayRatingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_ratings)

        findViewById<TextView>(R.id.tv_display).movementMethod = ScrollingMovementMethod()
        displayRatings()
    }

    fun displayRatings() {
        var displayString = ""
        teamsByRank.forEach { displayString = displayString +
                "${it.rating.roundToInt().toString().padEnd(5, '-')}-" +
                "-${it.name.padEnd(20, '-')}" +
                "-${it.number.toString().padEnd(5, '-') }\n" }
        findViewById<TextView>(R.id.tv_display).text = displayString
    }
}
