package net.orekyuu.iroha.integration.mysql;

import net.orekyuu.iroha.integration.DatabaseController;
import net.orekyuu.iroha.integration.SimpleDataSource;
import net.orekyuu.iroha.integration.testcase.MasterSlaveDataSourceTestBase;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.MySQLContainer;

import javax.sql.DataSource;
import java.sql.SQLException;

public class MasterSlaveDataSourceTest extends MasterSlaveDataSourceTestBase {
    static MySQLContainer<?> master = new MySQLContainer<>("mysql");
    static MySQLContainer<?> slave = new MySQLContainer<>("mysql");

    private SimpleDataSource masterDataSource = new SimpleDataSource(master.getUsername(), master.getPassword(), master.getJdbcUrl());
    private SimpleDataSource slaveDataSource = new SimpleDataSource(slave.getUsername(), slave.getPassword(), slave.getJdbcUrl());

    @BeforeAll
    static void setUp() throws SQLException {
        master.start();
        slave.start();
    }

    @AfterAll
    static void tearDown() throws SQLException {
        master.stop();
        slave.stop();
    }

    @Override
    public DataSource getMasterDataSource() {
        return masterDataSource;
    }

    @Override
    public DataSource getSlaveDataSource() {
        return slaveDataSource;
    }

    @Override
    public DatabaseController databaseController() {
        return new MySqlDatabaseController();
    }
}
