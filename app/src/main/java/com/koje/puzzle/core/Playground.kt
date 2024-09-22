package com.koje.puzzle.core

import android.view.MotionEvent
import com.koje.framework.graphics.Position
import com.koje.framework.graphics.Surface
import com.koje.math.puzzle.R

object Playground : Surface() {

    val picmap = createImageDrawer(R.drawable.picmap)
    var board = Board()
    private var eyeLeft = Eye(0.37f)
    private var eyeRight = Eye(0.45f)

    init {
        addComponent(Background())
        addComponent(eyeLeft)
        addComponent(eyeRight)
        addComponent(board)

    }

    override fun onTouch(position: Position, event: MotionEvent) {
        eyeLeft.lookAt(position)
        eyeRight.lookAt(position)
        board.onTouch(position, event)
    }


}