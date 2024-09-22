package com.koje.puzzle.data

object Operations : OptionGroup() {

    val group =
        listOf(Addition.enabled, Subtraction.enabled, Multiplication.enabled, Division.enabled)

    override fun switch(preference: OptionPreference) {
        switchOneMinimum(preference, group)
    }

    fun enabledCount(): Int {
        var result = 0
        group.forEach {
            if (it.get()) {
                result++
            }
        }
        return result
    }
}