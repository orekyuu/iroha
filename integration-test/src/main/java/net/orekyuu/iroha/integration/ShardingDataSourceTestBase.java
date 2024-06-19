package net.orekyuu.iroha.integration;

import javax.sql.DataSource;

public abstract class ShardingDataSourceTestBase implements TestScenario {

    abstract DatabaseController databaseController();

    abstract DataSource getMasterShard1();
    abstract DataSource getMasterShard2();

    abstract DataSource getSlaveShard1();
    abstract DataSource getSlaveShard2();
    abstract DataSource getSlaveShard3();
}
