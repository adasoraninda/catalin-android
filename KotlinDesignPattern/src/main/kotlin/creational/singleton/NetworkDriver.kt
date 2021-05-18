package creational.singleton

object NetworkDriver {

    fun log() = apply { println("Network driver: $this") }

}

class NetworkDriver2 private constructor() {

    companion object {
        val instance by lazy { NetworkDriver2() }
    }

}

class NetworkDriver3 private constructor() {

    companion object {
        private var instance: NetworkDriver3? = null

        fun getInstance(): NetworkDriver3 {
            if (instance == null) {
                instance = NetworkDriver3()
            }
            return instance!!
        }
    }

}