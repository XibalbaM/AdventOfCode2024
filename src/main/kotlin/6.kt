package fr.xibalba.adventOfCode.year2024

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import java.io.File

fun main() {
    val input = File("src/main/resources/6.txt").readLines().map { it.toList() }
    val startPosX = input.indexOfFirst { it.contains('^') }
    val startPosY = input[startPosX].indexOf('^')
    val startPos = startPosX to startPosY
    val map = input.map { it.map { it == '#' } }

    var pos = startPos
    var direction = Direction.UP
    val result1 = (listOf(pos) + generateSequence {
        val newPos = direction.move(pos)
        if (newPos.first !in 0..<map.size || newPos.second !in 0..<map.first().size) {
            null
        } else if (map[newPos.first][newPos.second]) {
            direction = direction.inc()
            -1 to -1
        } else {
            pos = newPos
            pos
        }
    }).filter { it != -1 to -1 }.toSet().size
    println(result1)

    runBlocking {
        (0..<map.size).windowed(10, 10).map {
            async(Dispatchers.Default) {
                println("${Thread.currentThread().name} $it")
                var workingNewObstacle = 0
                for (i in it) {
                    loop@
                    for (j in 0..<map.first().size) {
                        if (map[i][j] || (i to j) == startPos) {
                            continue
                        }
                        val newMap = map.map { it.toMutableList() }.apply { this[i][j] = true }
                        val reachedPositions = mutableListOf<Pair<Pair<Int, Int>, Direction>>(startPos to Direction.UP)
                        var currentPosition = startPos to Direction.UP
                        do {
                            reachedPositions += currentPosition
                            val newPos = currentPosition.second.move(currentPosition.first)
                            currentPosition = if (newPos.first !in 0..<newMap.size || newPos.second !in 0..<newMap.first().size) {
                                continue@loop
                            } else if (newMap[newPos.first][newPos.second]) {
                                currentPosition.first to currentPosition.second.inc()
                            } else {
                                newPos to currentPosition.second
                            }
                        } while (currentPosition !in reachedPositions)
                        workingNewObstacle += 1
                    }
                    println("${Thread.currentThread().name} $i")
                }
                println("${Thread.currentThread().name} $workingNewObstacle")
                workingNewObstacle
            }
        }.awaitAll().sum().also(::println)
    }
}

enum class Direction {
    UP, RIGHT, DOWN, LEFT;

    operator fun inc(): Direction {
        return entries[(ordinal + 1) % entries.size]
    }

    fun move(pos: Pair<Int, Int>): Pair<Int, Int> {
        return when(this) {
            UP -> pos.first - 1 to pos.second
            RIGHT -> pos.first to pos.second + 1
            DOWN -> pos.first + 1 to pos.second
            LEFT -> pos.first to pos.second - 1
        }
    }
}