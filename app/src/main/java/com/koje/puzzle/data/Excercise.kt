package com.koje.puzzle.data

import kotlin.random.Random

class Excercise(var content: String = "") {

    init {
        while (content.isEmpty()) {
            when (getRandomEnabledPref(Operations.group)) {
                Addition.enabled -> createAddition()
                Subtraction.enabled -> createSubtraction()
                Multiplication.enabled -> createMultiplication()
                Division.enabled -> createDivision()
            }
        }
    }

    private fun createAddition() {
        var range = 0
        if (Addition.to10.get()) range = 10
        if (Addition.to20.get()) range = 20
        if (Addition.to30.get()) range = 30
        if (Addition.to50.get()) range = 50
        if (Addition.to100.get()) range = 100
        if (Addition.over100.get()) range = 200

        val result = getRandom(2, range)
        var operant1: Int
        var operant2: Int

        if (Addition.carryOver.get()) {
            operant1 = getRandom(1, result)
            operant2 = result - operant1
        } else {
            operant2 = result
            while (operant2 > 10) {
                operant2 -= 10
            }
            operant2 = getRandom(0, operant2)
            operant1 = result - operant2
        }

        if (operant1 == 0 || operant2 == 0) {
            return
        }

        content = "$operant1+$operant2=$result"
        if(content.length>9){
            content = ""
        }
    }

    private fun createSubtraction() {
        var range = 0
        if (Subtraction.to10.get()) range = 10
        if (Subtraction.to20.get()) range = 20
        if (Subtraction.to30.get()) range = 30
        if (Subtraction.to50.get()) range = 50
        if (Subtraction.to100.get()) range = 100
        if (Subtraction.over100.get()) range = 200

        var operant1 = getRandom(2, range)
        var result: Int
        var operant2: Int

        if (Subtraction.carryOver.get()) {
            result = getRandom(1, operant1)
            operant2 = operant1 - result
        } else {
            operant2 = operant1
            while (operant2 > 10) {
                operant2 -= 10
            }
            operant2 = getRandom(0, operant2)
            result = operant1 - operant2
        }

        if (operant1 == 0 || operant2 == 0) {
            return
        }

        content = "$operant1-$operant2=$result"
        if(content.length>9){
            content = ""
        }
    }

    private fun createMultiplication() {
        val selector = getRandomEnabledPref(Multiplication.group)
        val range = when (selector) {
            Multiplication.row1 -> 1
            Multiplication.row2 -> 2
            Multiplication.row3 -> 3
            Multiplication.row4 -> 4
            Multiplication.row5 -> 5
            Multiplication.row6 -> 6
            Multiplication.row7 -> 7
            Multiplication.row8 -> 8
            Multiplication.row9 -> 9
            Multiplication.row10 -> 10
            else -> 0
        }

        var operant1 = getRandom(1, 10)
        var operant2 = range
        var result = operant1 * operant2

        content = "$operant1*$operant2=$result"
    }

    private fun createDivision() {
        val selector = getRandomEnabledPref(Division.group)
        val range = when (selector) {
            Division.row1 -> 1
            Division.row2 -> 2
            Division.row3 -> 3
            Division.row4 -> 4
            Division.row5 -> 5
            Division.row6 -> 6
            Division.row7 -> 7
            Division.row8 -> 8
            Division.row9 -> 9
            Division.row10 -> 10
            else -> 0
        }

        var result = getRandom(1, 10)
        var operant2 = range
        var operant1 = result * operant2

        content = "$operant1:$operant2=$result"
    }

    private fun getRandomEnabledPref(source: List<OptionPreference>): OptionPreference {
        val items = mutableListOf<OptionPreference>()
        source.forEach {
            if (it.get()) {
                items.add(it)
            }
        }
        items.shuffle(Random)
        return items[0]
    }

    private fun getRandom(min: Int, max: Int): Int {
        return Random.nextInt((max - min) + 1) + min
    }
}