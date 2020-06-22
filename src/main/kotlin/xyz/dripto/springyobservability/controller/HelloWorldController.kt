package xyz.dripto.springyobservability.controller

import io.micrometer.core.annotation.Timed
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import xyz.dripto.springyobservability.config.record
import xyz.dripto.springyobservability.service.HelloWorldService

@RestController
@RequestMapping("/hw")
class HelloWorldController(private val service: HelloWorldService) {
    /**
     * This will work fine on spring web. Timed annotation does not work on webflux somehow.
     * workaround given below
     */
    @GetMapping("/hello")
    @Timed("timed_hello_world")
    fun hello() = service.hello()

    /**
     * workaround for spring webflux
     */
    @GetMapping("/helloTimerSuspend")
    suspend fun helloTimerSuspend(@RequestParam name: String) = record(
            timerName = "helloTimerSuspend",
            timerTags = mapOf("val" to "halla")
    ) {
        service.helloTimerSuspend(name)
    }
}
