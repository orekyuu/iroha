package net.orekyuu.iroha.integration.mysql;

import net.orekyuu.iroha.integration.DatabaseController;
import net.orekyuu.iroha.integration.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MySqlDatabaseController implements DatabaseController {

    @Override
    public void createUserTableIfNotExists(DataSource dataSource) {
        connection(dataSource, c -> {
            prepareStatement(c, "CREATE TABLE IF NOT EXISTS users (\n" +
                    "    id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                    "    name VARCHAR(255) NOT NULL,\n" +
                    "    age INT NOT NULL\n" +
                    ")", ps -> {}, r -> null);
        });
    }

    @Override
    public void truncateUserTable(DataSource dataSource) {
        connection(dataSource, c -> {
            prepareStatement(c, "TRUNCATE TABLE users", ps -> {}, r -> null);
        });
    }

    @Override
    public List<User> findByIds(DataSource dataSource, List<Long> ids) {
        String param = ids.stream().map(it -> "?").collect(Collectors.joining(","));

        return connection(dataSource, c -> {
            return prepareStatement(c, "SELECT * FROM users WHERE id IN (" + param + ")", ps -> {
                for (int i = 0; i < ids.size(); i++) {
                    ps.setLong(i + 1, ids.get(i));
                }
            }, r -> new User(r.getLong("id"), r.getString("name"), r.getInt("age")));
        });
    }

    @Override
    public List<User> findAll(DataSource dataSource) {
        return connection(dataSource, c -> {
            return prepareStatement(c, "SELECT * FROM users", ps -> {},
                    r -> new User(r.getLong("id"), r.getString("name"), r.getInt("age")));
        });
    }

    @Override
    public User insert(DataSource dataSource, User user) {
        return null;
    }

    @Override
    public void update(DataSource dataSource, User user) {

    }

    @Override
    public void delete(DataSource dataSource, User user) {

    }

    @Override
    public List<User> batchInsert(DataSource dataSource, List<User> users) {
        return Collections.emptyList();
    }

    @Override
    public void batchUpdate(DataSource dataSource, List<User> users) {

    }

    @Override
    public void batchDelete(DataSource dataSource, List<User> users) {

    }
}
