package creational.lazy

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class LazyTest {

    @Test
    fun windowTest() {
        val window = Window()
        window.showMessage("Hello window")

        Assertions.assertThat(window.box).isNotNull
    }

}
