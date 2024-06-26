package net.orekyuu.iroha.integration.scenario;

import static org.assertj.db.api.Assertions.assertThat;

import java.util.EnumMap;
import javax.sql.DataSource;
import net.orekyuu.iroha.AggregateDataSource;
import net.orekyuu.iroha.integration.EnumShardSelector;
import net.orekyuu.iroha.integration.Shard;
import org.assertj.db.api.TableAssert;
import org.assertj.db.type.Table;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class IntegrationTestBase {

  protected abstract DataSource shard1();

  protected abstract DataSource shard2();

  protected abstract DataSource shard3();

  protected EnumShardSelector selector;
  private EnumMap<Shard, DataSource> shardMap;
  protected AggregateDataSource dataSource;

  protected final TableAssert assertTable(String name, Shard shard) {
    return assertThat(new Table(shardMap.get(shard), name));
  }

  @BeforeEach
  void setupDatabase() {
    var script = new DatabaseScript();
    script.setupDatabase(shard1());
    script.setupDatabase(shard2());
    script.setupDatabase(shard3());

    shardMap = new EnumMap<>(Shard.class);
    shardMap.put(Shard.SHARD_1, shard1());
    shardMap.put(Shard.SHARD_2, shard2());
    shardMap.put(Shard.SHARD_3, shard3());
    selector = new EnumShardSelector(shardMap);

    dataSource = new AggregateDataSource(selector);
  }

  @AfterEach
  void truncateDatabase() {
    var script = new DatabaseScript();
    script.truncateAllTables(shard1());
    script.truncateAllTables(shard2());
    script.truncateAllTables(shard3());
  }
}
