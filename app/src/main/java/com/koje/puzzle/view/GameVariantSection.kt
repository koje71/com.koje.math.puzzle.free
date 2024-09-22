package com.koje.puzzle.view

import android.view.Gravity
import com.koje.framework.App
import com.koje.framework.utils.StringPreference
import com.koje.framework.view.LinearLayoutBuilder
import com.koje.framework.view.RadioButtonBuilder
import com.koje.math.puzzle.R
import com.koje.puzzle.core.Names
import com.koje.puzzle.core.Sound
import com.koje.puzzle.data.Operations

class GameVariantSection(val settings: Settings) : LinearLayoutBuilder.Editor {

    override fun edit(target: LinearLayoutBuilder) {
        with(target) {
            add(SectionHeader(R.string.GameVariant))

            addLinearLayout {
                setPaddingsDP(5, 5)
                setOrientationVertical()
                add(RoundSectionFormat())
                addVariant(this, R.string.GameVariantStandart, Names.VariantStandard)
                addVariant(this, R.string.GameVariantMixer, Names.VariantMixer)
                addVariant(this, R.string.GameVariantNumbers, Names.VariantNumbers)
                addVariant(
                    this,
                    R.string.GameVariantOperators,
                    Names.VariantOperators
                )
            }
        }
    }

    fun addVariant(target: LinearLayoutBuilder, label: Int, value: Names) {
        target.addLinearLayout {
            setOrientationHorizontal()
            addRadioButton {
                setTextSizeSP(20f)
                view.gravity = Gravity.CENTER_VERTICAL
                setColor(R.color.Grey)

                setOnClickListener {
                    handleClick(this, value)
                }

                addReceiver(variant) {
                    setChecked(it.equals(value.name))
                }

                addReceiver(FooterSection.languageCode) {
                    setFontId(Activity.getCustomFontId())
                    setText(App.getString(label))
                }
            }
        }

    }


    fun handleClick(target: RadioButtonBuilder, value: Names) {
        Sound.knock.play()
        if (value == Names.VariantOperators && Operations.enabledCount() < 2) {
            target.setChecked(false)
            return

        }
        variant.set(value.name)
        settings.onRelevantOptionChange()
    }

    companion object {
        val variant = StringPreference("game-variant", "VariantStandard")

        fun hasVariant(value: Names): Boolean {
            return value.name.equals((variant.get()))
        }
    }
}
