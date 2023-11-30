package raspberry.ledservice.`in`

import com.pi4j.Pi4J
import com.pi4j.context.Context
import com.pi4j.io.gpio.digital.DigitalOutput
import com.pi4j.io.gpio.digital.DigitalOutputConfig
import com.pi4j.io.gpio.digital.DigitalState
import com.pi4j.ktx.io.digital.piGpioProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class Pi4jContextConfig {

    @Bean
    fun led(): Led {
        val pi4j = Pi4J.newAutoContext()
        val digitalOutputConfig = buildDigitalOutputConfig(pi4j, PIN.D26)
        val digitalOutput = pi4j.create(digitalOutputConfig)
        val led = Led(digitalOutput)
        return led
    }

    protected fun buildDigitalOutputConfig(pi4j: Context?, address: PIN): DigitalOutputConfig {
        return DigitalOutput.newConfigBuilder(pi4j)
            .id("BCM$address")
            .name("LED")
            .initial(DigitalState.LOW)
            .shutdown(DigitalState.LOW)
            .address(address.getPin())
            .piGpioProvider()
            .build()
    }



}

abstract class Component {

    /**
     * Utility function to sleep for the specified amount of milliseconds.
     * An [InterruptedException] will be caught and ignored while setting the interrupt flag again.
     *
     * @param milliseconds Time in milliseconds to sleep
     */
    fun delay(milliseconds: Long) {
        try {
            Thread.sleep(milliseconds)
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
        }
    }
}

class Led(private val digitalOutput: DigitalOutput) : Component() {
    /**
     * Pi4J digital output instance used by this component
     */
//        private val digitalOutput: DigitalOutput? = null

    /**
     * Creates a new simpleLed component with a custom BCM pin.
     *
     * @param pi4j    Pi4J context
     * @param address Custom BCM pin address
     */
//        fun SimpleLed(pi4j: Context, address: PIN) {
//            digitalOutput = pi4j.create(buildDigitalOutputConfig(pi4j, address))
//        }

    /**
     * Set the LED on or off depending on the boolean argument.
     *
     * @param on Sets the LED to on (true) or off (false)
     */
    fun setState(on: Boolean) {
        digitalOutput.setState(on)
    }

    /**
     * Sets the LED to on.
     */
    fun on() {
        digitalOutput.on()
    }

    /**
     * Sets the LED to off
     */
    fun off() {
        digitalOutput.off()
    }

    /**
     * Toggle the LED state depending on its current state.
     *
     * @return Return true or false according to the new state of the relay.
     */
    fun toggleState(): Boolean {
        digitalOutput.toggle()
        return digitalOutput.isOff
    }

    /**
     * Returns the instance of the digital output
     *
     * @return DigitalOutput instance of the LED
     */
    fun getDigitalOutput(): DigitalOutput? {
        return digitalOutput
    }

    /**
     * Configure Digital Output
     *
     * @param pi4j    PI4J Context
     * @param address GPIO Address of the relay
     * @return Return Digital Output configuration
     */
}

enum class PIN(private val pin: Int) {
    SDA1(2),
    SCL1(2),
    D4(4),
    TXD(14),
    RXD(15),
    D17(17),
    PWM18(18),
    D27(27),
    D22(22),
    D23(23),
    D24(24),
    MOSI(10),
    MISO(9),
    D25(25),
    D11(11),
    CEO(8),
    CE1(7),
    D5(5),
    D6(6),
    D16(16),
    D26(26),
    D20(20),
    D21(21),
    PWM12(12),
    PWM13(13),
    PWM19(19);

    fun getPin() = pin
}