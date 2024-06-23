package net.orekyuu.iroha.integration.testcase;

import net.orekyuu.iroha.datasource.IrohaDataSource;
import net.orekyuu.iroha.integration.DatabaseController;
import net.orekyuu.iroha.integration.User;
import net.orekyuu.iroha.selector.DataSourceSelector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class MasterSlaveDataSourceTestBase {

    public abstract DataSource getMasterDataSource();

    public abstract DataSource getSlaveDataSource();

    public abstract DatabaseController databaseController();

    public static class MasterSlaveDataSourceSelector implements DataSourceSelector {

        private final ThreadLocal<Boolean> isMaster = new ThreadLocal<>();
        private final DataSource master;
        private final DataSource slave;

        public MasterSlaveDataSourceSelector(DataSource master, DataSource slave) {
            this.master = master;
            this.slave = slave;
            isMaster.set(false);
        }

        public void useMaster() {
            isMaster.set(true);
        }

        public void useSlave() {
            isMaster.set(false);
        }

        @Override
        public List<DataSource> select() {
            return Collections.singletonList(isMaster.get() ? master : slave);
        }
    }

    public DataSource testTarget() {
        return new IrohaDataSource(selector);
    }

    public MasterSlaveDataSourceSelector dataSourceSelector() {
        return new MasterSlaveDataSourceSelector(getMasterDataSource(), getSlaveDataSource());
    }

    private DatabaseController db;
    private MasterSlaveDataSourceSelector selector;

    @BeforeEach
    void setup() {
        selector = dataSourceSelector();
        db = databaseController();
        db.createUserTableIfNotExists(getMasterDataSource());
        db.createUserTableIfNotExists(getSlaveDataSource());
        db.truncateUserTable(getMasterDataSource());
        db.truncateUserTable(getSlaveDataSource());
    }

    @Test
    void insertToMaster() {
        selector.useMaster();
        db.insert(testTarget(), new User("test", 20));
        assertThat(db.findAll(getMasterDataSource())).hasSize(1);
        assertThat(db.findAll(getSlaveDataSource())).hasSize(0);
    }
}
