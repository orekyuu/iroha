package net.orekyuu.iroha.delegate;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import net.orekyuu.iroha.util.ThrowableToIntFunction;

public class ExecuteUpdateDelegator<T extends Statement> {
  private final List<T> statements;

  public ExecuteUpdateDelegator(List<T> statements) {
    this.statements = statements;
  }

  public int delegate(ThrowableToIntFunction<T, SQLException> func) throws SQLException {
    // sum update row count
    int result = 0;
    for (T statement : statements) {
      result += func.applyAsInt(statement);
    }
    return result;
  }
}
