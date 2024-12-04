package fr.xibalba.adventOfCode.year2024

import java.io.File

fun main() {
    val input = File("src/main/resources/4.txt").readLines().map { it.toList() }
    val regex1 = """(?=XMAS|SAMX)""".toRegex()
    input.listAll().filter { it.size >= 4 }.sumOf { regex1.findAll(it.joinToString("")).count() }.also(::println)
    val regex2 = """MAS|SAM""".toRegex()
    input.listXShape().count { it.first.joinToString("").matches(regex2) && it.second.joinToString("").matches(regex2) }.also(::println)
}


fun <T> List<List<T>>.listAll(): List<List<T>> {
    val result = mutableListOf<List<T>>()
    result.addAll(this.listRows())
    result.addAll(this.listColumns())
    result.addAll(this.listDiagonals())
    return result
}

fun <T> List<List<T>>.listRows(): List<List<T>> {
    return this
}
fun <T> List<List<T>>.listColumns(): List<List<T>> {
    return this[0].indices.map { i -> this.map { it[i] } }
}
fun <T> List<List<T>>.listDiagonals(): List<List<T>> {
    val result = mutableListOf<List<T>>()
    for (i in 0..this.size + this[0].size - 2) {
        val diagonal = mutableListOf<T>()
        for (j in 0..i) {
            if (j < this.size && i - j < this[0].size) {
                diagonal.add(this[j][i - j])
            }
        }
        result.add(diagonal)
    }
    for (i in 1 - this[0].size until this.size) {
        val diagonal = mutableListOf<T>()
        for (j in 0 until this[0].size) {
            if (i + j >= 0 && i + j < this.size) {
                diagonal.add(this[i + j][j])
            }
        }
        result.add(diagonal)
    }
    return result
}

fun <T> List<List<T>>.listXShape(): List<Pair<List<T>, List<T>>> {
    val result = mutableListOf<Pair<List<T>, List<T>>>()
    for (i in 0 until this.size - 2) {
        for (j in 0 until this[0].size - 2) {
            result.add(listOf(this[i][j], this[i + 1][j + 1], this[i + 2][j + 2]) to listOf(this[i + 2][j], this[i + 1][j + 1], this[i][j + 2]))
        }
    }
    return result
}