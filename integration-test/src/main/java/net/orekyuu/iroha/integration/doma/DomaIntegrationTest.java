package net.orekyuu.iroha.integration.doma;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import net.orekyuu.iroha.integration.Shard;
import net.orekyuu.iroha.integration.scenario.IntegrationTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.seasar.doma.jdbc.dialect.Dialect;

public abstract class DomaIntegrationTest extends IntegrationTestBase {

  protected abstract Dialect getDialect();

  UserDao userDao;

  @BeforeEach
  void setupDao() {
    userDao = new UserDaoImpl(new DomaConfig(dataSource, getDialect()));
  }

  @Test
  void insertToShard1() {
    selector.use(
        () -> {
          var userEntity = new UserEntity("user1", 20);
          var insertRows = userDao.insert(userEntity);
          assertThat(userEntity.id).isNotNull();
          assertThat(insertRows).isEqualTo(1);
          assertTable("users", Shard.SHARD_1).hasNumberOfRows(1);
          assertTable("users", Shard.SHARD_2).hasNumberOfRows(0);
          assertTable("users", Shard.SHARD_3).hasNumberOfRows(0);
        },
        Shard.SHARD_1);
  }

  @Test
  void insertToAll() {
    selector.use(
        () -> {
          var userEntity = new UserEntity("user1", 20);
          var insertRows = userDao.insert(userEntity);

          assertThat(userEntity.id).isNotNull();
          assertThat(insertRows).isEqualTo(3);
          assertTable("users", Shard.SHARD_1).hasNumberOfRows(1);
          assertTable("users", Shard.SHARD_2).hasNumberOfRows(1);
          assertTable("users", Shard.SHARD_3).hasNumberOfRows(1);
        },
        Shard.SHARD_1,
        Shard.SHARD_2,
        Shard.SHARD_3);
  }

  @Test
  void selectFromShard1() {
    selector.use(() -> userDao.insert(new UserEntity("user1", 20)), Shard.SHARD_1);
    selector.use(() -> userDao.insert(new UserEntity("user2", 22)), Shard.SHARD_2);
    selector.use(() -> userDao.insert(new UserEntity("user3", 23)), Shard.SHARD_3);

    selector.use(
        () -> {
          assertThat(userDao.selectAll())
              .hasSize(1)
              .first()
              .satisfies(
                  u -> {
                    assertThat(u.name).isEqualTo("user1");
                    assertThat(u.age).isEqualTo(20);
                  });
        },
        Shard.SHARD_1);
  }

  @Test
  void selectFromAllShards() {
    selector.use(() -> userDao.insert(new UserEntity("user1", 20)), Shard.SHARD_1);
    selector.use(() -> userDao.insert(new UserEntity("user2", 22)), Shard.SHARD_2);
    selector.use(() -> userDao.insert(new UserEntity("user3", 23)), Shard.SHARD_3);

    selector.use(
        () -> {
          assertThat(userDao.selectAll())
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
    selector.use(
        () -> {
          assertThat(userDao.selectAll()).isEmpty();
        },
        Shard.SHARD_1,
        Shard.SHARD_2,
        Shard.SHARD_3);
  }

  @Test
  void selectFromAllShards_existsEmptyResultSet() {
    selector.use(() -> userDao.insert(new UserEntity("user1", 20)), Shard.SHARD_1);
    selector.use(() -> userDao.insert(new UserEntity("user3", 23)), Shard.SHARD_3);

    selector.use(
        () -> {
          assertThat(userDao.selectAll())
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
    selector.use(
        () -> {
          var user1 = new UserEntity("user1", 20);
          var user2 = new UserEntity("user2", 22);
          var insertRows = userDao.batchInsert(List.of(user1, user2));
          assertThat(user1.id).isNotNull();
          assertThat(user2.id).isNotNull();
          assertThat(user1.id).isNotEqualTo(user2.id);
          assertThat(insertRows).hasSize(2);
          assertTable("users", Shard.SHARD_1).hasNumberOfRows(2);
          assertTable("users", Shard.SHARD_2).hasNumberOfRows(0);
          assertTable("users", Shard.SHARD_3).hasNumberOfRows(0);
        },
        Shard.SHARD_1);
  }

  @Test
  void batchInsert_allShard() {
    selector.use(
        () -> {
          var user1 = new UserEntity("user1", 20);
          var user2 = new UserEntity("user2", 22);
          var insertRows = userDao.batchInsert(List.of(user1, user2));
          assertThat(user1.id).isNotNull();
          assertThat(user2.id).isNotNull();
          assertThat(user1.id).isNotEqualTo(user2.id);
          assertThat(insertRows).hasSize(2);
          assertThat(insertRows[0]).isEqualTo(3);
          assertThat(insertRows[1]).isEqualTo(3);
          assertTable("users", Shard.SHARD_1).hasNumberOfRows(2);
          assertTable("users", Shard.SHARD_2).hasNumberOfRows(2);
          assertTable("users", Shard.SHARD_3).hasNumberOfRows(2);
        },
        Shard.SHARD_1,
        Shard.SHARD_2,
        Shard.SHARD_3);
  }
}
