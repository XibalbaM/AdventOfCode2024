package fr.xibalba.adventOfCode.year2024

import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.sign

fun main() {
    val input = File("src/main/resources/2.txt").readLines().map { it.split(" ").map { it.toInt() } }
    val result1 = input.count { checkReport(it) }
    println(result1)
    val result2 = input.count { checkReportAndAllowElementRemoval(it) }
    println(result2)
}

fun checkReport(report: List<Int>): Boolean {
    val spaces = report.windowed(2).map { it[0] - it[1] }
    return spaces.all { it.absoluteValue in 1..3 && it.sign == spaces.first().sign }
}

fun checkReportAndAllowElementRemoval(report: List<Int>): Boolean {
    if (checkReport(report))
        return true
    val curatedReports = report.subListsWithOneElementRemoved()
    return curatedReports.any { checkReport(it) }
}

fun List<Int>.subListsWithOneElementRemoved(): List<List<Int>> {
    val result = mutableListOf<List<Int>>()
    for (i in 0 until this.size) {
        val subList = this.toMutableList()
        subList.removeAt(i)
        result.add(subList)
    }
    return result
}