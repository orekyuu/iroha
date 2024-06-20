package net.orekyuu.iroha.integration.mysql;

import net.orekyuu.iroha.integration.DatabaseController;
import net.orekyuu.iroha.integration.SimpleDataSource;
import net.orekyuu.iroha.integration.testcase.SingleDataSourceTestBase;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.MySQLContainer;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class MySqlSingleDataSourceTest extends SingleDataSourceTestBase {
    static MySQLContainer<?> container = new MySQLContainer<>("mysql");

    @BeforeAll
    static void setUp() throws SQLException {
        container.start();
    }

    @Override
    protected DataSource getDataSource() {
        return new SimpleDataSource(container.getUsername(), container.getPassword(), container.getJdbcUrl());
    }

    @Override
    public DataSource testTarget() {
        return getDataSource();
    }

    @Override
    public DatabaseController databaseController() {
        return new MySqlDatabaseController();
    }
}
