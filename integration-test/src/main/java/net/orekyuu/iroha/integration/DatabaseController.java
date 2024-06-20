package net.orekyuu.iroha.integration;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class DatabaseController {

    public interface ThrowableFunction<T, R> {
        R apply(T t) throws Exception;
    }

    public interface ThrowableConsumer<T> {
        void consume(T t) throws Exception;
    }

    protected QueryResult query(DataSource dataSource, String sql, ThrowableConsumer<PreparedStatement> block) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement st = connection.prepareStatement(sql);
            block.consume(st);
            return new QueryResult(st.executeQuery());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected int execute(DataSource dataSource, String sql, ThrowableConsumer<PreparedStatement> block) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement st = connection.prepareStatement(sql);
            block.consume(st);
            return st.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected int executeAndGetGeneratedId(DataSource dataSource, String sql, ThrowableConsumer<PreparedStatement> block) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            block.consume(st);
            return st.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected int[] batchExecute(DataSource dataSource, String sql, ThrowableConsumer<PreparedStatement> block) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            block.consume(st);
            st.executeBatch();
            ArrayList<Integer> ids = new ArrayList<>();
            try (ResultSet rs = st.getGeneratedKeys()) {
                while (rs.next()) {
                    ids.add(rs.getInt(1));
                }
            }
            return ids.stream().mapToInt(Integer::intValue).toArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public abstract void createUserTableIfNotExists(DataSource dataSource);

    public abstract void truncateUserTable(DataSource dataSource);

    public abstract List<User> findByIds(DataSource dataSource, List<Long> ids);

    public abstract List<User> findAll(DataSource dataSource);

    public abstract User insert(DataSource dataSource, User user);

    public abstract void update(DataSource dataSource, User user);

    public abstract void delete(DataSource dataSource, User user);

    public abstract List<User> batchInsert(DataSource dataSource, List<User> users);

    public abstract void batchUpdate(DataSource dataSource, List<User> users);

    public abstract void batchDelete(DataSource dataSource, List<User> users);
}
