package xyz.dripto.springyobservability.service

import kotlinx.coroutines.delay
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class HelloWorldService {
    fun hello(): String {
        Thread.sleep(Random.nextLong(5000))
        return "world"
    }

    suspend fun helloTimerSuspend(name: String): String {
        delay(Random.nextLong(3000))
        return name
    }
}
