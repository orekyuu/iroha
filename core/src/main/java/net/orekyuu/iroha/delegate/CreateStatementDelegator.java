package net.orekyuu.iroha.delegate;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import net.orekyuu.iroha.AggregateStatement;
import net.orekyuu.iroha.util.ThrowableFunction;

public class CreateStatementDelegator {
  private final List<Connection> connections;
  private final Connection parent;

  public CreateStatementDelegator(List<Connection> connections, Connection parent) {
    this.connections = connections;
    this.parent = parent;
  }

  public Statement delegate(ThrowableFunction<Connection, Statement, SQLException> func)
      throws SQLException {
    List<Statement> statements = new ArrayList<>();
    for (Connection connection : connections) {
      statements.add(func.apply(connection));
    }
    return new AggregateStatement<>(parent, statements);
  }
}
