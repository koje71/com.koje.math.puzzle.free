package com.koje.puzzle.data

open class OptionGroup {

    open fun switch(preference: OptionPreference) {
    }

    open fun switchOneMinimum(preference: OptionPreference, group: List<OptionPreference>) {
        var others = 0
        group.forEach {
            if (it != preference && it.contains(true)) {
                others++
            }
        }

        if (others > 0) {
            preference.switch()
        } else {
            preference.set(true)
        }
    }

    open fun switchMaximum(preference: OptionPreference, group: List<OptionPreference>) {
        var reached = false
        group.forEach {
            if (it == preference) {
                it.set(true)
                reached = true
            } else {
                it.set(!reached)
            }
        }
    }
}