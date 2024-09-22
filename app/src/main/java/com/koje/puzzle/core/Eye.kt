package com.koje.puzzle.core

import com.koje.framework.graphics.ImageComponent
import com.koje.framework.graphics.Position
import java.lang.Float.max
import kotlin.math.abs
import kotlin.math.atan

class Eye(val posX: Float) : ImageComponent(Playground) {

    private var angle = 120f

    init {
        image = Playground.picmap
        count = 400
        index = 120

        addProcedure {
            move(posX, getPosY())
            scale(0.08f)
            rotate(angle)

        }
    }

    fun getPosY(): Float {
        return max(surface.ratio / 2f - 0.05f, 0.65f)
    }

    fun lookAt(position: Position) {
        val posY = getPosY()
        val deltaX = abs(posX - position.x).toDouble()
        val deltaY = abs(posY - position.y).toDouble()
        val angleN = Math.toDegrees(atan(deltaY / deltaX)).toFloat()

        if (posY > position.y) {
            if (posX > position.x) {
                angle = 180 - angleN
            }
            if (posX < position.x) {
                angle = angleN
            }
        }

        if (posY < position.y) {
            if (posX > position.x) {
                angle = 180 + angleN
            }
            if (posX < position.x) {
                angle = 360f - angleN
            }
        }
    }
}