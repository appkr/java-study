package dev.appkr.flowapi

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class FlowOperator {
    @Test
    fun map() {
        runBlocking {
            (1..3).asFlow()
                .map {
                    delay(500)
                    "processing synchronously $it"
                }
                .collect { println(it) }
        }
    }

    @Test
    fun filter() {
        runBlocking {
            (1..3).asFlow()
                .filter {
                    delay(500)
                    it >= 2
                }
                .collect { println(it) }
        }
    }

    @Test
    fun take() {
        runBlocking {
            (1..3).asFlow()
                .onEach { delay(500) }
                .take(2)
                .collect { println(it) }
        }
    }

    @Test
    fun takeWhile() {
        runBlocking {
            (1..3).asFlow()
                .onEach { delay(500) }
                .takeWhile { it < 3 }
                .collect { println(it) }
        }
    }

    @Test
    fun zip() {
        // 어느 하나의 Flow가 끝나면 다른 Flow도 같이 끝낸다
        // 즉, 엘리먼트의 갯수가 다르거나, 다른 시점에 데이터가 가용하면 먼저 끝난 Flow에 의존적이다
        val a = (1..3).asFlow()
        val b = flowOf("one", "two", "three")
        runBlocking {
            a.zip(b) { a, b -> "$a $b" }
                .collect { println(it) }
        }
    }

    @Test
    fun zip2() {
        val a = (1..3).asFlow().onEach { delay(300) }
        val b = flowOf("one", "two", "three").onEach { delay(400) }
        runBlocking {
            a.zip(b) { a, b -> "$a $b" }
                .collect { println(it) } // 1 one, 2 two, 3 three
        }
    }

    @Test
    fun combine() {
        // 값이 발행될 때마다 재계산한다
        val a = (1..3).asFlow().onEach { delay(300) }
        val b = flowOf("one", "two", "three").onEach { delay(400) }
        runBlocking {
            a.combine(b) { a, b -> "$a $b" }
                .collect { println(it) } // 1 one, 2 one, 2, two, 3 two, 3 three
        }
    }
}
