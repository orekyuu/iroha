package net.orekyuu.iroha.integration.mysql;

import javax.sql.DataSource;
import net.orekyuu.iroha.integration.SimpleDataSource;
import net.orekyuu.iroha.integration.doma.DomaIntegrationTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.seasar.doma.jdbc.dialect.MysqlDialect;
import org.testcontainers.containers.MySQLContainer;

public class MySqlDomaIntegrationTest extends DomaIntegrationTest {
  static MySQLContainer<?> shard1 = new MySQLContainer<>("mysql");
  static MySQLContainer<?> shard2 = new MySQLContainer<>("mysql");
  static MySQLContainer<?> shard3 = new MySQLContainer<>("mysql");

  @BeforeAll
  static void startContainers() {
    shard1.start();
    shard2.start();
    shard3.start();
  }

  @AfterAll
  static void stopContainers() {
    shard1.close();
    shard2.close();
    shard3.close();
  }

  @Override
  protected org.seasar.doma.jdbc.dialect.Dialect getDialect() {
    return new MysqlDialect();
  }

  @Override
  protected DataSource shard1() {
    return new SimpleDataSource(shard1.getUsername(), shard1.getPassword(), shard1.getJdbcUrl());
  }

  @Override
  protected DataSource shard2() {
    return new SimpleDataSource(shard2.getUsername(), shard2.getPassword(), shard2.getJdbcUrl());
  }

  @Override
  protected DataSource shard3() {
    return new SimpleDataSource(shard3.getUsername(), shard3.getPassword(), shard3.getJdbcUrl());
  }
}
