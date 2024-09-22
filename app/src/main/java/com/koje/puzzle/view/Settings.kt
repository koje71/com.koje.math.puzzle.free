package com.koje.puzzle.view

import com.koje.framework.App
import com.koje.framework.utils.BooleanPreference
import com.koje.framework.utils.IntPreference
import com.koje.framework.view.FrameLayoutBuilder
import com.koje.framework.view.LinearLayoutBuilder
import com.koje.math.puzzle.R
import com.koje.puzzle.core.Names
import com.koje.puzzle.core.Playground
import com.koje.puzzle.core.Sound
import com.koje.puzzle.data.Addition
import com.koje.puzzle.data.Division
import com.koje.puzzle.data.Multiplication
import com.koje.puzzle.data.Subtraction

class Settings : FrameLayoutBuilder.Editor {

    var reload = false

    override fun edit(target: FrameLayoutBuilder) {

        val settings = this
        target.addLinearLayout {
            setOrientationVertical()
            addHeader(this)
            addScrollView {
                setBackgroundColorId(R.color.Wood)
                addLinearLayout {
                    setOrientationVertical()
                    setPaddingsDP(8, 20)

                    add(OperationsSection(settings))
                    add(GameVariantSection(settings))
                    add(PreferencesSection(settings))
                    add(StatisticSection(settings))
                    add(FooterSection(settings))

                }

            }
        }
    }

    private fun addHeader(target: LinearLayoutBuilder) {
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
                    setDrawableId(R.drawable.menu)
                    setSizeDP(36)
                    setMarginsDP(0,0,0,5)
                    setOnClickListener {
                        Sound.knock.play()
                        if (reload) {
                            Playground.board.switch()
                        }
                        Activity.content.set(Game())
                    }
                    setBackgroundStateList {
                        addStatePressedGradient {
                            setColorId(R.color.Gold)
                            setCornerRadius(5)
                        }
                        addStateWildcardGradient {
                            setColorId(R.color.Transparent)
                            setCornerRadius(5)
                        }
                    }
                }
                addTextView {
                    setMarginsDP(20, 0, 0, 0)
                    setTextColorID(R.color.White)
                    setTextSizeSP(32f)
                    setIncludeFontPadding(true)
                    setPaddingsDP(0,5)

                    addReceiver(FooterSection.languageCode) {
                        setText(App.getString(R.string.SettingsTitle))
                        if("tlh".equals(it)){
                            setPaddingsDP(0,0,0,5)
                        }else{
                            setPaddingsDP(0,0,0,0)
                        }
                        setFontId(Activity.getCustomFontId())
                    }
                }
                addFiller()
            }
        }
    }


    fun onRelevantOptionChange() {
        ScoresArea.solved.set(IntPreference("score-${getPrefString()}", 0).get())
        reload = true
    }


    companion object {

        fun getPrefString(): String {
            val result = StringBuilder()
            addPref(result, Addition.enabled)
            addPref(
                result, when (Addition.enabled.get()) {
                    true -> Addition.carryOver
                    else -> Addition.enabled
                }
            )
            Addition.group.forEach {
                addPref(
                    result, when (Addition.enabled.get()) {
                        true -> it
                        else -> Addition.enabled
                    }
                )
            }
            addPref(result, Subtraction.enabled)
            addPref(
                result, when (Subtraction.enabled.get()) {
                    true -> Subtraction.carryOver
                    else -> Subtraction.enabled
                }
            )
            Subtraction.group.forEach {
                addPref(
                    result, when (Subtraction.enabled.get()) {
                        true -> it
                        else -> Subtraction.enabled
                    }
                )
            }
            addPref(result, Multiplication.enabled)
            Multiplication.group.forEach {
                addPref(
                    result, when (Multiplication.enabled.get()) {
                        true -> it
                        else -> Multiplication.enabled
                    }
                )
            }
            addPref(result, Division.enabled)
            Division.group.forEach {
                addPref(
                    result, when (Division.enabled.get()) {
                        true -> it
                        else -> Division.enabled
                    }
                )
            }
            addPref(result, PreferencesSection.joker)
            addPref(result, PreferencesSection.marker)
            result.append(
                when (GameVariantSection.variant.get()) {
                    Names.VariantOperators.name -> "0"
                    Names.VariantNumbers.name -> "1"
                    Names.VariantStandard.name -> "4"
                    else -> "3"
                }
            )

            var timer = PreferencesSection.countdown.get().toString()
            if (timer.length < 2) {
                result.append("0")
            }
            result.append(timer)

            return result.toString()
        }

        fun addPref(builder: StringBuilder, pref: BooleanPreference) {
            builder.append(
                when (pref.get()) {
                    true -> 1
                    else -> 0
                }
            )
        }
    }
}