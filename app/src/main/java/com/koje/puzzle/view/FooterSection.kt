package com.koje.puzzle.view

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import com.koje.framework.App
import com.koje.framework.utils.StringPreference
import com.koje.framework.view.LinearLayoutBuilder
import com.koje.math.puzzle.R
import com.koje.puzzle.core.Sound
import java.net.URLEncoder


class FooterSection(val settings: Settings) : LinearLayoutBuilder.Editor {

    override fun edit(target: LinearLayoutBuilder) {
        with(target) {

            addLinearLayout {
                setMarginsDP(20,20,20,20)
                setOrientationHorizontal()
                setGravityCenterVertical()
                setWidthMatchParent()

                addView {
                    setHeightDP(3)
                    setWidthDP(10)
                    setLayoutWeight(1f)
                    setBackgroundColorId(R.color.Grey)
                }

                addImageView {
                    setDrawableId(R.drawable.raute)
                    setSizeDP(20)
                    setMarginsDP(5,0,5,0)
                }

                addView {
                    setHeightDP(3)
                    setWidthDP(10)
                    setLayoutWeight(1f)
                    setBackgroundColorId(R.color.Grey)
                }
            }


            addLinearLayout {
                setOrientationHorizontal()
                setGravityCenterVertical()

                addTextView {
                    setTextSizeSP(24f)
                    setTextColorID(R.color.Grey)

                    addReceiver(languageCode) {
                        setFontId(Activity.getCustomFontId())
                        setText(App.getString(R.string.ShareLink))
                    }
                }

                addFiller()

                addImageView {
                    setSizeDP(30)
                    setDrawableId(R.drawable.share)
                }

                setBackgroundGradient {
                    setCornerRadius(10)
                    setStroke(2, R.color.Grey)

                    addReceiver(Activity.overlay) {
                        setColorId(
                            when (it is ScannerOverlay) {
                                true -> R.color.GoldLight
                                else -> R.color.White
                            }
                        )
                    }
                }

                setPaddingsDP(20, 8)
                setOnClickListener {
                    Sound.knock.play()
                    Activity.overlay.set(ScannerOverlay())
                }
            }

            addLinearLayout {
                setOrientationHorizontal()
                setGravityCenterVertical()
                setMarginsDP(0, 10, 0, 20)

                addTextView {
                    setTextSizeSP(24f)
                    setTextColorID(R.color.Grey)

                    addReceiver(languageCode) {
                        setFontId(Activity.getCustomFontId())
                        setText(App.getString(R.string.Language))
                    }
                }

                addFiller()

                addImageView {
                    setHeightDP(30)
                    setWidthDP(40)
                    setScaleTypeFitXY()
                    setBackgroundColorId(R.color.Black)
                    setPaddingsDP(1, 1)
                    addReceiver(languageCode) {
                        setDrawableId(LanguagesOverlay.getImageFromLanguageCode(it))
                    }
                }

                setBackgroundGradient {
                    setCornerRadius(10)
                    setStroke(2, R.color.Grey)

                    addReceiver(Activity.overlay) {
                        setColorId(
                            when (it is LanguagesOverlay) {
                                true -> R.color.GoldLight
                                else -> R.color.White
                            }
                        )
                    }
                }

                setPaddingsDP(20, 8)
                setOnClickListener {
                    Sound.knock.play()
                    Activity.overlay.set(LanguagesOverlay())
                }
            }

        }

    }

    companion object {
        val languageCode = StringPreference("LanguageCode", App.getString(R.string.LanguageCode))

    }

}
