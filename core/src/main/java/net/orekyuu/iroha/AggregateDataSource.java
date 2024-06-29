package net.orekyuu.iroha;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.sql.DataSource;

public class AggregateDataSource extends WrapAdaptor implements DataSource {

  private final DataSourceSelector selector;

  public AggregateDataSource(DataSourceSelector selector) {
    this.selector = selector;
  }

  @Override
  public Connection getConnection() throws SQLException {
    var selected = selector.select();
    if (selected.isEmpty()) {
      throw new SQLException(selector.getClass().getName() + " does not return empty list");
    }
    var connections = new ArrayList<Connection>();
    for (DataSource dataSource : selected) {
      connections.add(dataSource.getConnection());
    }
    return new AggregateConnection(connections);
  }

  @Override
  public Connection getConnection(String username, String password) throws SQLException {
    var selected = selector.select();
    if (selected.isEmpty()) {
      throw new SQLException(selector.getClass().getName() + " does not return empty list");
    }
    var connections = new ArrayList<Connection>();
    for (DataSource dataSource : selected) {
      connections.add(dataSource.getConnection(username, password));
    }
    return new AggregateConnection(connections);
  }

  @Override
  public PrintWriter getLogWriter() throws SQLException {
    throw new SQLFeatureNotSupportedException();
  }

  @Override
  public void setLogWriter(PrintWriter out) throws SQLException {
    throw new SQLFeatureNotSupportedException();
  }

  @Override
  public void setLoginTimeout(int seconds) throws SQLException {
    throw new SQLFeatureNotSupportedException();
  }

  @Override
  public int getLoginTimeout() throws SQLException {
    return 0;
  }

  @Override
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    return Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
  }
}
