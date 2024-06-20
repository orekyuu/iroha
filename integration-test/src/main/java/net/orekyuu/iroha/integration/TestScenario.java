package net.orekyuu.iroha.integration;

import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public interface TestScenario {

    DataSource testTarget();

    DatabaseController databaseController();

    @Test
    default void selectQuery() {
        DatabaseController db = databaseController();
        User result = db.insert(testTarget(), new User("test", 20));
        assertThat(db.findByIds(testTarget(), Arrays.asList(result.id)))
                .hasSize(1)
                .first().isEqualTo(result);

        assertThat(db.findAll(testTarget()))
                .hasSize(1)
                .first().isEqualTo(result);
    }

    @Test
    default void insertQuery() {
        DatabaseController db = databaseController();
        assertThat(db.findAll(testTarget()))
                .isEmpty();
        User result = db.insert(testTarget(), new User("test", 20));

        assertThat(db.findAll(testTarget()))
                .hasSize(1)
                .isEqualTo(Arrays.asList(result));
    }

    @Test
    default void deleteQuery() {
        DatabaseController db = databaseController();
        User result = db.insert(testTarget(), new User("test", 20));
        db.delete(testTarget(), result);
        assertThat(db.findByIds(testTarget(), Arrays.asList(result.id))).isEmpty();
    }

    @Test
    default void updateQuery() {
        DatabaseController db = databaseController();
        User result = db.insert(testTarget(), new User("test", 20));
        result.name = "updated";
        db.update(testTarget(), result);

        User updatedUser = db.findByIds(testTarget(), Arrays.asList(result.id)).get(0);
        assertThat(updatedUser.name).isEqualTo("updated");
    }
}
