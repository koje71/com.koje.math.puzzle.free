package com.koje.puzzle.view

import android.os.Handler
import android.os.Looper
import com.koje.framework.App
import com.koje.framework.view.FrameLayoutBuilder
import com.koje.framework.view.LinearLayoutBuilder
import com.koje.math.puzzle.R
import com.koje.puzzle.core.Names
import com.koje.puzzle.core.Playground
import com.koje.puzzle.core.Sound


class Game : FrameLayoutBuilder.Editor {

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
                addImageView {
                    setMarginsDP(0, 0, 0, 5)
                    setDrawableId(R.drawable.menu)
                    setSizeDP(36)
                    setOnClickListener {
                        if (Playground.board.state == Names.Playing) {
                            Sound.knock.play()
                            Activity.content.set(Settings())
                        }
                    }
                    setBackgroundStateList {
                        addStatePressedGradient {
                            setCornerRadius(5)
                            setColorId(R.color.Gold)
                        }
                        addStateWildcardGradient {
                            setCornerRadius(5)
                            setColorId(R.color.Transparent)
                        }
                    }
                }
                addTextView {
                    setMarginsDP(20, 0, 0, 0)
                    setTextColorID(R.color.White)
                    setTextSizeSP(32f)
                    setIncludeFontPadding(true)
                    addReceiver(FooterSection.languageCode) {
                        setFontId(Activity.getCustomFontId())
                        setText(App.getString(R.string.AppName))
                        if("tlh".equals(it)){
                            setPaddingsDP(0,0,0,5)
                        }else{
                            setPaddingsDP(0,0,0,0)
                        }
                    }
                }
                addFiller()
                addImageView {
                    setMarginsDP(0, 0, 0, 5)
                    setDrawableId(R.drawable.help)
                    setSizeDP(36)
                    setOnClickListener {
                        if (Playground.board.state == Names.Playing) {
                            Playground.board.solve()
                        }
                    }
                    setBackgroundStateList {
                        addStatePressedGradient {
                            setCornerRadius(5)
                            setColorId(R.color.Gold)
                        }
                        addStateWildcardGradient {
                            setCornerRadius(5)
                            setColorId(R.color.Transparent)
                        }
                    }

                    if (Activity.demoMode) {
                        Handler(Looper.getMainLooper()).postDelayed({
                            view.performClick()
                        }, 4000)
                    }
                }
            }
        }
    }

    fun addContent(target: LinearLayoutBuilder) {
        target.addFiller()
    }

}