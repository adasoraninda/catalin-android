package creational.factorymethod

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class FactoryMethodTest {

    @Test
    fun currencyTest() {
        val spainCurrency = CurrencyFactory.currencyForCountry(Country.Spain).code
        val usaCurrency = CurrencyFactory.currencyForCountry(Country.USA).code

        val expectedCurrencySpain = "EUR"
        val expectedCurrencyUsa = "USD"

        Assertions.assertThat(spainCurrency).isEqualTo(expectedCurrencySpain)
        Assertions.assertThat(usaCurrency).isEqualTo(expectedCurrencyUsa)

    }

}