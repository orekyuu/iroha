package net.orekyuu.iroha.integration.mysql;

import net.orekyuu.iroha.integration.DatabaseController;
import net.orekyuu.iroha.integration.QueryResult;
import net.orekyuu.iroha.integration.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MySqlDatabaseController extends DatabaseController {

    private User mapping(ResultSet rs) {
        try {
            return new User(rs.getLong("id"), rs.getString("name"), rs.getInt("age"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createUserTableIfNotExists(DataSource dataSource) {
        String sql = "CREATE TABLE IF NOT EXISTS users (\n" +
                "    id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                "    name VARCHAR(255) NOT NULL,\n" +
                "    age INT NOT NULL\n" +
                ")";
        execute(dataSource, sql, st -> {});
    }

    @Override
    public void truncateUserTable(DataSource dataSource) {
        String sql = "TRUNCATE TABLE users";
        execute(dataSource, sql, st -> {});
    }

    @Override
    public List<User> findByIds(DataSource dataSource, List<Long> ids) {
        String param = ids.stream().map(it -> "?").collect(Collectors.joining(","));

        QueryResult result = query(dataSource, "SELECT * FROM users WHERE id IN (" + param + ")", ps -> {
            for (int i = 0; i < ids.size(); i++) {
                ps.setLong(i + 1, ids.get(i));
            }
        });
        return result.stream()
                .map(this::mapping)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findAll(DataSource dataSource) {
        QueryResult result = query(dataSource, "SELECT * FROM users", ps -> {});
        return result.stream()
                .map(this::mapping)
                .collect(Collectors.toList());
    }

    @Override
    public User insert(DataSource dataSource, User user) {
        int id = executeAndGetGeneratedId(dataSource, "INSERT INTO users(name, age) VALUES (?, ?)", ps -> {
            ps.setString(1, user.name);
            ps.setInt(2, user.age);
        });
        return new User((long) id, user.name, user.age);
    }

    @Override
    public void update(DataSource dataSource, User user) {
        execute(dataSource, "UPDATE users SET name = ?, age = ? WHERE id = ? ", ps -> {
            ps.setString(1, user.name);
            ps.setInt(2, user.age);
            ps.setLong(3, user.id);
        });
    }

    @Override
    public void delete(DataSource dataSource, User user) {
        execute(dataSource, "DELETE FROM users WHERE id = ?", ps -> {
            ps.setLong(1, user.id);
        });
    }

    @Override
    public List<User> batchInsert(DataSource dataSource, List<User> users) {
        int[] ids = batchExecute(dataSource, "INSERT INTO users(name, age) VALUES (?, ?)", ps -> {
            for (User user : users) {
                ps.setString(1, user.name);
                ps.setInt(2, user.age);
                ps.addBatch();
                ps.clearParameters();
            }
        });
        ArrayList<User> results = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            results.add(new User((long)ids[i], user.name, user.age));
        }
        return results;
    }

    @Override
    public void batchUpdate(DataSource dataSource, List<User> users) {
        batchExecute(dataSource, "UPDATE users SET name = ?, age = ? WHERE id = ? ", ps -> {
            for (User user : users) {
                ps.setString(1, user.name);
                ps.setInt(2, user.age);
                ps.setLong(3, user.id);
                ps.addBatch();
                ps.clearParameters();
            }
        });
    }

    @Override
    public void batchDelete(DataSource dataSource, List<User> users) {
        batchExecute(dataSource, "DELETE FROM users WHERE id = ?", ps -> {
            for (User user : users) {
                ps.setLong(1, user.id);
                ps.addBatch();
                ps.clearParameters();
            }
        });
    }
}
