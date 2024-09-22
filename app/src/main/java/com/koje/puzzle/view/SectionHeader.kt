package com.koje.puzzle.view

import com.koje.framework.App
import com.koje.framework.view.LinearLayoutBuilder
import com.koje.math.puzzle.R

class SectionHeader(val label: Int) : LinearLayoutBuilder.Editor {

    override fun edit(target: LinearLayoutBuilder) {
        target.addLinearLayout {
            setOrientationHorizontal()
            setMarginsDP(3, 20, 3, 10)
            addTextView {
                setText("☛")
                setTextSizeSP(22f)
                setTextColorID(R.color.Grey)
                setMarginsDP(0, 0, 0, 3)
            }
            addTextView {
                setTextSizeSP(24f)
                setTextColorID(R.color.Grey)
                setPaddingsDP(8, 0)

                addReceiver(FooterSection.languageCode) {
                    setFontId(Activity.getCustomFontId())
                    setText(App.getString(label))
                }
            }
            addFiller()
        }
    }
}
