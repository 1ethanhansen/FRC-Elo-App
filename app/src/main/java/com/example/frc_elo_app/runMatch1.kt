package com.example.frc_elo_app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_run_match1.*
import kotlin.math.pow
import kotlin.math.roundToInt

class runMatch1 : AppCompatActivity() {

    var counter = 0
    var displayString = ""
    var inputName = ""
    var inputNumber = 0
    var alliancesDisplay = ""

    val redAlliance = Array(3){emptyTeam}
    val blueAlliance = Array(3){emptyTeam}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_run_match1)
        counter = 0
        displayString = "Enter the team number in RED alliance station 1: "
        findViewById<TextView>(R.id.tv_prompt).text = displayString
    }

    fun teamEntered(view: View) {

        val driverStationInt = counter % 3 + 1
        val allianceInt = counter / 3

        counter++
        if (counter > 5) {
            counter = 0
            runningPtTwo()
        }

        displayString = "Enter the team number in ${if(allianceInt == 0) "RED" else "BLUE"} " +
                "alliance station $driverStationInt: "
        findViewById<TextView>(R.id.tv_prompt).text = displayString

        var userInput = findViewById<EditText>(R.id.et_input).text.toString()
        inputNumber = userInput.toInt()

        if (teamsByRank.find {it.number == inputNumber} != null) {
            val foundName = teamsByRank.find {it.number == inputNumber}!!.name
            val foundRating = teamsByRank.find {it.number == inputNumber}!!.rating
            alliancesDisplay = alliancesDisplay + "Team $inputNumber, team $foundName " +
                    "with an elo rating of ${foundRating.roundToInt()}\n"
            findViewById<TextView>(R.id.tv_red_teams).text = alliancesDisplay
        } else {
            findViewById<EditText>(R.id.et_input2).visibility = View.VISIBLE
            findViewById<Button>(R.id.but_enter2).visibility = View.VISIBLE
            findViewById<TextView>(R.id.tv_prompt).text = "Please enter the name of team #$inputNumber"
        }
        //Keep track of who is on which alliance
        if (allianceInt == 0) {
            redAlliance[driverStationInt - 1] = teamsByRank.find {it.number == inputNumber}!!
        } else {
            blueAlliance[driverStationInt - 1] = teamsByRank.find {it.number == inputNumber}!!
        }
    }

    fun nameEntered(view: View) {
        inputName = findViewById<EditText>(R.id.et_input2).text.toString()

        val newTeam = Team(inputNumber, inputName)

        teamsByRank.add(0, newTeam)

        alliancesDisplay = alliancesDisplay + "Team $inputNumber, team ${newTeam.name} " +
                "with an elo rating of ${newTeam.rating.roundToInt()}\n"
        findViewById<TextView>(R.id.tv_red_teams).text = alliancesDisplay

        findViewById<EditText>(R.id.et_input2).visibility = View.INVISIBLE
        findViewById<Button>(R.id.but_enter2).visibility = View.INVISIBLE
    }

    fun runningPtTwo() {
        val partTwoIntent = Intent
    }
}
