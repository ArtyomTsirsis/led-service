package raspberry.ledservice.`in`

import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/led")
class LedHandler(private val led: Led) {

    @PostMapping("/switch")
    fun switchLed(@RequestBody switch: Switch): String {
        val state = led.getDigitalOutput()?.state()?.value
        return if (switch.switch) {
            led.on()
            "was ${state}, now turned on"
        } else {
            led.off()
            "was ${state}, turned off"
        }
    }

    @GetMapping("/switch")
    fun ledStatus(): Int {
        return led.getDigitalOutput()?.state()?.value!!.toInt()
    }

    data class Switch(var switch: Boolean)

}