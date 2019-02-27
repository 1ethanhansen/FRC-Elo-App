package com.example.frc_elo_app

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.PrintWriter

class Team (var number: Int,
            var name: String,
            var rating: Double = 1000.0)

val teamsByRank = mutableListOf<Team>()

class MainActivity : AppCompatActivity() {

    val emptyTeam = Team(0, "0")

    val redAlliance = Array(3){emptyTeam}
    val blueAlliance = Array(3){emptyTeam}


    val listOfUpsets = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun runMe(view: View) {
        val runIntent = Intent(this, runMatch1::class.java)
        startActivity(runIntent)
    }

    fun displayRatingsMe(view: View) {
        val displayIntent = Intent(this, DisplayRatingsActivity::class.java)
        startActivity(displayIntent)
    }

    fun loadMe(view: View) {
        if (isExternalStorageWritable()) {
            val letDirectory = File(Environment.getExternalStorageDirectory(), "FRC-ELO")
            letDirectory.mkdirs()
            val file = File(letDirectory, "elo.txt")

            val inputAsString = FileInputStream(file).bufferedReader().use { it.readText() }
            val readLines = inputAsString.split('\n')
            for (i in readLines) {
                val mReadDataList = i.split('\t')

                val readTeam = Team(mReadDataList[0].toInt(), mReadDataList[1], mReadDataList[2].toDouble())

                teamsByRank.add(0, readTeam)
            }
        } else {
            Toast.makeText(this, "Gib Permission ༼ つ ◕_◕ ༽つ", Toast.LENGTH_LONG).show()
        }
    }

    fun saveMe(view: View) {
        if (isExternalStorageWritable()) {
            val letDirectory = File(Environment.getExternalStorageDirectory(), "FRC-ELO")
            letDirectory.mkdirs()
            val file = File(letDirectory, "elo.txt")

            FileOutputStream(file).use {
                it.write("".toByteArray())
            }

            teamsByRank.forEach { file.appendText("${it.number}\t${it.name}\t${it.rating}") }
        } else {
            Toast.makeText(this, "Gib Permission ༼ つ ◕_◕ ༽つ", Toast.LENGTH_LONG).show()
        }
    }

    /* Checks if external storage is available for read and write */
    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }
}
