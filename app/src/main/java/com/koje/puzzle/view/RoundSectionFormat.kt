package com.koje.puzzle.view

import com.koje.framework.view.ViewBuilder
import com.koje.math.puzzle.R

class RoundSectionFormat : ViewBuilder.Editor {
    override fun edit(target: ViewBuilder) {
        with(target) {
            setBackgroundGradient {
                setCornerRadius(10)
                setStroke(2, R.color.Grey)
                setColorId(R.color.White)
            }
        }
    }
}
