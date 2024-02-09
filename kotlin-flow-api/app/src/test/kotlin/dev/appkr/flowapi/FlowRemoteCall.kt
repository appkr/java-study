package dev.appkr.flowapi

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class FlowRemoteCall {
    @Test
    fun mockRemoteApiCall() {
        val flow = flow {
            // java.lang.IllegalStateException: Flow invariant is violated
            // -> Use flowOn()
            // withContext(Dispatchers.IO) { ... }
            delay(500)
            emit(1)
            delay(500)
            emit(2)
            delay(500)
            emit(3)
        }
            .flowOn(Dispatchers.IO)

        runBlocking {
            flow.collect {
                println(it)
            }
        }
    }
}
