package fr.xibalba.adventOfCode.year2024

import java.io.File
import kotlin.math.absoluteValue

fun main() {
    val input = File("src/main/resources/1.txt").readLines().map { it.substringBefore(" ").toInt() to it.substringAfterLast(" ").toInt() }
    val list1 = input.map { it.first }.sorted()
    val list2 = input.map { it.second }.sorted()
    //Part 1
    val result1 = list1.zip(list2).sumOf { (it.first - it.second).absoluteValue }
    println(result1)
    //Part 2
    val numberToCountInList2 = list2.groupBy {it}.mapValues { it.value.size }
    val result2 = list1.sumOf { it * (numberToCountInList2[it] ?: 0) }
    println(result2)
}