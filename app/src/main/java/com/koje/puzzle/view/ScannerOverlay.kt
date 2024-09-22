package com.koje.puzzle.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.koje.framework.view.FrameLayoutBuilder
import com.koje.math.puzzle.R
import com.koje.puzzle.core.Sound


class ScannerOverlay : FrameLayoutBuilder.Editor {
    override fun edit(target: FrameLayoutBuilder) {
        target.addRelativeLayout {
            setGravityCenter()


            addLinearLayout {
                setOrientationVertical()
                setMarginsDP(20, 20, 20, 20)

                addImageView {
                    setDrawableId(R.drawable.scanner)
                    setPaddingsDP(10, 10)
                }

                addTextView {
                    val url = "https://play.google.com/store/apps/details?id=com.koje.math.puzzle.free"
                    setText(url)
                    setFontId(Activity.getCustomFontId())
                    setTextSizeSP(24f)
                    setPaddingsDP(20, 0, 20, 10)
                    setGravityCenter()

                    setOnClickListener {
                        Sound.knock.play()
                        openStore(view.context)
                    }
                }

                add(RoundSectionFormat())
            }

            setOnClickListener {
                Sound.knock.play()
                Activity.overlay.set(EmptyOverlay())
            }
        }
    }

    fun openStore(context: Context) {
        with(context) {
            val appPackageName = "com.koje.math.puzzle.free"
            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$appPackageName")
                    )
                )
            } catch (anfe: android.content.ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                    )
                )
            }
        }
    }
}