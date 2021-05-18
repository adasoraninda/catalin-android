package creational.singleton

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class SingletonTest {

    @Test
    fun testSingleton1() {
        val instance1 = NetworkDriver.log()
        val instance2 = NetworkDriver.log()

        Assertions.assertThat(instance1).isSameAs(NetworkDriver)
        Assertions.assertThat(instance2).isSameAs(NetworkDriver)
        Assertions.assertThat(instance1).isSameAs(instance2)

    }

    @Test
    fun testSingleton2() {
        val instance1 = NetworkDriver2.instance
        val instance2 = NetworkDriver2.instance

        Assertions.assertThat(instance1).isSameAs(NetworkDriver2.instance)
        Assertions.assertThat(instance2).isSameAs(NetworkDriver2.instance)
        Assertions.assertThat(instance1).isSameAs(instance2)
    }

    @Test
    fun testSingleton3() {
        val instance1 = NetworkDriver3.getInstance()
        val instance2 = NetworkDriver3.getInstance()

        Assertions.assertThat(instance1).isSameAs(NetworkDriver3.getInstance())
        Assertions.assertThat(instance2).isSameAs(NetworkDriver3.getInstance())
        Assertions.assertThat(instance1).isSameAs(instance2)
    }

}