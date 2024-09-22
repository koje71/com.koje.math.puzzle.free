package com.koje.puzzle.core

import android.view.MotionEvent
import com.koje.framework.App
import com.koje.framework.graphics.ComponentGroup
import com.koje.framework.graphics.Position
import com.koje.framework.utils.IntPreference
import com.koje.framework.utils.Logger
import com.koje.math.puzzle.R
import com.koje.puzzle.data.Excercise
import com.koje.puzzle.view.Activity
import com.koje.puzzle.view.Forward
import com.koje.puzzle.view.Game
import com.koje.puzzle.view.GameVariantSection
import com.koje.puzzle.view.PreferencesSection
import com.koje.puzzle.view.ScoresArea
import com.koje.puzzle.view.Settings
import com.koje.puzzle.view.StatisticSection
import kotlin.random.Random

class Board : ComponentGroup(Playground) {


    private val excercieses = createExercises()
    private var selection: Symbol? = null
    private val offset = Position()
    val fields = mutableListOf<Field>()
    val symbols = mutableListOf<Symbol>()
    val indicators = mutableListOf<Indicator>()
    var state = Names.Playing
    var timer = PreferencesSection.countdown.get() * 60


    init {
        initTimer()
        ScoresArea.solved.set(IntPreference("score-${Settings.getPrefString()}", 0).get())

        for (i in excercieses.indices) {
            for (x in excercieses[i].content.indices) {
                val ch = excercieses[i].content[x]
                val field = Field(x, i, this)
                fields.add(field)
                addComponent(field)
                if (ch != ' ') {
                    val symbol = Symbol(field, ch)
                    symbols.add(symbol)
                    addComponent(symbol)
                }
            }
        }

        for (i in 0..6) {
            val element = Indicator(this, i)
            addComponent(element)
            indicators.add(element)
        }

        when (GameVariantSection.variant.get()) {
            Names.VariantNumbers.name -> prepareForGameVariantNumbers()
            Names.VariantOperators.name -> prepareForGameVariantOperators()
            Names.VariantMixer.name -> prepareForGameVariantMixer()
            Names.VariantStandard.name -> prepareForGameVariantStandard()
        }

        if (PreferencesSection.joker.get()) {
            addJoker()
        }

    }

    fun prepareForGameVariantStandard() {
        val types = listOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')

        val targets = mutableListOf<Field>()
        intArrayOf(0, 1).forEach { locY ->
            intArrayOf(4, 3, 5, 2, 6, 1, 7, 0, 8).forEach { locX ->
                val entry = getField(locX, locY)
                if (entry != null) {
                    Logger.info(this, "target: $locX, $locY")
                    targets.add(entry)
                }
            }
        }

        val candidates = mutableListOf<Symbol>()
        intArrayOf(2, 3, 4, 5, 6).forEach { y ->
            var lastToken = false
            symbols.forEach {
                if (it.field.locY == y) {
                    if (lastToken && types.contains(it.content)) {
                        candidates.add(it)
                    }
                    if (it.content == '=') {
                        lastToken = true
                    }
                }
            }
        }

        candidates.shuffle()
        candidates.forEachIndexed { index, symbol ->
            if (index < targets.size) {
                val target = targets.get(index)
                symbol.field.addMarker()
                symbol.locate(target)
                target.addMarker()
            }
        }
    }


    fun prepareForGameVariantOperators() {
        val types = listOf('+', '-', '*', ':')
        val candidates = mutableListOf<Symbol>()
        symbols.forEach {
            if (types.contains(it.content)) {
                candidates.add(it)
            }
        }

        prepareBottomLine(candidates)
    }

    fun prepareBottomLine(candidates: MutableList<Symbol>) {
        val rows = mutableSetOf<Int>()
        var posX = 2
        candidates.shuffle()
        candidates.forEach {
            if (!rows.contains(it.field.locY)) {
                val target = getField(posX, 0)
                if (target != null) {
                    it.field.addMarker()
                    rows.add(it.field.locY)
                    it.locate(target)
                    target.addMarker()
                    posX++
                }
            }
        }
    }

    fun prepareForGameVariantNumbers() {
        val types = listOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
        val candidates = mutableListOf<Symbol>()
        symbols.forEach {
            if (types.contains(it.content)) {
                candidates.add(it)
            }
        }

        prepareBottomLine(candidates)
    }

    fun getField(locX: Int, locY: Int): Field? {
        fields.forEach {
            if (it.locX == locX && it.locY == locY) {
                return it
            }
        }
        return null
    }

    fun prepareForGameVariantMixer() {
        val types = listOf('1', '2', '3', '4', '5', '6', '7', '8', '9')
        val candidates = mutableListOf<Symbol>()
        symbols.forEach {
            if (types.contains(it.content)) {
                candidates.add(it)
            }
        }
        if (candidates.size > 2) {
            while (hasErrors() < 3) {
                for (i in 0..3) {
                    candidates.shuffle()
                    val temp = candidates[0].field
                    candidates[0].locate(candidates[1].field)
                    candidates[0].field.addMarker()
                    candidates[1].locate(temp)
                    candidates[1].field.addMarker()
                }
            }
        }
    }


