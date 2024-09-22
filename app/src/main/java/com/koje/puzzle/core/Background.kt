package com.koje.puzzle.core

import com.koje.framework.graphics.ComponentGroup

class Background : ComponentGroup(Playground) {

    init {
        addImageComponent {
            image = Playground.picmap
            index = 3
            count = 4

            addProcedure {
                move(0f, 0f)
                scale(
                    when (Playground.ratio < 1.4f) {
                        true -> 1.4f
                        else -> Playground.ratio
                    }
                )
            }
        }
        addImageComponent {
            image = Playground.picmap
            index = 100
            count = 400

            addProcedure {
                move(-1.5f, 0f)
                scale(1.8f)
            }
        }
        addImageComponent {
            image = Playground.picmap
            index = 100
            count = 400

            addProcedure {
                move(1.5f, 0f)
                scale(1.8f)
            }
        }
    }
}