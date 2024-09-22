package com.koje.puzzle.view

import com.koje.framework.App
import com.koje.framework.events.IntNotifier
import com.koje.framework.utils.IntPreference
import com.koje.framework.view.LinearLayoutBuilder
import com.koje.math.puzzle.R

class ScoresArea : LinearLayoutBuilder.Editor {

    override fun edit(target: LinearLayoutBuilder) {
        target.addFrameLayout {
            setWidthMatchParent()

            setHeightDP(40)
            addImageView {
                setDrawableId(R.drawable.leather2)
                setScaleTypeFitXY()
                setSizeMatchParent()
            }

            addRelativeLayout {
                setPaddingsDP(8, 0)
                setSizeMatchParent()
                setGravityCenter()
                addTextView {
                    setTextColorID(R.color.White)
                    setTextSizeSP(16f)
                    setGravityCenterLeft()

                    addReceiver(solved) {
                        setText("${App.getString(R.string.SolvedCurrent)} $it")
                    }

                    addReceiver(FooterSection.languageCode) {
                        setText("${App.getString(R.string.SolvedCurrent)} ${solved.get()}")
                        setFontId(Activity.getCustomFontId())
                    }
                }
            }

            addRelativeLayout {
                setPaddingsDP(8, 0)
                setSizeMatchParent()
                setGravityCenter()
                addImageView {
                    setSizeDP(40)
                    setPaddingsDP(5, 5)
                    setDrawableId(R.drawable.star)
                }
            }

            addRelativeLayout {
                setPaddingsDP(8, 0)
                setSizeMatchParent()
                setGravityCenter()
                addTextView {
                    setTextColorID(R.color.White)
                    setTextSizeSP(16f)
                    setGravityCenterRight()

                    addReceiver(total) {
                        setText("$it ${App.getString(R.string.SolvedTotal)}")
                    }

                    addReceiver(FooterSection.languageCode) {
                        setText("${total.get()} ${App.getString(R.string.SolvedTotal)}")
                        setFontId(Activity.getCustomFontId())
                    }
                }
            }
        }
    }


    companion object {
        val total = IntPreference("score-total", 0)
        val solved = IntNotifier(0)
    }
}