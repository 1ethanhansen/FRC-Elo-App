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
val emptyTeam = Team(0, "0")
val listOfUpsets = mutableListOf<String>()

class MainActivity : AppCompatActivity() {

    val redAlliance = Array(3){emptyTeam}
    val blueAlliance = Array(3){emptyTeam}

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

    fun upsetMe(view: View) {
        val upsetIntent = Intent(this, DisplayUpsetsActivity::class.java)
        startActivity(upsetIntent)
    }

    fun loadMe(view: View) {
        if (isExternalStorageWritable()) {
            teamsByRank.clear()

            val letDirectory = File(Environment.getExternalStorageDirectory(), "FRC-ELO")
            letDirectory.mkdirs()
            val file = File(letDirectory, "elo.txt")

            val inputAsString = FileInputStream(file).bufferedReader().use { it.readText() }
            if (inputAsString.length > 5) {
                val readLines = inputAsString.split('\n').toMutableList()
                readLines.removeAt(readLines.lastIndex)
                for (i in readLines) {
                    val mReadDataList = i.split('\t')

                    val readTeam = Team(mReadDataList[0].toInt(), mReadDataList[1], mReadDataList[2].toDouble())

                    teamsByRank.add(0, readTeam)
                }
            }

            val upsetFile = File(letDirectory, "upsets.txt")

            val upsetsAsString = FileInputStream(upsetFile).bufferedReader().use { it.readText() }
            if (upsetsAsString.length > 5) {
                val readUpsetLines = upsetsAsString.split('\n')
                for (i in readUpsetLines) {
                    listOfUpsets.add(0, i)
                }
            }

            Toast.makeText(this, "LOADED", Toast.LENGTH_LONG).show()
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

            teamsByRank.sortByDescending { it.rating }
            teamsByRank.forEach { file.appendText("${it.number}\t${it.name}\t${it.rating}\n") }


            val upsetFile = File(letDirectory, "upsets.txt")

            FileOutputStream(upsetFile).use {
                it.write("".toByteArray())
            }

            if(listOfUpsets.size == 0) {
                Toast.makeText(this, "Gib upset ༼ つ ◕_◕ ༽つ", Toast.LENGTH_LONG).show()
            }

            listOfUpsets.forEach {upsetFile.appendText("$it\n")}

            Toast.makeText(this, "SAVED", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Gib Permission ༼ つ ◕_◕ ༽つ", Toast.LENGTH_LONG).show()
        }
    }

    /* Checks if external storage is available for read and write */
    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }
}
