package net.orekyuu.iroha.delegate;

import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.orekyuu.iroha.util.ThrowableFunction;

public class SQLWarningDelegator<T> {
  private final List<T> instances;
  private final ThrowableFunction<T, SQLWarning, SQLException> func;

  public SQLWarningDelegator(
      List<T> instances, ThrowableFunction<T, SQLWarning, SQLException> func) {
    this.instances = instances;
    this.func = func;
  }

  public SQLWarning getSQLWarnings() throws SQLException {
    var warnings = new ArrayList<SQLWarning>();
    for (T instance : instances) {
      warnings.add(func.apply(instance));
    }
    return warnings.stream()
        .filter(Objects::nonNull)
        .reduce(
            (a, b) -> {
              b.setNextWarning(a);
              return b;
            })
        .orElse(null);
  }
}
