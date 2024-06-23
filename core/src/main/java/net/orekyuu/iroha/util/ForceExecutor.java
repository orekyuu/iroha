package net.orekyuu.iroha.util;

import net.orekyuu.iroha.ThrowableConsumer;
import net.orekyuu.iroha.ThrowableFunction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ForceExecutor {

    public static <T> void execute(Iterable<T> iterable, ThrowableConsumer<T, SQLException> consumer) throws SQLException {
        List<SQLException> errors = new ArrayList<>();
        for (T t : iterable) {
            try {
                consumer.accept(t);
            } catch (SQLException e) {
                errors.add(e);
            }
        }
        if (!errors.isEmpty()) {
            SQLException exception = new SQLException();
            errors.forEach(exception::setNextException);
            throw exception;
        }
    }
}
