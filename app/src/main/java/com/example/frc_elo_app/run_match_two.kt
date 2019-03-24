package com.example.frc_elo_app

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text
import java.io.File
import java.io.FileOutputStream
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
            displayHidden()
        } else {
            startMain()
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
            displayHidden()
        } else {
            startMain()
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
            displayHidden()
        } else {
            startMain()
        }
    }

    fun displayHidden() {
        findViewById<TextView>(R.id.tv_whoa).visibility = View.VISIBLE
        findViewById<EditText>(R.id.et_enter_match).visibility = View.VISIBLE
        findViewById<Button>(R.id.but_enter_match).visibility = View.VISIBLE
    }

    fun startMain() {
        saveAll()

        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
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

        saveAll()

        startMain()
    }

    /* Checks if external storage is available for read and write */
    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    fun saveAll() {
        if (isExternalStorageWritable()) {
            val letDirectory = File(Environment.getExternalStorageDirectory(), "FRC-ELO")
            letDirectory.mkdirs()
            val file = File(letDirectory, "elo.txt")

            FileOutputStream(file).use {
                it.write("".toByteArray())
            }

            teamsByRank.sortByDescending { it.rating }
            teamsByRank.forEach { file.appendText("${it.number}\t${it.name}\t${it.rating}\n") }


            val upsetFile = File(letDirectory, "upsets.txt")

            FileOutputStream(upsetFile).use {
                it.write("".toByteArray())
            }

            if(listOfUpsets.size == 0) {
                Toast.makeText(this, "Gib upset ༼ つ ◕_◕ ༽つ", Toast.LENGTH_LONG).show()
            }

            val distinct = listOfUpsets.distinct()
            distinct.forEach {upsetFile.appendText("$it\n")}

            Toast.makeText(this, "SAVED", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Gib Permission ༼ つ ◕_◕ ༽つ", Toast.LENGTH_LONG).show()
        }
    }
}
