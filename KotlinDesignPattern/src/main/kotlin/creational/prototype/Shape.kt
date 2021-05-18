package creational.prototype

abstract class Shape : Cloneable {
    var id: String? = null
    var type: String? = null

    abstract fun draw()

    public override fun clone(): Any {
        var clone: Any? = null
        try {
            clone = super.clone()
        } catch (e: CloneNotSupportedException) {
            e.printStackTrace()
        }
        return clone!!
    }

}

class Rectangle : Shape() {
    override fun draw() {
        println("Inside Rectangle::draw() method.")
    }

    init {
        id = "1"
        type = "Rectangle"
    }
}

class Square : Shape() {
    override fun draw() {
        println("Inside Square::draw() method.")
    }

    init {
        id = "2"
        type = "Square"
    }
}

class Circle : Shape() {
    override fun draw() {
        println("Inside Circle::draw() method.")
    }

    init {
        id = "3"
        type = "Circle"
    }
}

object ShapeCache {
    private val shapeMap = hashMapOf<String?, Shape>()

    fun loadCache() {
        val circle = Circle()
        val square = Square()
        val rectangle = Rectangle()

        shapeMap[circle.id] = circle
        shapeMap[square.id] = square
        shapeMap[rectangle.id] = rectangle
    }

    fun getShape(shapeId: String): Shape {
        val cacheShape = shapeMap[shapeId]

        return cacheShape?.clone() as Shape
    }

}