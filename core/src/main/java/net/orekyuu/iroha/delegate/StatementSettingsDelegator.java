package net.orekyuu.iroha.delegate;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import net.orekyuu.iroha.util.ThrowableConsumer;
import net.orekyuu.iroha.util.ThrowableFunction;

public class StatementSettingsDelegator<T extends Statement> {
  private final List<T> statements;

  public StatementSettingsDelegator(List<T> statements) {
    this.statements = statements;
  }

  public void set(ThrowableConsumer<T, SQLException> setter) throws SQLException {
    // apply all statements
    for (T statement : statements) {
      setter.accept(statement);
    }
  }

  public <V> V get(ThrowableFunction<T, V, SQLException> getter) throws SQLException {
    // return first statement value
    for (T statement : statements) {
      return getter.apply(statement);
    }
    throw new SQLException("No statements found");
  }
}
