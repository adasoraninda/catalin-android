package creational.prototype

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class PrototypeTest {

    @Test
    fun testPrototype(){
        ShapeCache.loadCache()

        val cloneShape1 = ShapeCache.getShape("1")
        val cloneShape2 = ShapeCache.getShape("2")
        val cloneShape3 = ShapeCache.getShape("3")

        Assertions.assertThat(cloneShape1.type).isEqualTo("Rectangle")
        Assertions.assertThat(cloneShape2.type).isEqualTo("Square")
        Assertions.assertThat(cloneShape3.type).isEqualTo("Circle")

    }

}