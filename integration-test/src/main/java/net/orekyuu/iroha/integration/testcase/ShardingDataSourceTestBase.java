package net.orekyuu.iroha.integration.testcase;

import javax.sql.DataSource;

public abstract class ShardingDataSourceTestBase implements TestScenario {

    abstract DataSource getMasterShard1();
    abstract DataSource getMasterShard2();

    abstract DataSource getSlaveShard1();
    abstract DataSource getSlaveShard2();
    abstract DataSource getSlaveShard3();
}
