package dev.appkr.flowapi

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.takeWhile
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
}
