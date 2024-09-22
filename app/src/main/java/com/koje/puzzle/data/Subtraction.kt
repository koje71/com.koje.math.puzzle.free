package com.koje.puzzle.data

import com.koje.framework.utils.BooleanPreference
import com.koje.math.puzzle.R

object Subtraction : OptionGroup() {

    val enabled = OptionPreference(R.string.OperationsSubtraction, "sub-enabled", true)
    val to10 = OptionPreference(R.string.Number10, "sub-to-10", true)
    val to20 = OptionPreference(R.string.Number20, "sub-to-20", false)
    val to30 = OptionPreference(R.string.Number30, "sub-to-30", false)
    val to50 = OptionPreference(R.string.Number50, "sub-to-50", false)
    val to100 = OptionPreference(R.string.Number100, "sub-to-100", false)
    val over100 = OptionPreference(R.string.Over100, "sub-over-100", false)

    val group = listOf(to10, to20, to30, to50, to100)

    override fun switch(preference: OptionPreference) {
        switchMaximum(preference, group)
    }

    val carryOver = BooleanPreference("subtraction-carry-over", true)

}