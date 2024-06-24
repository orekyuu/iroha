package net.orekyuu.iroha.delegate;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import net.orekyuu.iroha.util.ThrowableToIntFunction;

public class GetUpdateCountDelegator<T extends Statement> {
  private final List<T> statements;

  public GetUpdateCountDelegator(List<T> statements) {
    this.statements = statements;
  }

  public int delegate(ThrowableToIntFunction<T, SQLException> func) throws SQLException {
    boolean hasPositive = false;
    int sumPositiveValue = 0;
    for (T statement : statements) {
      var result = func.applyAsInt(statement);
      if (-1 < result) {
        hasPositive = true;
      }
      // when cannot be updated, set to 0
      sumPositiveValue += Math.max(0, result);
    }
    // when everything -1
    if (!hasPositive) {
      return -1;
    }
    // when everything return update counts.
    return sumPositiveValue;
  }
}
