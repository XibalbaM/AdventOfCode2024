package fr.xibalba.adventOfCode.year2024

import java.io.File
import kotlin.collections.drop
import kotlin.collections.filter
import kotlin.collections.first

fun main() {
    val input = File("src/main/resources/7.txt").readLines().map { it.substringBefore(":").toLong() to it.substringAfter(":").trim().split(" ").map(String::toLong) }
    val operators = setOf({ a: Long, b: Long -> a + b }, { a: Long, b: Long -> a * b })
    println(compute(input, operators))
    val operators2 = operators + { a: Long, b: Long -> (a.toString() + b.toString()).toLong() }
    println(compute(input, operators2))
}

fun compute(input: List<Pair<Long, List<Long>>>, operators: Set<(Long, Long) -> Long>) = input
        .filter { (result, components) ->
            operators.listsOfSize(components.size - 1)
                .any { operators ->
                    components.drop(1)
                        .foldIndexed(components.first()) { index, accumulator, item -> operators[index](accumulator, item)} == result
                }
        }.sumOf { it.first }

fun <T> Set<T>.listsOfSize(size: Int): Set<List<T>> = when {
    size == 0 -> setOf(emptyList())
    isEmpty() -> emptySet()
    else -> flatMap { element -> this.listsOfSize(size - 1).map { it + element } }.toSet()
}