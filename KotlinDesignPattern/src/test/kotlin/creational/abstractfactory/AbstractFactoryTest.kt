package creational.abstractfactory

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class AbstractFactoryTest {

    @Test
    fun databaseDataSourceTest() {
        val datasourceFactory = DataSourceFactory.createFactory<DatabaseDataSource>()
        val dataSource = datasourceFactory.makeDataSource()

        Assertions.assertThat(dataSource).isInstanceOf(DatabaseDataSource::class.java)
    }

    @Test
    fun networkDataSourceTest(){
        val datasourceFactory = DataSourceFactory.createFactory<NetworkDataSource>()
        val dataSource = datasourceFactory.makeDataSource()

        Assertions.assertThat(dataSource).isInstanceOf(NetworkDataSource::class.java)
    }

}