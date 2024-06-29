# iroha
iroha is a library for handling multiple databases.  
You can wrap the selection logic of DataSource in `javax.sql.DataSource`, allowing transparent routing of queries across multiple databases.  

## Intended Use Cases
- Read/Write Splitting
- Sharding
- Database Migration
  - Used for strategies like zero-downtime migration where writes can be directed to both old and new databases simultaneously.

## example
```java
public class ShardingDataSourceSelector implements DataSourceSelector {
    private ThreadLocal<Long> userIdHolder = new ThreadLocal<>();
    private DataSource[] shards;

    public ShardingDataSourceSelector(DataSource[] shards) {
        this.shards = shards;
    }

    public void use(long userId, Runnable block) {
        userIdHolder.set(userId);
        try {
            block.run();
        } finally {
            userIdHolder.set(null);
        }
    }

    public List<DataSource> select() {
        DataSource selected = shards[userIdHolder.get() % shards.length];
        return List.of(selected);
    }
}

ShardingDataSourceSelector selector = ShardingDataSourceSelector(new DataSource[] {
        dataSource1,
        dataSource2,
});
AggregateDataSource dataSource = new AggregateDataSource(selector);

selector.use(10L, () -> {
    // Execute queries using AggregateDataSource here.
    // dataSource.getConnection(); ...
});
```