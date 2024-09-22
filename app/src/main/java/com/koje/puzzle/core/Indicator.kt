package com.koje.puzzle.core

import com.koje.framework.graphics.ComponentGroup
import com.koje.framework.utils.Logger

class Indicator(val board: Board, val locY: Int) : ComponentGroup(Playground) {


    val posX = -0.477f
    val posY = -0.49f + locY * 0.165f
    var solved = false


    init {
        addImageComponent {
            image = Playground.picmap
            count = 100

            addProcedure {
                index = when (solved) {
                    true -> 2
                    else -> 1
                }
                move(posX, posY)
                scale(0.158f)
            }
        }
    }

    fun check() {
        val builder = StringBuilder()
        for (col in 0..8) {
            board.symbols.forEach {
                if (it.field.locX == col && it.field.locY == locY) {
                    builder.append(it.content)
                }
            }
        }

        val jokerPos = builder.indexOf('?')
        var result = false
        if (jokerPos >= 0) {
            listOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9').forEach {
                builder[jokerPos] = it
                if (validate(split(builder.toString()))) {
                    result = true
                }
            }
        } else {
            result = validate(split(builder.toString()))
        }
        solved = result
    }

    fun split(text: String): MutableList<String> {
        var result = mutableListOf<String>()

        val builder = StringBuilder()

        text.forEach {
            if (listOf('+', '-', '*', ':', '=').contains(it)) {
                if (builder.isNotEmpty()) {
                    result.add(builder.toString())
                    builder.clear()
                }
                result.add(it.toString())
            } else {
                builder.append(it)
            }
        }

        if (builder.isNotEmpty()) {
            result.add(builder.toString())
        }

        return result
    }

    private fun validate(tokens: MutableList<String>): Boolean {
        Logger.info(this, "validate: $tokens")

        if (!computePre(tokens)) return false
        if (!computeOps(tokens, "*")) return false
        if (!computeOps(tokens, ":")) return false
        if (!computeOps(tokens, "+")) return false
        if (!computeOps(tokens, "-")) return false
        if (!computeOps(tokens, "=")) return false

        return true
    }

    private fun computePre(tokens: MutableList<String>): Boolean {
        // vorzeichen behandeln
        return true
    }

    private fun computeOps(tokens: MutableList<String>, op: String): Boolean {
        if (op == "=" && tokens.size > 0 && tokens.indexOf(op) < 0) return false

        for (i in 0..5) {
            val pos = tokens.indexOf(op)
            if (pos < 0) return true
            if (pos == 0 || pos == tokens.size - 1) return false
            if (!isNumber(tokens[pos - 1])) return false
            if (!isNumber(tokens[pos + 1])) return false

            val op1 = Integer.parseInt(tokens[pos - 1])
            val op2 = Integer.parseInt(tokens[pos + 1])

            if (op == "=" && op1 != op2) return false
            if (op == ":") {
                if (op2 == 0) return false
                if (op1 % op2 != 0) return false
            }

            tokens[pos] = (when (op) {
                "+" -> op1 + op2
                "-" -> op1 - op2
                "*" -> op1 * op2
                ":" -> op1 / op2
                else -> op1
            }).toString()

            tokens.removeAt(pos + 1)
            tokens.removeAt(pos - 1)
        }

        return false
    }

    private fun isNumber(value: String): Boolean {
        return !listOf("+", "-", "*", ":", "=").contains(value)
    }


}