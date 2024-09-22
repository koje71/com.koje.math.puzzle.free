package com.koje.puzzle.view

import com.koje.framework.App
import com.koje.framework.utils.BooleanPreference
import com.koje.framework.view.LinearLayoutBuilder
import com.koje.framework.view.ViewBuilder
import com.koje.math.puzzle.R
import com.koje.puzzle.core.Names
import com.koje.puzzle.core.Sound
import com.koje.puzzle.data.Addition
import com.koje.puzzle.data.Division
import com.koje.puzzle.data.Multiplication
import com.koje.puzzle.data.Operations
import com.koje.puzzle.data.OptionGroup
import com.koje.puzzle.data.OptionPreference
import com.koje.puzzle.data.Subtraction

class OperationsSection(val settings: Settings) : LinearLayoutBuilder.Editor {

    override fun edit(target: LinearLayoutBuilder) {
        with(target) {
            add(SectionHeader(R.string.OperationsHeader))
            addAdditionSection(this)
            addSubtractionSection(this)
            addMultiplicationSection(this)
            addDivisionSection(this)

        }
    }

    private fun addAdditionSection(target: LinearLayoutBuilder) {
        with(target) {
            addSectionHeader2(this, Addition.enabled, Addition.carryOver)
            addHorizontalScrollView {
                addLinearLayout {
                    setOrientationHorizontal()
                    Addition.group.forEach {
                        addSectionOption(this, it, Addition)
                    }
                }
                setMarginsDP(0, 0, 0, 20)
            }
        }
    }

    private fun addSubtractionSection(target: LinearLayoutBuilder) {
        with(target) {
            addSectionHeader2(this, Subtraction.enabled, Subtraction.carryOver)
            addHorizontalScrollView {
                addLinearLayout {
                    setOrientationHorizontal()
                    Subtraction.group.forEach {
                        addSectionOption(this, it, Subtraction)
                    }
                }
                setMarginsDP(0, 0, 0, 20)
            }
        }
    }

    private fun addMultiplicationSection(target: LinearLayoutBuilder) {
        with(target) {
            addSectionHeader(this, Multiplication.enabled)
            addHorizontalScrollView {
                addLinearLayout {
                    setOrientationHorizontal()
                    Multiplication.group.forEach {
                        addSectionOption(this, it, Multiplication)
                    }
                }
                setMarginsDP(0, 0, 0, 20)
            }
        }
    }

    private fun addDivisionSection(target: LinearLayoutBuilder) {
        with(target) {
            addSectionHeader(this, Division.enabled)
            addHorizontalScrollView {
                addLinearLayout {
                    setOrientationHorizontal()
                    Division.group.forEach {
                        addSectionOption(this, it, Division)
                    }
                }
            }
        }
    }

    private fun addSectionHeader(
        target: LinearLayoutBuilder,
        option: OptionPreference
    ) {
        target.addLinearLayout {
            setOrientationHorizontal()
            setMarginsDP(3, 0, 3, 3)
            // setHeightDP(40)
            setGravityCenterVertical()
            addTextView {
                setTextSizeSP(28f)
                setTextColorID(R.color.Grey)
                setPaddingsDP(8, 0)

                addReceiver(FooterSection.languageCode) {
                    setFontId(Activity.getCustomFontId())
                    setText(App.getString(option.label))
                }
            }
            addFiller()

            addReceiver(option) {
                updateOption(this, it)
            }

            setOnClickListener {
                if (checkGameVariant(option)) {
                    Operations.switch(option)
                    Sound.knock.play()
                    settings.onRelevantOptionChange()
                }
            }
        }
    }

    private fun addSectionHeader2(
        target: LinearLayoutBuilder,
        option: OptionPreference,
        option2: BooleanPreference

    ) {
        target.addLinearLayout {
            setOrientationHorizontal()
            setGravityCenterVertical()
            setMarginsDP(3, 0, 3, 3)
            // setHeightDP(40)
            setGravityCenterVertical()
            addTextView {
                setTextSizeSP(28f)
                setTextColorID(R.color.Grey)
                setPaddingsDP(8, 0)

                addReceiver(FooterSection.languageCode) {
                    setFontId(Activity.getCustomFontId())
                    setText(App.getString(option.label))
                }
            }
            addFiller()

            addFrameLayout {
                addLinearLayout {
                    setOrientationHorizontal()
                    setGravityCenterVertical()


                    addImageView {
                        if (option2 == Addition.carryOver) setDrawableId(R.drawable.carryup)
                        if (option2 == Subtraction.carryOver) setDrawableId(R.drawable.carrydown)
                        setSizeDP(40)
                    }

                    addCheckbox {
                        setMarginsDP(10, 0, 10, 0)
                        setChecked(option2.get())
                        addReceiver(option2) {
                            setChecked(it)
                        }
                    }
                }
                addFrameLayout {
                    // hier werden die Klicks auf das Bild und auf die Checkbox abgefangen
                    setOnClickListener {
                        if (option.get()) {
                            option2.switch()
                            Sound.knock.play()
                            settings.onRelevantOptionChange()
                        }
                    }
                }
            }

            addReceiver(option) {
                updateOption(this, it)
            }

            setOnClickListener {
                if (checkGameVariant(option)) {
                    Operations.switch(option)
                    Sound.knock.play()
                    settings.onRelevantOptionChange()
                }
            }
        }
    }

    fun checkGameVariant(preference: OptionPreference): Boolean {

        if (preference.get() && GameVariantSection.hasVariant(Names.VariantOperators) && Operations.enabledCount() < 3) {
            return false
        }

        return true
    }

    private fun addSectionOption(
        target: LinearLayoutBuilder,
        option: OptionPreference,
        group: OptionGroup
    ) {
        target.addTextView {
            setMarginsDP(3, 3, 3, 6)

            addReceiver(FooterSection.languageCode){
                setFontId(Activity.getCustomFontId(true))
            }
            setTextSizeSP(20f)
            setTextColorID(R.color.Grey)
            setPaddingsDP(8, 0, 10, 3)
            setHeightDP(40)
            setGravityCenter()

            addReceiver(option) {
                updateOption(this, it)
            }


            setText(App.getString(option.label))

            setOnClickListener {
                group.switch(option)
                Sound.knock.play()
                settings.onRelevantOptionChange()
            }
        }
    }

    private fun updateOption(target: ViewBuilder, enabled: Boolean) {
        with(target) {
            setBackgroundGradient {
                setCornerRadius(10)
                setStroke(2, R.color.Grey)
                setColorId(
                    when (enabled) {
                        true -> R.color.White
                        else -> R.color.Wood
                    }
                )
            }
        }
    }


}
