package com.example.frc_elo_app

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text
import kotlin.math.pow
import kotlin.math.roundToInt

class run_match_two : AppCompatActivity() {

    var redAllianceAverage = 0.0
    var blueAllianceAverage = 0.0

    var redChance = 0.0
    var blueChance = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_run_match_two)

        findViewById<TextView>(R.id.tv_whoa).visibility = View.INVISIBLE
        findViewById<EditText>(R.id.et_enter_match).visibility = View.INVISIBLE
        findViewById<Button>(R.id.but_enter_match).visibility = View.INVISIBLE

        calcAverages()

        val initialDisplay = "Red alliance has a ${(redChance * 100).roundToInt()}% chance of winning, giving blue " +
                "${(blueChance * 100).roundToInt()}%"
        findViewById<TextView>(R.id.tv_chances).text = initialDisplay
    }

    fun redEntered(view: View) {
        val redScore = 1
        val blueScore = 0

        val newRedRatingDelta = 64 * (redScore - redChance)
        val newBlueRatingDelta = 64 * (blueScore - blueChance)
        redAlliance.forEach { it.rating = it.rating + newRedRatingDelta }
        blueAlliance.forEach { it.rating = it.rating + newBlueRatingDelta }

        if (redChance < .49) {
            findViewById<TextView>(R.id.tv_whoa).visibility = View.VISIBLE
            findViewById<EditText>(R.id.et_enter_match).visibility = View.VISIBLE
            findViewById<Button>(R.id.but_enter_match).visibility = View.VISIBLE
        } else {
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
        }
    }

    fun blueEntered(view: View) {
        val redScore = 0
        val blueScore = 1

        val newRedRatingDelta = 64 * (redScore - redChance)
        val newBlueRatingDelta = 64 * (blueScore - blueChance)
        redAlliance.forEach { it.rating += newRedRatingDelta }
        blueAlliance.forEach { it.rating += newBlueRatingDelta }

        if (blueChance < .49) {
            findViewById<TextView>(R.id.tv_whoa).visibility = View.VISIBLE
            findViewById<EditText>(R.id.et_enter_match).visibility = View.VISIBLE
            findViewById<Button>(R.id.but_enter_match).visibility = View.VISIBLE
        } else {
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
        }
    }

    fun tieEntered(view: View) {
        val redScore = .5
        val blueScore = .5

        val newRedRatingDelta = 64 * (redScore - redChance)
        val newBlueRatingDelta = 64 * (blueScore - blueChance)
        redAlliance.forEach { it.rating += newRedRatingDelta }
        blueAlliance.forEach { it.rating += newBlueRatingDelta }

        if (blueChance < .48 || redChance < .48) {
            findViewById<TextView>(R.id.tv_whoa).visibility = View.VISIBLE
            findViewById<EditText>(R.id.et_enter_match).visibility = View.VISIBLE
            findViewById<Button>(R.id.but_enter_match).visibility = View.VISIBLE
        } else {
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
        }
    }

    fun calcAverages() {
        redAlliance.forEach { redAllianceAverage += it.rating }
        blueAlliance.forEach { blueAllianceAverage += it.rating }
        redAllianceAverage /= 3
        blueAllianceAverage /= 3

        redChance = 1.0 / (1 + 10.0.pow((blueAllianceAverage - redAllianceAverage) / 400.0))
        blueChance = 1 - redChance
    }

    fun saveUpsets(view: View) {
        val inputStr = findViewById<EditText>(R.id.et_enter_match).text.toString()
        listOfUpsets.add(0, inputStr)
        Toast.makeText(this, listOfUpsets[0], Toast.LENGTH_SHORT).show()

        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
    }
}
