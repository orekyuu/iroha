package net.orekyuu.iroha.integration.doma;

import static org.assertj.db.api.Assertions.assertThat;

import java.util.List;
import net.orekyuu.iroha.AggregateDataSource;
import net.orekyuu.iroha.integration.Shard;
import net.orekyuu.iroha.integration.scenario.IntegrationTestBase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.seasar.doma.jdbc.dialect.Dialect;

public abstract class DomaIntegrationTest extends IntegrationTestBase {

  protected abstract Dialect getDialect();

  @Test
  void insertToShard1() {
    var dataSource = new AggregateDataSource(selector);
    UserDao dao = new UserDaoImpl(new DomaConfig(dataSource, getDialect()));

    selector.use(
        () -> {
          var userEntity = new UserEntity("user1", 20);
          var insertRows = dao.insert(userEntity);
          Assertions.assertThat(userEntity.id).isNotNull();
          Assertions.assertThat(insertRows).isEqualTo(1);
          assertThat(table("users", Shard.SHARD_1)).hasNumberOfRows(1);
          assertThat(table("users", Shard.SHARD_2)).hasNumberOfRows(0);
          assertThat(table("users", Shard.SHARD_3)).hasNumberOfRows(0);
        },
        Shard.SHARD_1);
  }

  @Test
  void insertToAll() {
    var dataSource = new AggregateDataSource(selector);
    UserDao dao = new UserDaoImpl(new DomaConfig(dataSource, getDialect()));

    selector.use(
        () -> {
          var userEntity = new UserEntity("user1", 20);
          var insertRows = dao.insert(userEntity);

          Assertions.assertThat(userEntity.id).isNotNull();
          Assertions.assertThat(insertRows).isEqualTo(3);
          assertThat(table("users", Shard.SHARD_1)).hasNumberOfRows(1);
          assertThat(table("users", Shard.SHARD_2)).hasNumberOfRows(1);
          assertThat(table("users", Shard.SHARD_3)).hasNumberOfRows(1);
        },
        Shard.SHARD_1,
        Shard.SHARD_2,
        Shard.SHARD_3);
  }

  @Test
  void selectFromShard1() {
    var dataSource = new AggregateDataSource(selector);
    UserDao dao = new UserDaoImpl(new DomaConfig(dataSource, getDialect()));

    selector.use(() -> dao.insert(new UserEntity("user1", 20)), Shard.SHARD_1);
    selector.use(() -> dao.insert(new UserEntity("user2", 22)), Shard.SHARD_2);
    selector.use(() -> dao.insert(new UserEntity("user3", 23)), Shard.SHARD_3);

    selector.use(
        () -> {
          Assertions.assertThat(dao.selectAll())
              .hasSize(1)
              .first()
              .satisfies(
                  u -> {
                    Assertions.assertThat(u.name).isEqualTo("user1");
                    Assertions.assertThat(u.age).isEqualTo(20);
                  });
        },
        Shard.SHARD_1);
  }

  @Test
  void selectFromAllShards() {
    var dataSource = new AggregateDataSource(selector);
    UserDao dao = new UserDaoImpl(new DomaConfig(dataSource, getDialect()));

    selector.use(() -> dao.insert(new UserEntity("user1", 20)), Shard.SHARD_1);
    selector.use(() -> dao.insert(new UserEntity("user2", 22)), Shard.SHARD_2);
    selector.use(() -> dao.insert(new UserEntity("user3", 23)), Shard.SHARD_3);

    selector.use(
        () -> {
          Assertions.assertThat(dao.selectAll())
              .hasSize(3)
              .anyMatch(user -> user.name.equals("user1") && user.age == 20)
              .anyMatch(user -> user.name.equals("user2") && user.age == 22)
              .anyMatch(user -> user.name.equals("user3") && user.age == 23);
        },
        Shard.SHARD_1,
        Shard.SHARD_2,
        Shard.SHARD_3);
  }

  @Test
  void selectFromAllShards_emptyResult() {
    var dataSource = new AggregateDataSource(selector);
    UserDao dao = new UserDaoImpl(new DomaConfig(dataSource, getDialect()));

    selector.use(
        () -> {
          Assertions.assertThat(dao.selectAll()).isEmpty();
        },
        Shard.SHARD_1,
        Shard.SHARD_2,
        Shard.SHARD_3);
  }

  @Test
  void selectFromAllShards_existsEmptyResultSet() {
    var dataSource = new AggregateDataSource(selector);
    UserDao dao = new UserDaoImpl(new DomaConfig(dataSource, getDialect()));

    selector.use(() -> dao.insert(new UserEntity("user1", 20)), Shard.SHARD_1);
    selector.use(() -> dao.insert(new UserEntity("user3", 23)), Shard.SHARD_3);

    selector.use(
        () -> {
          Assertions.assertThat(dao.selectAll())
              .hasSize(2)
              .anyMatch(user -> user.name.equals("user1") && user.age == 20)
              .anyMatch(user -> user.name.equals("user3") && user.age == 23);
        },
        Shard.SHARD_1,
        Shard.SHARD_2,
        Shard.SHARD_3);
  }

  @Test
  void batchInsert() {
    var dataSource = new AggregateDataSource(selector);
    UserDao dao = new UserDaoImpl(new DomaConfig(dataSource, getDialect()));

    selector.use(
        () -> {
          var user1 = new UserEntity("user1", 20);
          var user2 = new UserEntity("user2", 22);
          var insertRows = dao.batchInsert(List.of(user1, user2));
          Assertions.assertThat(user1.id).isNotNull();
          Assertions.assertThat(user2.id).isNotNull();
          Assertions.assertThat(user1.id).isNotEqualTo(user2.id);
          Assertions.assertThat(insertRows).hasSize(2);
          assertThat(table("users", Shard.SHARD_1)).hasNumberOfRows(2);
        },
        Shard.SHARD_1);
  }

  @Test
  void batchInsert_allShard() {
    var dataSource = new AggregateDataSource(selector);
    UserDao dao = new UserDaoImpl(new DomaConfig(dataSource, getDialect()));

    selector.use(
        () -> {
          var user1 = new UserEntity("user1", 20);
          var user2 = new UserEntity("user2", 22);
          var insertRows = dao.batchInsert(List.of(user1, user2));
          Assertions.assertThat(user1.id).isNotNull();
          Assertions.assertThat(user2.id).isNotNull();
          Assertions.assertThat(user1.id).isNotEqualTo(user2.id);
          Assertions.assertThat(insertRows).hasSize(2);
          Assertions.assertThat(insertRows[0]).isEqualTo(3);
          Assertions.assertThat(insertRows[1]).isEqualTo(3);
          assertThat(table("users", Shard.SHARD_1)).hasNumberOfRows(2);
          assertThat(table("users", Shard.SHARD_2)).hasNumberOfRows(2);
          assertThat(table("users", Shard.SHARD_3)).hasNumberOfRows(2);
        },
        Shard.SHARD_1,
        Shard.SHARD_2,
        Shard.SHARD_3);
  }
}
