package net.orekyuu.iroha.integration;

import javax.sql.DataSource;

public abstract class MasterSlaveDataSourceTestBase implements TestScenario {

    abstract DatabaseController databaseController();

    abstract DataSource getMasterDataSource();

    abstract DataSource getSlaveDataSource();
}
