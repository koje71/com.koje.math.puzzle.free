package com.koje.math.puzzle

import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

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

    fun validate(tokens: MutableList<String>): Boolean {

        if (!compute(tokens, "*")) return false
        if (!compute(tokens, ":")) return false
        if (!compute(tokens, "+")) return false
        if (!compute(tokens, "-")) return false
        if (!compute(tokens, "=")) return false

        return true
    }


    fun compute(tokens: MutableList<String>, op: String): Boolean {
        for (i in 0..5) {
            val pos = tokens.indexOf(op)
            if (pos < 0) return true
            if (pos == 0 || pos == tokens.size - 1) return false
            if (!isNumber(tokens[pos - 1])) return false
            if (!isNumber(tokens[pos + 1])) return false

            val op1 = Integer.parseInt(tokens[pos - 1])
            val op2 = Integer.parseInt(tokens[pos + 1])

            if (op == "=" && op1 != op2) {
                return false
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

    fun isNumber(value: String): Boolean {
        return !listOf("+", "-", "*", ":", "=").contains(value)
    }

    @Test
    fun addition_isCorrect() {
        println("test")
        println(validate(split("12+11=23")))
        println(split(""))
    }
}