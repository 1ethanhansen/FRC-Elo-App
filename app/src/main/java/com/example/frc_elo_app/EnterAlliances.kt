package com.example.frc_elo_app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

var allianceOfZeros = Alliance(emptyTeam, emptyTeam, emptyTeam)

val alliancesArray = Array(8){Alliance(emptyTeam, emptyTeam, emptyTeam)}
var countOfAlliances = 0
var countOfPicks = 0
class EnterAlliances : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_alliances)

        countOfAlliances = 0
        countOfPicks = 0
    }

    fun addAlliance(view: View) {
        var inputNumber = findViewById<EditText>(R.id.et_alliance_team_num).text.toString().toInt()

        if (teamsByRank.find { it.number == inputNumber } != null) {
            when (countOfPicks) {
                0 -> alliancesArray[countOfAlliances].captain = teamsByRank.find { it.number == inputNumber }!!
                1 -> alliancesArray[countOfAlliances].pickOne = teamsByRank.find { it.number == inputNumber }!!
                2 -> {alliancesArray[countOfAlliances].pickTwo = teamsByRank.find { it.number == inputNumber }!!
                        countOfPicks = -1
                        countOfAlliances++}
            }
            if (countOfAlliances == 7 && countOfPicks == -1) {
                //Start next intent
            }
            countOfPicks++
        } else {
            Toast.makeText(this, "Something when wrong", Toast.LENGTH_SHORT).show()
        }
        findViewById<TextView>(R.id.tv_alliance_list_one).text = "1:" + alliancesArray[0].getAllianceString()
        findViewById<TextView>(R.id.tv_alliance_list_two).text = "2:" + alliancesArray[1].getAllianceString()
        findViewById<TextView>(R.id.tv_alliance_list_three).text = "3:" + alliancesArray[2].getAllianceString()
        findViewById<TextView>(R.id.tv_alliance_list_four).text = "4:" + alliancesArray[3].getAllianceString()
        findViewById<TextView>(R.id.tv_alliance_list_five).text = "5:" + alliancesArray[4].getAllianceString()
        findViewById<TextView>(R.id.tv_alliance_list_six).text = "6:" + alliancesArray[5].getAllianceString()
        findViewById<TextView>(R.id.tv_alliance_list_seven).text = "7:" + alliancesArray[6].getAllianceString()
        findViewById<TextView>(R.id.tv_alliance_list_eight).text = "8:" + alliancesArray[7].getAllianceString()

        findViewById<EditText>(R.id.et_alliance_team_num).text.clear()
    }
}
