package com.example.frc_elo_app

import android.os.Environment
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class Team (var number: Int,
            var name: String,
            var rating: Double = 1000.0)

class Alliance (var captain: Team,
                var pickOne: Team,
                var pickTwo: Team) {
    fun getAllianceString() : String {
        return "\t${captain.number}\n\t\t${pickOne.number}\n\t\t${pickTwo.number}\n"
    }
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
//            Toast.makeText(this, "Gib upset ༼ つ ◕_◕ ༽つ", Toast.LENGTH_LONG).show()
        }

        val distinct = listOfUpsets.distinct()
        distinct.forEach {upsetFile.appendText("$it\n")}

//        Toast.makeText(this, "SAVED", Toast.LENGTH_LONG).show()
    } else {
//        Toast.makeText(this, "Gib Permission ༼ つ ◕_◕ ༽つ", Toast.LENGTH_LONG).show()
    }
}

fun loadAll() {
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

        listOfUpsets.clear()
        val upsetFile = File(letDirectory, "upsets.txt")

        val upsetsAsString = FileInputStream(upsetFile).bufferedReader().use { it.readText() }
        if (upsetsAsString.length > 5) {
            val readUpsetLines = upsetsAsString.split('\n')
            for (i in readUpsetLines) {
                listOfUpsets.add(0, i)
            }
        }

//        Toast.makeText(this, "LOADED", Toast.LENGTH_LONG).show()
    } else {
//        Toast.makeText(this, "Gib Permission ༼ つ ◕_◕ ༽つ", Toast.LENGTH_LONG).show()
    }
}

fun saveBackups() {
    if (isExternalStorageWritable()) {
        val letDirectory = File(Environment.getExternalStorageDirectory(), "FRC-ELO")
        letDirectory.mkdirs()
        val file = File(letDirectory, "elo_backup.txt")

        FileOutputStream(file).use {
            it.write("".toByteArray())
        }

        teamsByRank.sortByDescending { it.rating }
        teamsByRank.forEach { file.appendText("${it.number}\t${it.name}\t${it.rating}\n") }


        val upsetFile = File(letDirectory, "upsets_backup.txt")

        FileOutputStream(upsetFile).use {
            it.write("".toByteArray())
        }

        if(listOfUpsets.size == 0) {
//            Toast.makeText(this, "Gib upset ༼ つ ◕_◕ ༽つ", Toast.LENGTH_LONG).show()
        }

        val distinct = listOfUpsets.distinct()
        distinct.forEach {upsetFile.appendText("$it\n")}

//        Toast.makeText(this, "SAVED", Toast.LENGTH_LONG).show()
    } else {
//        Toast.makeText(this, "Gib Permission ༼ つ ◕_◕ ༽つ", Toast.LENGTH_LONG).show()
    }
}

/* Checks if external storage is available for read and write */
fun isExternalStorageWritable(): Boolean {
    return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
}