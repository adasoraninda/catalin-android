package creational.abstractfactory

interface DataSource

class NetworkDataSource : DataSource

class DatabaseDataSource : DataSource

abstract class DataSourceFactory {
    abstract fun makeDataSource(): DataSource

    companion object {
        inline fun <reified T : DataSource> createFactory(): DataSourceFactory {
            return when (T::class) {
                NetworkDataSource::class -> NetworkFactory()
                DatabaseDataSource::class -> DatabaseFactory()
                else -> throw IllegalArgumentException()
            }
        }
    }
}

class NetworkFactory : DataSourceFactory() {

    override fun makeDataSource(): DataSource {
        return NetworkDataSource()
    }
}

class DatabaseFactory : DataSourceFactory() {

    override fun makeDataSource(): DataSource {
        return DatabaseDataSource()
    }
}