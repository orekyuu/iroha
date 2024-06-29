package net.orekyuu.iroha.delegate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import net.orekyuu.iroha.AggregatePreparedStatement;
import net.orekyuu.iroha.util.ThrowableFunction;

public class CreatePreparedStatementDelegator {
  private final List<Connection> connections;
  private final Connection parent;

  public CreatePreparedStatementDelegator(List<Connection> connections, Connection parent) {
    this.connections = connections;
    this.parent = parent;
  }

  public PreparedStatement delegate(
      ThrowableFunction<Connection, PreparedStatement, SQLException> func) throws SQLException {
    List<PreparedStatement> statements = new ArrayList<>();
    for (Connection connection : connections) {
      statements.add(func.apply(connection));
    }
    return new AggregatePreparedStatement(parent, statements);
  }
}
