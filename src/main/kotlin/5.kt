package fr.xibalba.adventOfCode.year2024

import java.io.File

fun main() {
    val input = File("src/main/resources/5.txt").readText()
    val rules = input.substringBefore("\n\n").lines().map { Rule(it.substringBefore("|").toInt(), it.substringAfter("|").toInt()) }
    val updates = input.substringAfter("\n\n").lines().map {it.split(",").map {it.toInt()}}
    val result1 = updates.filter { isSorted(it, rules) }.sumOf { it[it.size/2]}
    println(result1)
    val result2 = updates.filterNot { isSorted(it, rules) }.map { sort(it, rules) }.sumOf { it[it.size/2] }
    println(result2)
}

data class Rule(val number1: Int, val number2: Int) {
    operator fun invoke(list: List<Int>): Boolean {
        return list.indexOf(number1) == -1 || list.indexOf(number2) == -1 || list.indexOf(number1) < list.indexOf(number2)
    }
}
fun isSorted(list: List<Int>, rules: List<Rule>): Boolean {
    return rules.all { it(list) }
}
fun sort(list: List<Int>, rules: List<Rule>): List<Int> {
    val rules = rules.filter { it.number1 in list && it.number2 in list }
    val result = list.toMutableList()
    while (!isSorted(result, rules)) {
        for (rule in rules) {
            if (!rule(result)) {
                val index1 = result.indexOf(rule.number1)
                val index2 = result.indexOf(rule.number2)
                result[index1] = rule.number2
                result[index2] = rule.number1
            }
        }
    }
    return result
}