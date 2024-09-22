package com.koje.puzzle.core

import com.koje.framework.graphics.ComponentGroup
import com.koje.framework.graphics.Position
import com.koje.puzzle.view.PreferencesSection

class Field(val locX: Int, val locY: Int, board: Board) : ComponentGroup(Playground) {


    val position = Position(-0.40f + locX * 0.105f, -0.49f + locY * 0.165f)

    fun addMarker() {
        if (PreferencesSection.marker.get()) {
            addImageComponent {
                image = Playground.picmap
                index = 3
                count = 100

                addProcedure {
                    move(position)
                    scale(0.16f)
                }
            }
        }
    }

    init {
        plane = 1

        addImageComponent {
            image = Playground.picmap
            index = 0
            count = 100

            addProcedure {
                move(position)
                scale(0.18f)
            }
        }

    }

}