package xyz.dripto.springyobservability.config

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Timer
import org.springframework.beans.factory.getBean
import java.util.UUID

inline fun <T> record(
        timerName: String = UUID.randomUUID().toString(),
        timerTags: Map<String, String> = mapOf(),
        timerDescription: String? = null,
        function: () -> T
): T {
    val registry = ContextWrapper.context.getBean<MeterRegistry>()
    val timer = Timer.builder("timer_$timerName").apply {
        timerDescription?.let { description(timerDescription) }
        timerTags.forEach { (t, u) -> tag(t, u) }
    }.register(registry)

    val sample: Timer.Sample = Timer.start(registry)
    val response = function()
    sample.stop(timer)

    return response
}
