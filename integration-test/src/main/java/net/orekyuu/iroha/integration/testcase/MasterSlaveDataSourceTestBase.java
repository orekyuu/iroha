package net.orekyuu.iroha.integration.testcase;

import javax.sql.DataSource;

public abstract class MasterSlaveDataSourceTestBase implements TestScenario {

    abstract DataSource getMasterDataSource();

    abstract DataSource getSlaveDataSource();
}
