package dev.appkr.flowapi

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class FlowTest {
    @Test
    fun run() {
        val flow = flow {
            (0..10).forEach {
                kotlinx.coroutines.delay(500)
                emit(it)
            }
        }

        runBlocking {
            flow.collect {
                println(it)
            }
        }
    }
}
