package net.orekyuu.iroha.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import javax.sql.DataSource;

public abstract class SingleDataSourceTestBase implements TestScenario {

    protected abstract DataSource getDataSource();

    private DatabaseController databaseController;

    @BeforeEach
    void setup() {
        databaseController = databaseController();
        databaseController.createUserTableIfNotExists(getDataSource());
    }

    @AfterEach
    void tearDown() {
        databaseController.truncateUserTable(getDataSource());
    }


}
