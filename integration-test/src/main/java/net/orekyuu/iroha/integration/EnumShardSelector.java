package net.orekyuu.iroha.integration;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import net.orekyuu.iroha.DataSourceSelector;

public class EnumShardSelector implements DataSourceSelector {
  private final EnumMap<Shard, DataSource> shardMap;

  private List<DataSource> selected;

  public EnumShardSelector(EnumMap<Shard, DataSource> shardMap) {
    this.shardMap = shardMap;
  }

  public void use(Runnable block, Shard... shards) {
    selected = Arrays.stream(shards).map(shardMap::get).collect(Collectors.toList());
    try {
      block.run();
    } finally {
      selected = null;
    }
  }

  @Override
  public List<DataSource> select() {
    if (selected == null) {
      throw new IllegalStateException("No shards selected");
    }
    return selected;
  }
}
