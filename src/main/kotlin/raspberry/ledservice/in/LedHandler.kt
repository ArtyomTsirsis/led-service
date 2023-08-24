package raspberry.ledservice.`in`

import com.pi4j.Pi4J
import com.pi4j.io.gpio.digital.DigitalOutput
import com.pi4j.io.gpio.digital.DigitalState
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/led")
class LedHandler {

    @PostMapping("/switch")
    fun switchLed(@RequestBody switch: Switch): String {
        val pi4j = Pi4J.newAutoContext()
        val ledConfig = DigitalOutput.newConfigBuilder(pi4j)
            .name("LED Flasher")
            .address(2)
            .shutdown(DigitalState.LOW)
            .initial(DigitalState.LOW)
            .provider("pigpio-digital-output")
        val led = pi4j.create(ledConfig)
        return if (led.equals(DigitalState.HIGH)) {
            led.low()
            "turned off"
        } else {
            led.high()
            "turned on"
        }
    }

    data class Switch(var switch: Int)

}