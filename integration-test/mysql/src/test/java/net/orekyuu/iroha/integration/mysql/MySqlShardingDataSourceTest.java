package net.orekyuu.iroha.integration.mysql;

import net.orekyuu.iroha.integration.DatabaseController;
import net.orekyuu.iroha.integration.SimpleDataSource;
import net.orekyuu.iroha.integration.testcase.ShardingDataSourceTestBase;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.MySQLContainer;

import javax.sql.DataSource;
import java.sql.SQLException;

public class MySqlShardingDataSourceTest extends ShardingDataSourceTestBase {

    static MySQLContainer<?> shard1 = new MySQLContainer<>("mysql");
    static MySQLContainer<?> shard2 = new MySQLContainer<>("mysql");
    static MySQLContainer<?> shard3 = new MySQLContainer<>("mysql");

    private SimpleDataSource shard1DataSource = new SimpleDataSource(shard1.getUsername(), shard1.getPassword(), shard1.getJdbcUrl());
    private SimpleDataSource shard2DataSource = new SimpleDataSource(shard2.getUsername(), shard2.getPassword(), shard2.getJdbcUrl());
    private SimpleDataSource shard3DataSource = new SimpleDataSource(shard3.getUsername(), shard3.getPassword(), shard3.getJdbcUrl());

    @BeforeAll
    static void setUp() throws SQLException {
        shard1.start();
        shard2.start();
        shard3.start();
    }

    @AfterAll
    static void tearDown() throws SQLException {
        shard1.stop();
        shard2.stop();
        shard3.stop();
    }

    @Override
    public DataSource getShard1() {
        return shard1DataSource;
    }

    @Override
    public DataSource getShard2() {
        return shard2DataSource;
    }

    @Override
    public DataSource getShard3() {
        return shard3DataSource;
    }

    @Override
    public DatabaseController databaseController() {
        return new MySqlDatabaseController();
    }
}