    fun addJoker() {
        val types = listOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
        val candidates = mutableListOf<Symbol>()
        symbols.forEach {
            if (types.contains(it.content)) {
                candidates.add(it)
            }
        }
        if (candidates.size > 0) {
            candidates.random().content = '?'
        }
    }

    private fun initTimer() {
        Activity.timer.set(timer)
        Thread {
            Thread.sleep(1000)
            while (timer > 0) {
                Thread.sleep(1000)
                if (Activity.content.get() is Game && Activity.playing) {
                    timer--
                    if (this@Board == Playground.board) {
                        Activity.timer.set(timer)
                        if (timer == 0) solve()
                    }
                }
            }
        }.start()
    }

    open fun onTouch(touch: Position, event: MotionEvent) {
        if (state != Names.Playing) {
            return
        }
        when (event.action) {
            MotionEvent.ACTION_DOWN -> onTouchDown(touch)
            MotionEvent.ACTION_MOVE -> onTouchMove(touch)
            MotionEvent.ACTION_UP -> onTouchUp(touch)
        }

        Logger.info(this, "action: ${event.action}")
    }

    fun onTouchDown(touch: Position) {
        Logger.info(this, "on touch down")
        var distanceMax = 100f
        var nearest: Symbol? = null

        components.forEach {
            if (it is Symbol) {
                val distanceNew = touch.distanceTo(it.position)
                if (distanceNew < distanceMax && distanceNew < 0.1f) {
                    distanceMax = distanceNew
                    nearest = it
                }
            }
        }

        if (nearest != null) {
            selection = nearest
            Logger.info(this, "selection ${selection!!.content}")
            offset.x = touch.x - nearest!!.position.x
            offset.y = touch.y - nearest!!.position.y
        }
    }

    private fun onTouchMove(touch: Position) {
        selection?.moveToPosition(touch.x - offset.x, touch.y - offset.y)
    }

    private fun onTouchUp(touch: Position) {
        var distanceMax = 100f
        var nearest: Field? = null
        Sound.pong.play()

        getFreeFields().forEach {

            val distanceNew = touch.distanceTo(it.position)
            if (distanceNew < distanceMax) {
                distanceMax = distanceNew
                nearest = it
            }
        }

        if (nearest != null) {
            Logger.info(this, "found: ${nearest!!.locX} ${nearest!!.locY}")
            selection?.moveToField(touch.x - offset.x, touch.y - offset.y, nearest!!)
        }

        selection = null
    }

    private fun getFreeFields(): List<Field> {
        val result = mutableListOf<Field>()

        fields.forEach {
            result.add(it)
        }

        symbols.forEach {
            if (it != selection) {
                if (result.contains(it.field)) {
                    result.remove(it.field)
                }
            }
        }

        return result
    }


    fun hasErrors(): Int {
        var result = 0
        indicators.forEach {
            result++
            it.check()
            if (it.solved) {
                result--
            }
        }
        return result
    }

    private fun createExercises(): List<Excercise> {
        val result = mutableListOf<Excercise>()

        result.add(Excercise("  "))
        while (result.size < 6) {
            val creation = Excercise()
            var duplicate = false
            result.forEach {
                if (it.content == creation.content) {
                    duplicate = true
                }
            }
            if (!duplicate) {
                result.add(creation)
            }
        }
        if (GameVariantSection.variant.get().equals(Names.VariantMixer.name)) {
            result.add(Excercise("  "))
        } else {
            result.add(0, Excercise(" "))
        }


        result.forEach {
            while (it.content.length < 9) {
                it.content = when (Random.nextBoolean()) {
                    true -> " ${it.content}"
                    else -> "${it.content} "
                }
            }
        }

        return result
    }


    fun switch() {
        addProcedure {
            progress += 0.002f * Playground.loopTime
            scale(1f - progress)
            if (progress >= 1f) {
                death = true
                with(Board()) {
                    Playground.board = this
                    Playground.addComponent(this)
                    increase()
                }
            }
        }
    }

    private fun increase() {
        addProcedure {
            progress += 0.002f * Playground.loopTime
            scale(progress)
            if (progress >= 1f) {
                hasErrors()
            }
        }
    }

    fun onSolved() {
        if (state == Names.Playing) {
            state = Names.Solved
            ScoresArea.total.increase()
            ScoresArea.solved.increase()
            IntPreference("score-${Settings.getPrefString()}", 0).increase()
            Activity.content.set(Forward(App.getString(R.string.Congratulation)))
            Sound.applause.play()
            StatisticSection.increaseStatistics()
        }
        if (state == Names.Solving) {
            state = Names.Solved
            Activity.content.set(Forward(App.getString(R.string.Solution)))
        }
    }

    fun solve() {
        if (state != Names.Solving) {
            Sound.transporter.play()
            state = Names.Solving
            symbols.forEach {
                // felder mit richtigem symbol aber falscher solution brauchen nicht
                // verschoben werden
                if (it.field != it.solution) {
                    symbols.forEach { other ->
                        if (it.field == other.solution && it.content == other.content) {
                            val temp = other.solution
                            other.solution = it.solution
                            it.solution = temp
                        }
                    }

                }
            }
            symbols.forEach {
                it.moveToField(it.position.x, it.position.y, it.solution)
            }
        }
    }

}