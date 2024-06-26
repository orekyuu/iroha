package net.orekyuu.iroha;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import javax.sql.DataSource;

public class AggregateDataSource extends WrapAdaptor implements DataSource {

  private final List<DataSource> dataSources;

  public AggregateDataSource(Collection<? extends DataSource> dataSources) {
    this.dataSources = new ArrayList<>(dataSources);
  }

  public AggregateDataSource(DataSource... dataSources) {
    this(Arrays.asList(dataSources));
  }

  @Override
  public Connection getConnection() throws SQLException {
    return null;
  }

  @Override
  public Connection getConnection(String username, String password) throws SQLException {
    return null;
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
