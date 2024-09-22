package com.koje.puzzle.core

import com.koje.framework.graphics.ComponentGroup
import com.koje.framework.graphics.Position
import com.koje.framework.utils.Logger
import java.lang.Float.min
import kotlin.math.abs

class Symbol(var field: Field, var content: Char) : ComponentGroup(Playground) {

    private val offset = Position()
    var position = Position()
    var solution = field

    init {
        plane = 2
        locate(field)
        addImageComponent {
            image = Playground.picmap
            count = 400

            addProcedure {
                index = when (content) {
                    '0' -> 80
                    '1' -> 81
                    '2' -> 82
                    '3' -> 83
                    '4' -> 84
                    '5' -> 85
                    '6' -> 86
                    '7' -> 87
                    '8' -> 88
                    '9' -> 89
                    '-' -> 90
                    '+' -> 91
                    ':' -> 92
                    '*' -> 93
                    '=' -> 94
                    '?' -> 95

                    else -> 40
                }
                val posX = position.x + offset.x
                val posY = position.y + offset.y
                move(posX, posY)
                scale(0.105f)
            }
        }

        addProcedure {
        }
    }


    fun moveToField(posX: Float, posY: Float, target: Field) {
        position.set(position.x + offset.x, position.y + offset.y)
        offset.set(0f, 0f)

        addProcedure {
            if (hasReached(target)) {
                Logger.info(this, "snapped in")
                locate(target)
                if (Playground.board.hasErrors() == 0) {
                    Playground.board.onSolved()
                }
                finish()
            }
        }
    }

    private fun hasReached(target: Field): Boolean {
        val step = when (Playground.board.state == Names.Playing) {
            true -> 0.001f
            else -> 0.0001f
        } * Playground.loopTime

        var result = true

        if (position.x < target.position.x) {
            result = false
            position.x += min(step, abs(target.position.x - position.x))
        }

        if (position.x > target.position.x) {
            result = false
            position.x -= min(step, abs(target.position.x - position.x))
        }

        if (position.y < target.position.y) {
            result = false
            position.y += min(step, abs(target.position.y - position.y))
        }

        if (position.y > target.position.y) {
            result = false
            position.y -= min(step, abs(target.position.y - position.y))
        }

        return result
    }

    fun moveToPosition(posX: Float, posY: Float) {
        offset.set(posX - position.x, posY - position.y)
    }

    fun locate(target: Field) {
        field = target
        position.copyFrom(field.position)
    }
}