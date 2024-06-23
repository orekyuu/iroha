package net.orekyuu.iroha.integration.testcase;

import net.orekyuu.iroha.datasource.IrohaDataSource;
import net.orekyuu.iroha.integration.DatabaseController;
import net.orekyuu.iroha.integration.User;
import net.orekyuu.iroha.selector.DataSourceSelector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class ShardingDataSourceTestBase {

    public abstract DataSource getShard1();
    public abstract DataSource getShard2();
    public abstract DataSource getShard3();

    public abstract DatabaseController databaseController();

    public DataSource testTarget() {
        return new IrohaDataSource(selector);
    }

    public EnumDataSourceSelector dataSourceSelector() {
        return new EnumDataSourceSelector(dataSourceMap);
    }

    private DatabaseController db;
    private EnumDataSourceSelector selector;
    private Map<Target, DataSource> dataSourceMap;

    public enum Target {
        SHARD1, SHARD2, SHARD3
    }
    public static class EnumDataSourceSelector implements DataSourceSelector {

        private final ThreadLocal<List<Target>> targets = new ThreadLocal<>();
        private final Map<Target, DataSource> dataSourceMap;

        public EnumDataSourceSelector(Map<Target, DataSource> dataSourceMap) {
            this.dataSourceMap = dataSourceMap;
        }

        public void use(Target... targets) {
            this.targets.set(Arrays.asList(targets));
        }

        @Override
        public List<DataSource> select() {
            return targets.get().stream()
                    .map(t -> dataSourceMap.get(t))
                    .collect(Collectors.toList());
        }
    }

    @BeforeEach
    void setup() {
        dataSourceMap = new HashMap<>();
        dataSourceMap.put(Target.SHARD1, getShard1());
        dataSourceMap.put(Target.SHARD2, getShard2());
        dataSourceMap.put(Target.SHARD3, getShard3());
        selector = dataSourceSelector();
        db = databaseController();
        for (DataSource value : dataSourceMap.values()) {
            db.createUserTableIfNotExists(value);
            db.truncateUserTable(value);
        }
    }
    @Test
    void selectFromAllSlave() {
        db.insert(getShard1(), new User(1L, "test1", 20));
        db.insert(getShard1(), new User(2L, "test1_2", 20));
        db.insert(getShard2(), new User(3L, "test2", 21));
        db.insert(getShard2(), new User(4L, "test2_2", 21));
        db.insert(getShard3(), new User(5L, "test3", 22));
        db.insert(getShard3(), new User(6L, "test3_1", 22));

        selector.use(Target.SHARD1, Target.SHARD2, Target.SHARD3);
        assertThat(db.findAll(testTarget())).hasSize(6);
    }

    @Test
    void selectFromAllSlave_whenEmptyResult() {
        selector.use(Target.SHARD1, Target.SHARD2, Target.SHARD3);
        assertThat(db.findAll(testTarget())).hasSize(0);
    }

    @Test
    void selectFromAllSlave_when1ShardResult() {
        db.insert(getShard2(), new User(3L, "test2", 21));

        selector.use(Target.SHARD1, Target.SHARD2, Target.SHARD3);
        assertThat(db.findAll(testTarget())).hasSize(1);
    }
}
