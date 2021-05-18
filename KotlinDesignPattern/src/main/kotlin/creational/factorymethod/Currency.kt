package creational.factorymethod

sealed class Country {
    object USA : Country()
    object Canada : Country()
    object Spain : Country()
}

class Currency(val code: String)

object CurrencyFactory {

    fun currencyForCountry(country: Country): Currency {
        return when (country) {
            Country.Canada -> Currency(code = "CAD")
            Country.Spain -> Currency(code = "EUR")
            Country.USA -> Currency(code = "USD")
        }
    }

}