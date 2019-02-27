package com.example.frc_elo_app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.TextView

class DisplayUpsetsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_upsets)

        findViewById<TextView>(R.id.tv_display2).movementMethod = ScrollingMovementMethod()
        displayUpsets()
    }

    fun displayUpsets() {
        var displayString = ""
        listOfUpsets.forEach { displayString = displayString + it + "\n" }
        findViewById<TextView>(R.id.tv_display2).text = displayString
    }
}
