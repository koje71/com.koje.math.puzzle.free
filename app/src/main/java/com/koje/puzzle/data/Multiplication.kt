package com.koje.puzzle.data

import com.koje.math.puzzle.R

object Multiplication : OptionGroup() {

    val enabled = OptionPreference(R.string.OperationsMultiplication, "mul-enabled", false)
    val row1 = OptionPreference(R.string.Number1, "mul-row-1", true)
    val row2 = OptionPreference(R.string.Number2, "mul-row-2", true)
    val row3 = OptionPreference(R.string.Number3, "mul-row-3", true)
    val row4 = OptionPreference(R.string.Number4, "mul-row-4", true)
    val row5 = OptionPreference(R.string.Number5, "mul-row-5", true)
    val row6 = OptionPreference(R.string.Number6, "mul-row-6", true)
    val row7 = OptionPreference(R.string.Number7, "mul-row-7", true)
    val row8 = OptionPreference(R.string.Number8, "mul-row-8", true)
    val row9 = OptionPreference(R.string.Number9, "mul-row-9", true)
    val row10 = OptionPreference(R.string.Number10, "mul-row-10", true)
    val over10 = OptionPreference(R.string.Over10, "mul-over-10", true)

    val group = listOf(row1, row2, row3, row4, row5, row6, row7, row8, row9, row10)

    override fun switch(preference: OptionPreference) {
        switchOneMinimum(preference, group)
    }

}