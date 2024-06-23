package net.orekyuu.iroha.integration.testcase;

import net.orekyuu.iroha.datasource.IrohaDataSource;
import net.orekyuu.iroha.integration.DatabaseController;
import net.orekyuu.iroha.integration.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class SingleDataSourceTestBase implements TestScenario {

    protected abstract DataSource getDataSource();

    @Override
    public DataSource testTarget() {
        return new IrohaDataSource(() -> Collections.singletonList(getDataSource()));
    }

    private DatabaseController databaseController;

    @BeforeEach
    void setup() {
        databaseController = databaseController();
        databaseController.createUserTableIfNotExists(getDataSource());
        databaseController.truncateUserTable(getDataSource());
    }

    @Test
    void batchInsertQuery() {
        List<User> users = IntStream.range(0, 20)
                .mapToObj(i -> new User("user " + i, 10 + i))
                .collect(Collectors.toList());
        DatabaseController db = databaseController();
        List<User> inserted = db.batchInsert(testTarget(), users);

        assertThat(db.findByIds(testTarget(), inserted.stream().map(u -> u.id).collect(Collectors.toList())))
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
