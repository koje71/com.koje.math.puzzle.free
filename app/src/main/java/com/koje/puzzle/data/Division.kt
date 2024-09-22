package com.koje.puzzle.data

import com.koje.math.puzzle.R

object Division : OptionGroup() {

    val enabled = OptionPreference(R.string.OperationsDivision, "div-enabled", false)
    val row1 = OptionPreference(R.string.Number1, "div-row-1", true)
    val row2 = OptionPreference(R.string.Number2, "div-row-2", true)
    val row3 = OptionPreference(R.string.Number3, "div-row-3", true)
    val row4 = OptionPreference(R.string.Number4, "div-row-4", true)
    val row5 = OptionPreference(R.string.Number5, "div-row-5", true)
    val row6 = OptionPreference(R.string.Number6, "div-row-6", true)
    val row7 = OptionPreference(R.string.Number7, "div-row-7", true)
    val row8 = OptionPreference(R.string.Number8, "div-row-8", true)
    val row9 = OptionPreference(R.string.Number9, "div-row-9", true)
    val row10 = OptionPreference(R.string.Number10, "div-row-10", true)
    val over10 = OptionPreference(R.string.Over10, "div-over-10", false)

    val group = listOf(row1, row2, row3, row4, row5, row6, row7, row8, row9, row10)

    override fun switch(preference: OptionPreference) {
        switchOneMinimum(preference, group)
    }

}