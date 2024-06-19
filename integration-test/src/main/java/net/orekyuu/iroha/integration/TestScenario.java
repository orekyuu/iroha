package net.orekyuu.iroha.integration;

import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public interface TestScenario extends DatabaseController {

    DataSource testTarget();

    @Test
    default void selectQuery() {
        User result = insert(testTarget(), new User("test", 20));
        assertThat(findByIds(testTarget(), Arrays.asList(result.id)))
                .hasSize(1)
                .first().isEqualTo(result);

        assertThat(findAll(testTarget()))
                .hasSize(1)
                .first().isEqualTo(result);
    }

    @Test
    default void insertQuery() {
        User result = insert(testTarget(), new User("test", 20));
        assertThat(result).isNotNull();
    }

    @Test
    default void deleteQuery() {
        User result = insert(testTarget(), new User("test", 20));
        delete(testTarget(), result);
    }

    @Test
    default void updateQuery() {
        User result = insert(testTarget(), new User("test", 20));
        result.name = "updated";
        update(testTarget(), result);

        User updatedUser = findByIds(testTarget(), Arrays.asList(result.id)).get(0);
        assertThat(updatedUser.name).isEqualTo("updated");
    }

    @Test
    default void batchInsertQuery() {
        List<User> users = IntStream.range(0, 20)
                .mapToObj(i -> new User("user " + i, 10 + i))
                .collect(Collectors.toList());
        List<User> inserted = batchInsert(testTarget(), users);

        assertThat(findByIds(testTarget(), inserted.stream().map(u -> u.id).collect(Collectors.toList())))
                .containsExactly(inserted.toArray(new User[0]));
    }

    @Test
    default void batchDeleteQuery() {
        List<User> users = IntStream.range(0, 20)
                .mapToObj(i -> new User("user " + i, 10 + i))
                .collect(Collectors.toList());
        List<User> inserted = batchInsert(testTarget(), users);

        batchDelete(testTarget(), inserted);

        assertThat(findByIds(testTarget(), inserted.stream().map(u -> u.id).collect(Collectors.toList())))
                .isEmpty();
    }

    @Test
    default void batchUpdateQuery() {
        List<User> users = IntStream.range(0, 20)
                .mapToObj(i -> new User("user " + i, 10 + i))
                .collect(Collectors.toList());
        List<User> inserted = batchInsert(testTarget(), users);

        List<User> updated = inserted.stream()
                .map(u -> new User(u.id, "updated" + u.name, u.age + 10))
                .collect(Collectors.toList());

        batchUpdate(testTarget(), updated);

        assertThat(findByIds(testTarget(), inserted.stream().map(u -> u.id).collect(Collectors.toList())))
                .containsExactly(updated.toArray(new User[0]));
    }
}
