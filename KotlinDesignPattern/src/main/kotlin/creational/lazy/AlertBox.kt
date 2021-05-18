package creational.lazy

class AlertBox {
    var message: String? = null

    fun show() {
        println("Alert Box $this: $message")
    }

}

class Window {
    val box by lazy { AlertBox() }

    fun showMessage(message: String) {
        box.message = message
        box.show()
    }
}
