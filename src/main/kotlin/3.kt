package fr.xibalba.adventOfCode.year2024

import java.io.File

fun main() {
    val input = File("src/main/resources/3.txt").readText()
    val result1 = Regex("""mul\((\d{1,3}),(\d{1,3})\)""").findAll(input).map {it.groups[1]!!.value.toInt() * it.groups[2]!!.value.toInt()}.sum()
    println(result1)
    val regex = """mul\((\d{1,3}),(\d{1,3})\)|do\(\)|don't\(\)""".toRegex()
    var isExecuting = true
    val result2 = regex.findAll(input).map {
        when(it.value) {
            "do()" -> {
                isExecuting = true
                0
            }
            "don't()" -> {
                isExecuting = false
                0
            }
            else -> if (isExecuting) it.groups[1]!!.value.toInt() * it.groups[2]!!.value.toInt() else 0
        }
    }.sum()
    println(result2)
}