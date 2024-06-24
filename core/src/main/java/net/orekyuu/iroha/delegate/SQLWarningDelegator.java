package net.orekyuu.iroha.delegate;

import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SQLWarningDelegator<T extends Statement> {
  private final List<T> statements;

  public SQLWarningDelegator(List<T> statements) {
    this.statements = statements;
  }

  public SQLWarning getSQLWarnings() throws SQLException {
    var warnings = new ArrayList<SQLWarning>();
    for (T statement : statements) {
      warnings.add(statement.getWarnings());
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
