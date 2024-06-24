package net.orekyuu.iroha.delegate;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import net.orekyuu.iroha.util.ThrowableFunction;

public class ExecuteDelegator<T extends Statement> {
  private final List<T> statements;

  public ExecuteDelegator(List<T> statements) {
    this.statements = statements;
  }

  public boolean execute(ThrowableFunction<T, Boolean, SQLException> func) throws SQLException {
    // check any succeeded statement
    boolean result = false;
    for (T statement : statements) {
      if (func.apply(statement)) {
        result = true;
      }
    }
    return result;
  }
}
