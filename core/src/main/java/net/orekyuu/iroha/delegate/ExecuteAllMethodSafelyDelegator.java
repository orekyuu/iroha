package net.orekyuu.iroha.delegate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import net.orekyuu.iroha.util.ThrowableConsumer;

public class ExecuteAllMethodSafelyDelegator<T> {
  private final List<T> values;

  public ExecuteAllMethodSafelyDelegator(List<T> values) {
    this.values = values;
  }

  public void executeSafely(ThrowableConsumer<T, SQLException> func) throws SQLException {
    var errors = new ArrayList<SQLException>();
    for (T v : values) {
      try {
        func.accept(v);
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
