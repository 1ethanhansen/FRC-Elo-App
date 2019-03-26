package com.example.frc_elo_app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlin.math.roundToInt

var addingDisplayString: String = ""

class NewTeams : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_teams)

        addingDisplayString = ""
    }

    fun teamEntered(view: View) {
        var userNum = findViewById<EditText>(R.id.et_add_num).text.toString()
        val inputNumber = userNum.toInt()

        if (teamsByRank.find {it.number == inputNumber} != null) {
            Toast.makeText(this, "Already had that team", Toast.LENGTH_SHORT).show()

            val foundName = teamsByRank.find {it.number == inputNumber}!!.name
            val foundRating = teamsByRank.find {it.number == inputNumber}!!.rating

            addingDisplayString += foundRating.roundToInt().toString().padEnd(5, '-') +
                    foundName.padEnd(20, '-') +
                    inputNumber + "\n"
        } else {
            Toast.makeText(this, "Adding new team", Toast.LENGTH_SHORT).show()

            val inputName = findViewById<EditText>(R.id.et_add_name).text.toString()

            val newTeam = Team(inputNumber, inputName)

            teamsByRank.add(0, newTeam)

            addingDisplayString += "1000-" + inputName.padEnd(20, '-') +
                    inputNumber + "\n"

            saveAll()
        }

        findViewById<TextView>(R.id.tv_added_teams).text = addingDisplayString
    }
}
