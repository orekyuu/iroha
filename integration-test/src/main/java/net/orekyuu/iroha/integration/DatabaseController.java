package net.orekyuu.iroha.integration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public interface DatabaseController {

    interface ThrowableFunction<T, R> {
        R apply(T t) throws Exception;
    }

    interface ThrowableConsumer<T> {
        void consume(T t) throws Exception;
    }

    default <R> R connection(DataSource dataSource, ThrowableFunction<Connection, R> block) {
        try (Connection connection = dataSource.getConnection()) {
            return block.apply(connection);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    default void connection(DataSource dataSource, ThrowableConsumer<Connection> block) {
        connection(dataSource, c -> {
            block.consume(c);
            return Void.class;
        });
    }

    default <R> List<R> prepareStatement(Connection c, String sql, ThrowableConsumer<PreparedStatement> statementConsumer, ThrowableFunction<ResultSet, R> block) {
        try (PreparedStatement statement = c.prepareStatement(sql)) {
            statementConsumer.consume(statement);
            try (ResultSet resultSet = statement.executeQuery()) {
                ArrayList<R> result = new ArrayList<>();
                while (resultSet.next()) {
                    result.add(block.apply(resultSet));
                }
                return result;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void createUserTableIfNotExists(DataSource dataSource);

    void truncateUserTable(DataSource dataSource);

    List<User> findByIds(DataSource dataSource, List<Long> ids);

    List<User> findAll(DataSource dataSource);

    User insert(DataSource dataSource, User user);
    void update(DataSource dataSource, User user);
    void delete(DataSource dataSource, User user);

    List<User> batchInsert(DataSource dataSource, List<User> users);
    void batchUpdate(DataSource dataSource, List<User> users);
    void batchDelete(DataSource dataSource, List<User> users);
}
