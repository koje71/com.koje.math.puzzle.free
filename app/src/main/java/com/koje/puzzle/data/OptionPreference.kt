package com.koje.puzzle.data

import com.koje.framework.utils.BooleanPreference


class OptionPreference(val label: Int, name: String, default: Boolean) :
    BooleanPreference(name, default) {
}