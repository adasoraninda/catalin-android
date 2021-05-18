package creational.builder

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class BuilderTest {

    @Test
    fun builderPatternTest() {
        val alertDialog = AlertDialog.Builder()
            .setTitle("alert title")
            .setMessage("alert body")
            .setPositiveButton(true)
            .setNegativeButton(false)
            .build()

        Assertions.assertThat(alertDialog.title).isEqualTo("alert title")
        Assertions.assertThat(alertDialog.message).isEqualTo("alert body")
        Assertions.assertThat(alertDialog.positiveButton).isEqualTo(true)
        Assertions.assertThat(alertDialog.negativeButton).isEqualTo(false)

    }

}