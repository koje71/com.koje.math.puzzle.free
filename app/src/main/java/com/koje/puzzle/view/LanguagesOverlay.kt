package com.koje.puzzle.view

import com.koje.framework.App
import com.koje.framework.events.IntNotifier
import com.koje.framework.view.FrameLayoutBuilder
import com.koje.framework.view.LinearLayoutBuilder
import com.koje.math.puzzle.R
import com.koje.puzzle.core.Sound


class LanguagesOverlay : FrameLayoutBuilder.Editor {
    override fun edit(target: FrameLayoutBuilder) {
        target.addRelativeLayout {
            setGravityCenter()
            setWidthMatchParent()
            setPaddingsDP(20, 20, 20, 0)

            addScrollView {
                setFillViewPort()
                addLinearLayout {
                    setOrientationVertical()
                    setPaddingsDP(10,10)

                    addTextView {
                        setGravityCenter()
                        setTextSizeSP(30f)
                        addReceiver(FooterSection.languageCode) {
                            setFontId(Activity.getCustomFontId())
                            setText(App.getString(R.string.Language))
                        }
                        setMarginsDP(0, 0, 0, 5)
                    }

                    addLinearLayout {
                        setOrientationHorizontal()
                        addFlag(this, "ar")
                        addFlag(this, "bg")
                        addFlag(this, "cs")
                        addFlag(this, "da")
                        addFlag(this, "de")
                    }

                    addLinearLayout {
                        setOrientationHorizontal()
                        addFlag(this, "el")
                        addFlag(this, "en")
                        addFlag(this, "eo")
                        addFlag(this, "es")
                        addFlag(this, "et")
                    }

                    addLinearLayout {
                        setOrientationHorizontal()
                        addFlag(this, "fi")
                        addFlag(this, "fr")
                        addFlag(this, "hi")
                        addFlag(this, "in")
                        addFlag(this, "it")
                    }

                    addLinearLayout {
                        setOrientationHorizontal()
                        addFlag(this, "iw")
                        addFlag(this, "ja")
                        addFlag(this, "ka")
                        addFlag(this, "lt")
                        addFlag(this, "lv")
                    }

                    addLinearLayout {
                        setOrientationHorizontal()
                        addFlag(this, "mn")
                        addFlag(this, "no")
                        addFlag(this, "pt")
                        addFlag(this, "ro")
                        addFlag(this, "sw")
                    }

                    addLinearLayout {
                        setOrientationHorizontal()
                        addFlag(this, "th")
                        addFlag(this, "tlh")
                        addFlag(this, "tr")
                        addFlag(this, "uk")
                        addFlag(this, "zh")
                    }



                    add(RoundSectionFormat())

                }
            }

            setOnClickListener {
                close()
            }
        }
    }

    fun addFlag(target: LinearLayoutBuilder, code: String) {
        target.addLinearLayout {
            setOrientationVertical()
            setMarginsDP(5, 5, 5, 5)
            addImageView {
                setDrawableId(getImageFromLanguageCode(code))
                setHeightDP(35)
                setWidthDP(50)
                setScaleTypeFitXY()
                setBackgroundColorId(R.color.Black)
                setPaddingsDP(1, 1)
            }

            addTextView {
                setText(code)
                setTextSizeSP(16f)
                setMarginsDP(0, 4, 0, 0)
                setGravityCenter()

                addReceiver(FooterSection.languageCode) {
                    if (it == code) {
                        setBackgroundGradient {
                            setCornerRadius(5)
                            setColorId(R.color.GoldLight)
                        }
                    } else {
                        setBackgroundGradient {
                            setCornerRadius(5)
                            setColorId(R.color.Transparent)
                        }
                    }
                }

            }

            setOnClickListener {
                if (FooterSection.languageCode.contains(code)) {
                    close()
                } else {
                    Sound.knock.play()
                    App.overrideLocale(code)
                    FooterSection.languageCode.set(code)
                }
            }
        }

    }

    fun close() {
        Sound.knock.play()
        Activity.overlay.set(EmptyOverlay())
    }

    companion object {

        fun getImageFromLanguageCode(code: String): Int {
            return when (code) {
                "ar" -> R.drawable.locale_ar
                "bg" -> R.drawable.locale_bg
                "cs" -> R.drawable.locale_cs
                "da" -> R.drawable.locale_da
                "de" -> R.drawable.locale_de
                "el" -> R.drawable.locale_el
                "en" -> R.drawable.locale_en
                "eo" -> R.drawable.locale_eo
                "es" -> R.drawable.locale_es
                "et" -> R.drawable.locale_et
                "fi" -> R.drawable.locale_fi
                "fr" -> R.drawable.locale_fr
                "hi" -> R.drawable.locale_hi
                "in" -> R.drawable.locale_in
                "it" -> R.drawable.locale_it
                "iw" -> R.drawable.locale_iw
                "ja" -> R.drawable.locale_ja
                "ka" -> R.drawable.locale_ka
                "lt" -> R.drawable.locale_lt
                "lv" -> R.drawable.locale_lv
                "mn" -> R.drawable.locale_mn
                "no" -> R.drawable.locale_no
                "pt" -> R.drawable.locale_pt
                "ro" -> R.drawable.locale_ro
                "sw" -> R.drawable.locale_sw
                "uk" -> R.drawable.locale_uk
                "th" -> R.drawable.locale_th
                "tlh" -> R.drawable.locale_tlh
                "tr" -> R.drawable.locale_tr
                "zh" -> R.drawable.locale_zh
                else -> 0
            }
        }

        val currentFlag =
            IntNotifier(getImageFromLanguageCode(App.getString(R.string.LanguageCode)))
    }
}