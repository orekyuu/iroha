package net.orekyuu.iroha.integration.mysql;

import net.orekyuu.iroha.datasource.IrohaDataSource;
import net.orekyuu.iroha.integration.DatabaseController;
import net.orekyuu.iroha.integration.SimpleDataSource;
import net.orekyuu.iroha.integration.testcase.SingleDataSourceTestBase;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.MySQLContainer;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collections;

public class MySqlSingleDataSourceTest extends SingleDataSourceTestBase {
    static MySQLContainer<?> container = new MySQLContainer<>("mysql");
    private SimpleDataSource simpleDataSource = new SimpleDataSource(container.getUsername(), container.getPassword(), container.getJdbcUrl());

    @BeforeAll
    static void setUp() throws SQLException {
        container.start();
    }


    @Override
    protected DataSource getDataSource() {
        return simpleDataSource;
    }

    @Override
    public DataSource testTarget() {
        return new IrohaDataSource(() -> Collections.singletonList(simpleDataSource));
    }

    @Override
    public DatabaseController databaseController() {
        return new MySqlDatabaseController();
    }
}
