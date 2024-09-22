package com.koje.puzzle.view

import android.os.Handler
import android.os.Looper
import com.koje.framework.App
import com.koje.framework.view.FrameLayoutBuilder
import com.koje.framework.view.LinearLayoutBuilder
import com.koje.math.puzzle.R
import com.koje.puzzle.core.Playground
import com.koje.puzzle.core.Sound

class Forward(val text: String) : FrameLayoutBuilder.Editor {

    override fun edit(target: FrameLayoutBuilder) {
        target.addLinearLayout {
            setOrientationVertical()
            addHeader(this)
            addContent(this)
        }
    }

    fun addHeader(target: LinearLayoutBuilder) {
        target.addFrameLayout {
            setHeightDP(70)
            addImageView {
                setDrawableId(R.drawable.leather)
                setScaleTypeFitXY()
                setSizeMatchParent()
            }
            addLinearLayout {
                setWidthMatchParent()
                setOrientationHorizontal()
                setGravityBottom()
                setPaddingsDP(7, 7)
                setHeightDP(70)
                addFiller()
                addTextView {
                    setMarginsDP(20, 0, 0, 0)
                    setTextColorID(R.color.White)
                    setTextSizeSP(32f)
                    addReceiver(FooterSection.languageCode) {
                        setFontId(Activity.getCustomFontId())
                    }
                    setText(text)
                }
                addFiller()
            }
        }
    }

    fun addContent(target: LinearLayoutBuilder) {
        with(target) {
            addFiller()
            addLinearLayout {
                setOrientationHorizontal()
                addFiller()
                addTextView {
                    setTextColorID(R.color.Grey)
                    addReceiver(FooterSection.languageCode) {
                        setFontId(Activity.getCustomFontId())
                    }
                    setTextSizeSP(22f)
                    setMarginsDP(20, 20, 20, 20)
                    setPaddingsDP(20, 5, 20, 10)
                    setTextColorID(R.color.White)
                    setText(App.getString(R.string.StartNewGame))
                    setOnClickListener {
                        Sound.knock.play()
                        Activity.content.set(Game())
                        Playground.board.switch()
                    }
                    setBackgroundStateList {
                        addStatePressedGradient {
                            setCornerRadius(10)
                            setStroke(2, R.color.Grey)
                            setColorId(R.color.Gold)
                        }
                        addStateWildcardGradient {
                            setCornerRadius(10)
                            setStroke(2, R.color.Grey)
                            setColorId(R.color.Grey)
                        }
                    }

                    if (Activity.demoMode) {
                        Handler(Looper.getMainLooper()).postDelayed({
                            view.performClick()
                        }, 4000)
                    }
                }
                addFiller()
            }
        }
    }


}