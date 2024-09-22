package com.koje.puzzle.data

import com.koje.framework.utils.BooleanPreference
import com.koje.math.puzzle.R

object Addition : OptionGroup() {

    val enabled = OptionPreference(R.string.OperationsAddition, "add-enabled", true)
    val to10 = OptionPreference(R.string.Number10, "add-to-10", true)
    val to20 = OptionPreference(R.string.Number20, "add-to-20", false)
    val to30 = OptionPreference(R.string.Number30, "add-to-30", false)
    val to50 = OptionPreference(R.string.Number50, "add-to-50", false)
    val to100 = OptionPreference(R.string.Number100, "add-to-100", false)
    val over100 = OptionPreference(R.string.Over100, "add-over-100", false)

    val group = listOf(to10, to20, to30, to50, to100)

    override fun switch(preference: OptionPreference) {
        switchMaximum(preference, group)
    }

    val carryOver = BooleanPreference("addition-carry-over", true)

}