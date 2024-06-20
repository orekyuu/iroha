package net.orekyuu.iroha.integration.mysql;

import net.orekyuu.iroha.integration.DatabaseController;
import net.orekyuu.iroha.integration.SimpleDataSource;
import net.orekyuu.iroha.integration.SingleDataSourceTestBase;
import net.orekyuu.iroha.integration.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class MySqlSingleDataSourceTest extends SingleDataSourceTestBase {
    static MySQLContainer<?> container = new MySQLContainer<>("mysql");

    @BeforeAll
    static void setUp() throws SQLException {
        container.start();
    }

    @Override
    protected DataSource getDataSource() {
        return new SimpleDataSource(container.getUsername(), container.getPassword(), container.getJdbcUrl());
    }

    @Override
    public DataSource testTarget() {
        return getDataSource();
    }

    @Override
    public DatabaseController databaseController() {
        return new MySqlDatabaseController();
    }

    @Test
    void batchInsertQuery() {
        List<User> users = IntStream.range(0, 20)
                .mapToObj(i -> new User("user " + i, 10 + i))
                .collect(Collectors.toList());
        List<User> inserted = databaseController().batchInsert(testTarget(), users);

        assertThat(databaseController().findByIds(testTarget(), inserted.stream().map(u -> u.id).collect(Collectors.toList())))
                .containsExactly(inserted.toArray(new User[0]));
    }

    @Test
    void batchDeleteQuery() {
        List<User> users = IntStream.range(0, 20)
                .mapToObj(i -> new User("user " + i, 10 + i))
                .collect(Collectors.toList());
        List<User> inserted = databaseController().batchInsert(testTarget(), users);

        databaseController().batchDelete(testTarget(), inserted);

        assertThat(databaseController().findByIds(testTarget(), inserted.stream().map(u -> u.id).collect(Collectors.toList())))
                .isEmpty();
    }

    @Test
    void batchUpdateQuery() {
        List<User> users = IntStream.range(0, 20)
                .mapToObj(i -> new User("user " + i, 10 + i))
                .collect(Collectors.toList());
        List<User> inserted = databaseController().batchInsert(testTarget(), users);

        List<User> updated = inserted.stream()
                .map(u -> new User(u.id, "updated" + u.name, u.age + 10))
                .collect(Collectors.toList());

        databaseController().batchUpdate(testTarget(), updated);

        assertThat(databaseController().findByIds(testTarget(), inserted.stream().map(u -> u.id).collect(Collectors.toList())))
                .containsExactly(updated.toArray(new User[0]));
    }
}
