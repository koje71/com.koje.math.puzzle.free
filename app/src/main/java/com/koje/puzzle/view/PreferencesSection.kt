package com.koje.puzzle.view

import com.koje.framework.App
import com.koje.framework.utils.BooleanPreference
import com.koje.framework.utils.IntPreference
import com.koje.framework.view.LinearLayoutBuilder
import com.koje.math.puzzle.R
import com.koje.puzzle.core.Sound

class PreferencesSection(val settings: Settings) : LinearLayoutBuilder.Editor {

    override fun edit(target: LinearLayoutBuilder) {
        with(target) {
            add(SectionHeader(R.string.PreferencesHeader))
            addLinearLayout {
                setOrientationVertical()
                setPaddingsDP(10, 10)
                add(RoundSectionFormat())
                addCheckbox(this, marker, R.string.PreferencesMarker)
                addCheckbox(this, joker, R.string.PreferencesJoker)
                addCheckbox(this, Sound.enabled, R.string.PreferencesSound)

                addLinearLayout {
                    setOrientationHorizontal()
                    setGravityBottom()
                    addTextView {
                        setTextColorID(R.color.Grey)
                        setTextSizeSP(20f)
                        setPaddingsDP(0, 0, 5, 12)

                        addReceiver(FooterSection.languageCode) {
                            setText(App.getString(R.string.PreferencesCountdown))
                            setFontId(Activity.getCustomFontId())
                        }
                    }
                    addSlider {
                        setRange(0f, 10f)
                        setValue(countdown.get().toFloat())
                        setStepSize(1f)
                        setTrackColor(R.color.Grey)
                        setThumbColor(R.color.Grey)
                        setMarginsDP(0, 15, 0, 0)
                        setWidthMatchParent()
                        view.setHaloRadiusResource(R.dimen.SliderHalo)
                        setOnChangeListener {
                            countdown.set(it.toInt())
                            settings.onRelevantOptionChange()
                        }
                    }
                }
            }
        }
    }

    private fun addCheckbox(target: LinearLayoutBuilder, option: BooleanPreference, label: Int) {
        target.addLinearLayout {
            setOrientationHorizontal()
            addCheckbox {
                setTextSizeSP(20f)
                setColor(R.color.Grey)
                setOnClickListener {
                    Sound.knock.play()
                    option.switch()
                    if (option != Sound.enabled) {
                        settings.onRelevantOptionChange()
                    }
                }
                addReceiver(option) {
                    setChecked(it)
                }

                addReceiver(FooterSection.languageCode) {
                    setFontId(Activity.getCustomFontId())
                    setText(App.getString(label))
                }
            }
        }
    }

    companion object {
        val joker = BooleanPreference("joker-enabled", true)
        val countdown = IntPreference("countdown", 0)
        val marker = BooleanPreference("marker", false)
    }
}
