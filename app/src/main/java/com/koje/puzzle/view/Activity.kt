package com.koje.puzzle.view

import android.view.WindowManager
import com.koje.framework.App
import com.koje.framework.events.IntNotifier
import com.koje.framework.events.Notifier
import com.koje.framework.utils.Text
import com.koje.framework.view.BaseActivity
import com.koje.framework.view.FrameLayoutBuilder
import com.koje.math.puzzle.R
import com.koje.puzzle.core.Playground
import com.koje.puzzle.core.Sound

class Activity : BaseActivity() {

    override fun setup(target: FrameLayoutBuilder) {
        with(window) {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = target.getColorFromID(R.color.Black)
            navigationBarColor = target.getColorFromID(R.color.Black)
        }
        actionBar?.hide()
        Sound.init()

        App.overrideLocale(FooterSection.languageCode.get())
    }

    override fun createLayout(target: FrameLayoutBuilder) {
        with(target) {
            addLinearLayout {
                setOrientationVertical()
                addFrameLayout {
                    setBackgroundColorId(R.color.Black)
                    setLayoutWeight(1f)
                    addLinearLayout {
                        setOrientationVertical()
                        addFrameLayout {
                            setHeightDP(70)
                            setWidthMatchParent()
                            setBackgroundColorId(R.color.Black)
                        }
                        addFrameLayout {

                            addSurfaceView {
                                setWidthMatchParent()
                                setLayoutWeight(1f)
                                setSurface(Playground)
                            }
                            addLinearLayout {
                                setOrientationVertical()
                                addLinearLayout {
                                    setOrientationHorizontal()
                                    setMarginsDP(10, 0, 0, 0)
                                    addTextView {
                                        setTextColorID(R.color.Grey)
                                        setTextSizeSP(16f)
                                        addReceiver(FooterSection.languageCode){
                                            setFontId(getCustomFontId(true))
                                        }
                                        addReceiver(timer) {
                                            setText(Text.getFormattedDuration(it))
                                        }
                                    }
                                    addReceiver(PreferencesSection.countdown) {
                                        when (it > 0) {
                                            true -> setVisibleTrue()
                                            else -> setVisibleGone()
                                        }
                                    }
                                }
                            }
                        }
                    }
                    addFrameLayout {
                        setSizeMatchParent()
                        addReceiver(content) {
                            replaceWithFade(it)
                        }
                    }
                    addFrameLayout {
                        setSizeMatchParent()
                        addReceiver(overlay) {
                            replaceWithFade(it)
                        }
                    }

                }
                add(ScoresArea())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        playing = true
    }

    override fun onPause() {
        super.onPause()
        playing = false
    }

    companion object {
        val timer = IntNotifier(300)
        val content = Notifier<FrameLayoutBuilder.Editor>(Game())
        val overlay = Notifier<FrameLayoutBuilder.Editor>(EmptyOverlay())
        val demoMode = false
        var playing = false

        fun getCustomFontId(isNumber:Boolean=false):Int{
            if("tlh".equals(App.getString(R.string.LanguageCode))){
                if(!isNumber){
                    // https://fontmeme.com/fonts/klingon-font/
                    // free for comercial use
                    return R.font.klingon
                }
            }

            return R.font.nunito_bold
        }
    }



}