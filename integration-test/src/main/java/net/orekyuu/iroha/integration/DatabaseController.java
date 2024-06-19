package net.orekyuu.iroha.integration;

import javax.sql.DataSource;
import java.util.List;

public interface DatabaseController {

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
