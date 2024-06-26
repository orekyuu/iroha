package net.orekyuu.iroha;

import java.sql.*;
import java.util.List;
import net.orekyuu.iroha.delegate.*;

public class AggregateStatement<T extends Statement> extends WrapAdaptor implements Statement {
  final Connection connection;
  final List<T> statements;
  final ExecuteUpdateDelegator<T> executeUpdateDelegator;
  final ExecuteDelegator<T> executeDelegator;
  final StatementSettingsDelegator<T> settingsDelegator;
  final SQLWarningDelegator<T> sqlWarningDelegator;
  final GetUpdateCountDelegator<T> getUpdateCountDelegator;
  final ExecuteAllMethodSafelyDelegator<T> executeAllMethodSafelyDelegator;
  final ResultSetDelegator<T, AggregateStatement<T>> resultSetDelegator;

  public AggregateStatement(Connection connection, List<T> statements) {
    this.connection = connection;
    this.statements = statements;
    this.executeUpdateDelegator = new ExecuteUpdateDelegator<>(statements);
    this.executeDelegator = new ExecuteDelegator<>(statements);
    this.settingsDelegator = new StatementSettingsDelegator<>(statements);
    this.sqlWarningDelegator = new SQLWarningDelegator<>(statements, Statement::getWarnings);
    this.getUpdateCountDelegator = new GetUpdateCountDelegator<>(statements);
    this.executeAllMethodSafelyDelegator = new ExecuteAllMethodSafelyDelegator<>(statements);
    this.resultSetDelegator = new ResultSetDelegator<>(statements, this);
  }

  @Override
  public int executeUpdate(String sql) throws SQLException {
    return executeUpdateDelegator.delegate(st -> st.executeUpdate(sql));
  }

  @Override
  public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
    return executeUpdateDelegator.delegate(st -> st.executeUpdate(sql, autoGeneratedKeys));
  }

  @Override
  public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
    return executeUpdateDelegator.delegate(st -> st.executeUpdate(sql, columnIndexes));
  }

  @Override
  public int executeUpdate(String sql, String[] columnNames) throws SQLException {
    return executeUpdateDelegator.delegate(st -> st.executeUpdate(sql, columnNames));
  }

  @Override
  public boolean execute(String sql) throws SQLException {
    return executeDelegator.execute(st -> st.execute(sql));
  }

  @Override
  public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
    return executeDelegator.execute(st -> st.execute(sql, autoGeneratedKeys));
  }

  @Override
  public boolean execute(String sql, int[] columnIndexes) throws SQLException {
    return executeDelegator.execute(st -> st.execute(sql, columnIndexes));
  }

  @Override
  public boolean execute(String sql, String[] columnNames) throws SQLException {
    return executeDelegator.execute(st -> st.execute(sql, columnNames));
  }

  @Override
  public ResultSet executeQuery(String sql) throws SQLException {
    return resultSetDelegator.delegate(st -> st.executeQuery(sql));
  }

  @Override
  public void close() throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(Statement::close);
  }

  @Override
  public int getMaxFieldSize() throws SQLException {
    return settingsDelegator.get(Statement::getMaxFieldSize);
  }

  @Override
  public void setMaxFieldSize(int max) throws SQLException {
    settingsDelegator.set(st -> st.setMaxFieldSize(max));
  }

  @Override
  public int getMaxRows() throws SQLException {
    return settingsDelegator.get(Statement::getMaxRows);
  }

  @Override
  public void setMaxRows(int max) throws SQLException {
    settingsDelegator.set(st -> st.setMaxRows(max));
  }

  @Override
  public void setEscapeProcessing(boolean enable) throws SQLException {
    settingsDelegator.set(st -> st.setEscapeProcessing(enable));
  }

  @Override
  public int getQueryTimeout() throws SQLException {
    return settingsDelegator.get(Statement::getQueryTimeout);
  }

  @Override
  public void setQueryTimeout(int seconds) throws SQLException {
    settingsDelegator.set(st -> st.setQueryTimeout(seconds));
  }

  @Override
  public void cancel() throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(Statement::cancel);
  }

  @Override
  public SQLWarning getWarnings() throws SQLException {
    return sqlWarningDelegator.getSQLWarnings();
  }

  @Override
  public void clearWarnings() throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(Statement::clearWarnings);
  }

  @Override
  public void setCursorName(String name) throws SQLException {
    throw new TodoException();
  }

  @Override
  public ResultSet getResultSet() throws SQLException {
    return resultSetDelegator.delegate(Statement::getResultSet);
  }

  @Override
  public int getUpdateCount() throws SQLException {
    return getUpdateCountDelegator.delegate(Statement::getUpdateCount);
  }

  @Override
  public boolean getMoreResults() throws SQLException {
    throw new TodoException();
  }

  @Override
  public void setFetchDirection(int direction) throws SQLException {
    settingsDelegator.set(st -> st.setFetchDirection(direction));
  }

  @Override
  public int getFetchDirection() throws SQLException {
    return settingsDelegator.get(Statement::getFetchDirection);
  }

  @Override
  public void setFetchSize(int rows) throws SQLException {
    settingsDelegator.set(st -> st.setFetchSize(rows));
  }

  @Override
  public int getFetchSize() throws SQLException {
    return settingsDelegator.get(Statement::getFetchSize);
  }

  @Override
  public int getResultSetConcurrency() throws SQLException {
    return settingsDelegator.get(Statement::getResultSetConcurrency);
  }

  @Override
  public int getResultSetType() throws SQLException {
    return settingsDelegator.get(Statement::getResultSetType);
  }

  @Override
  public void addBatch(String sql) throws SQLException {
    throw new TodoException();
  }

  @Override
  public void clearBatch() throws SQLException {
    throw new TodoException();
  }

  @Override
  public int[] executeBatch() throws SQLException {
    // TODO
    throw new TodoException();
  }

  @Override
  public Connection getConnection() throws SQLException {
    return connection;
  }

  @Override
  public boolean getMoreResults(int current) throws SQLException {
    throw new TodoException();
  }

  @Override
  public ResultSet getGeneratedKeys() throws SQLException {
    return resultSetDelegator.delegate(Statement::getGeneratedKeys);
  }

  @Override
  public int getResultSetHoldability() throws SQLException {
    throw new TodoException();
  }

  @Override
  public boolean isClosed() throws SQLException {
    for (T statement : statements) {
      if (!statement.isClosed()) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void setPoolable(boolean poolable) throws SQLException {
    settingsDelegator.set(st -> st.setPoolable(poolable));
  }

  @Override
  public boolean isPoolable() throws SQLException {
    return settingsDelegator.get(Statement::isPoolable);
  }

  @Override
  public void closeOnCompletion() throws SQLException {
    settingsDelegator.set(Statement::closeOnCompletion);
  }

  @Override
  public boolean isCloseOnCompletion() throws SQLException {
    return settingsDelegator.get(Statement::isCloseOnCompletion);
  }
}
