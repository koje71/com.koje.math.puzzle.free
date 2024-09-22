package com.koje.puzzle.view

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import com.koje.framework.App
import com.koje.framework.utils.StringPreference
import com.koje.framework.view.LinearLayoutBuilder
import com.koje.math.puzzle.R
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class StatisticSection(val settings: Settings) : LinearLayoutBuilder.Editor {

    override fun edit(target: LinearLayoutBuilder) {
        with(target) {
            add(SectionHeader(R.string.StatisticsHeader))
            addLinearLayout {
                setOrientationVertical()
                add(RoundSectionFormat())

                val image = Bitmap.createBitmap(1000, 200, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(image)

                val data = mutableListOf<Int>()
                var days30 = 0
                var days10 = 0
                var days0 = 0
                loadStatistics().forEachIndexed { index, i ->
                    if (index > 0) {
                        data.add(0, i)
                        days30 += i
                        if (data.size < 2) days0 += i
                        if (data.size < 11) days10 += i
                    }
                }
                drawGraph(
                    canvas,
                    data,
                    target.getColorFromID(R.color.Grey),
                    target.getColorFromID(R.color.Gold)
                )


                addImageView {
                    setPaddingsDP(5, 5)
                    setWidthMatchParent()
                    setHeightWrapContent()
                    setImageBitmap(image)
                    setScaleTypeFitXY()
                }

                addLinearLayout {
                    setOrientationHorizontal()
                    setGravityCenterVertical()
                    setPaddingsDP(5,0,5,5)
                   // setHeightDP(30)
                    addTextView {
                        setPaddingsDP(5, 0, 5, 3)
                        setTextSizeSP(18f)

                        addReceiver(FooterSection.languageCode) {
                            setText(getFooterText(days0, days10, days30))
                            setFontId(Activity.getCustomFontId())
                        }
                    }
                }
            }
        }
    }

    private fun getFooterText(days0: Int, days10: Int, days30: Int): String {
        with(StringBuilder()) {
            append(days0)
            append(" ")
            append(App.getString(R.string.SolvedToday))
            append(", ")
            append(days10)
            append(" ")
            append(App.getString(R.string.SolvedDays10))
            append(", ")
            append(days30)
            append(" ")
            append(App.getString(R.string.SolvedDays30))

            return (toString())
        }
    }

    private fun drawGraph(target: Canvas, scores: List<Int>, color: Int, raster: Int) {

        val paint = Paint()

        paint.style = Paint.Style.FILL
        paint.strokeWidth = 1f
        paint.color = raster
        paint.strokeCap = Paint.Cap.ROUND

        for (rasterX in 10..target.width step 20) {
            target.drawLine(
                rasterX.toFloat(),
                0f,
                rasterX.toFloat(),
                target.height.toFloat(),
                paint
            )
        }

        for (rasterY in 10..target.height step 20) {
            target.drawLine(0f, rasterY.toFloat(), target.width.toFloat(), rasterY.toFloat(), paint)
        }


        paint.style = Paint.Style.FILL
        paint.strokeWidth = 10f
        paint.color = color
        paint.strokeCap = Paint.Cap.ROUND

        var oldX = 0f
        var oldY = target.height.toFloat()


        var maximum = 0
        scores.forEachIndexed { index, i ->
            if (i > maximum) {
                maximum = i
            }
        }

        if (maximum > 0) {
            scores.forEachIndexed { index, i ->
                val posX = (target.width / 30 * index).toFloat()
                val posY = (target.height - (target.height / maximum.toFloat() * i)) * 0.98f
                target.drawLine(oldX, oldY, posX, posY, paint)
                oldX = posX
                oldY = posY
            }
        }

    }

    companion object {
        val statistics = StringPreference("statistics30", "0")

        fun loadStatistics(): MutableList<Int> {
            val start = LocalDate.of(2023, 1, 1)
            val current = ChronoUnit.DAYS.between(start, LocalDate.now()).toInt()
            val temp = mutableListOf<Int>()

            statistics.get().split(",").forEach {
                temp.add(Integer.parseInt(it))
            }

            while (temp.size < 32) {
                temp.add(0)
            }

            while (temp[0] < current) {
                temp[0]++
                temp.add(1, 0)
            }

            val result = mutableListOf<Int>()
            for (i in 0..31) {
                result.add(temp[i])
            }

            return result
        }

        fun saveStatistics(data: MutableList<Int>) {
            val result = StringBuilder()
            data.forEach {
                if (result.isNotEmpty()) {
                    result.append(",")
                }
                result.append(it)
            }

            statistics.set(result.toString())
        }

        fun increaseStatistics() {
            val data = loadStatistics()
            data[1] = data[1] + 1
            saveStatistics(data)
        }
    }


}
