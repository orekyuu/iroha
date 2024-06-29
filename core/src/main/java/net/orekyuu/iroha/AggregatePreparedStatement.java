package net.orekyuu.iroha;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.List;

public class AggregatePreparedStatement extends AggregateStatement<PreparedStatement>
    implements PreparedStatement {
  public AggregatePreparedStatement(Connection connection, List<PreparedStatement> statements) {
    super(connection, statements);
  }

  @Override
  public ResultSet executeQuery() throws SQLException {
    return resultSetDelegator.delegate(PreparedStatement::executeQuery);
  }

  @Override
  public boolean execute() throws SQLException {
    return executeDelegator.execute(PreparedStatement::execute);
  }

  @Override
  public int executeUpdate() throws SQLException {
    return executeUpdateDelegator.delegate(PreparedStatement::executeUpdate);
  }

  @Override
  public void clearParameters() throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(PreparedStatement::clearParameters);
  }

  @Override
  public void clearBatch() throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(PreparedStatement::clearBatch);
  }

  @Override
  public void addBatch() throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(PreparedStatement::addBatch);
  }

  @Override
  public ResultSetMetaData getMetaData() throws SQLException {
    throw new SQLFeatureNotSupportedException();
  }

  @Override
  public ParameterMetaData getParameterMetaData() throws SQLException {
    return settingsDelegator.get(PreparedStatement::getParameterMetaData);
  }

  @Override
  public void setArray(int parameterIndex, Array x) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(st -> st.setArray(parameterIndex, x));
  }

  @Override
  public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(
        st -> st.setAsciiStream(parameterIndex, x, length));
  }

  @Override
  public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(
        st -> st.setAsciiStream(parameterIndex, x, length));
  }

  @Override
  public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(st -> st.setAsciiStream(parameterIndex, x));
  }

  @Override
  public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(st -> st.setBigDecimal(parameterIndex, x));
  }

  @Override
  public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(
        st -> st.setBinaryStream(parameterIndex, x, length));
  }

  @Override
  public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(st -> st.setBinaryStream(parameterIndex, x));
  }

  @Override
  public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(
        st -> st.setBinaryStream(parameterIndex, x, length));
  }

  @Override
  public void setBlob(int parameterIndex, InputStream inputStream, long length)
      throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(
        st -> st.setBlob(parameterIndex, inputStream, length));
  }

  @Override
  public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(st -> st.setBlob(parameterIndex, inputStream));
  }

  @Override
  public void setBlob(int parameterIndex, Blob x) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(st -> st.setBlob(parameterIndex, x));
  }

  @Override
  public void setBoolean(int parameterIndex, boolean x) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(st -> st.setBoolean(parameterIndex, x));
  }

  @Override
  public void setByte(int parameterIndex, byte x) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(st -> st.setByte(parameterIndex, x));
  }

  @Override
  public void setBytes(int parameterIndex, byte[] x) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(st -> st.setBytes(parameterIndex, x));
  }

  @Override
  public void setCharacterStream(int parameterIndex, Reader reader, long length)
      throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(
        st -> st.setCharacterStream(parameterIndex, reader, length));
  }

  @Override
  public void setCharacterStream(int parameterIndex, Reader reader, int length)
      throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(
        st -> st.setCharacterStream(parameterIndex, reader, length));
  }

  @Override
  public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(
        st -> st.setCharacterStream(parameterIndex, reader));
  }

  @Override
  public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(st -> st.setClob(parameterIndex, reader, length));
  }

  @Override
  public void setClob(int parameterIndex, Reader reader) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(st -> st.setClob(parameterIndex, reader));
  }

  @Override
  public void setClob(int parameterIndex, Clob x) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(st -> st.setClob(parameterIndex, x));
  }

  @Override
  public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(st -> st.setDate(parameterIndex, x, cal));
  }

  @Override
  public void setDate(int parameterIndex, Date x) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(st -> st.setDate(parameterIndex, x));
  }

  @Override
  public void setDouble(int parameterIndex, double x) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(st -> st.setDouble(parameterIndex, x));
  }

  @Override
  public void setFloat(int parameterIndex, float x) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(st -> st.setFloat(parameterIndex, x));
  }

  @Override
  public void setInt(int parameterIndex, int x) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(st -> st.setInt(parameterIndex, x));
  }

  @Override
  public void setLong(int parameterIndex, long x) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(st -> st.setLong(parameterIndex, x));
  }

  @Override
  public void setNCharacterStream(int parameterIndex, Reader value, long length)
      throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(
        st -> st.setNCharacterStream(parameterIndex, value, length));
  }

  @Override
  public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(
        st -> st.setNCharacterStream(parameterIndex, value));
  }

  @Override
  public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(
        st -> st.setNClob(parameterIndex, reader, length));
  }

  @Override
  public void setNClob(int parameterIndex, Reader reader) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(st -> st.setNClob(parameterIndex, reader));
  }

  @Override
  public void setNClob(int parameterIndex, NClob value) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(st -> st.setNClob(parameterIndex, value));
  }

  @Override
  public void setNString(int parameterIndex, String value) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(st -> st.setNString(parameterIndex, value));
  }

  @Override
  public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(
        st -> st.setNull(parameterIndex, sqlType, typeName));
  }

  @Override
  public void setNull(int parameterIndex, int sqlType) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(st -> st.setNull(parameterIndex, sqlType));
  }

  @Override
  public void setObject(int parameterIndex, Object x, SQLType targetSqlType, int scaleOrLength)
      throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(
        st -> st.setObject(parameterIndex, x, targetSqlType, scaleOrLength));
  }

  @Override
  public void setObject(int parameterIndex, Object x, SQLType targetSqlType) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(
        st -> st.setObject(parameterIndex, x, targetSqlType));
  }

  @Override
  public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength)
      throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(
        st -> st.setObject(parameterIndex, x, targetSqlType, scaleOrLength));
  }

  @Override
  public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(
        st -> st.setObject(parameterIndex, x, targetSqlType));
  }

  @Override
  public void setObject(int parameterIndex, Object x) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(st -> st.setObject(parameterIndex, x));
  }

  @Override
  public void setRef(int parameterIndex, Ref x) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(st -> st.setRef(parameterIndex, x));
  }

  @Override
  public void setRowId(int parameterIndex, RowId x) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(st -> st.setRowId(parameterIndex, x));
  }

  @Override
  public void setShort(int parameterIndex, short x) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(st -> st.setShort(parameterIndex, x));
  }

  @Override
  public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(st -> st.setSQLXML(parameterIndex, xmlObject));
  }

  @Override
  public void setString(int parameterIndex, String x) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(st -> st.setString(parameterIndex, x));
  }

  @Override
  public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(st -> st.setTime(parameterIndex, x, cal));
  }

  @Override
  public void setTime(int parameterIndex, Time x) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(st -> st.setTime(parameterIndex, x));
  }

  @Override
  public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(st -> st.setTimestamp(parameterIndex, x, cal));
  }

  @Override
  public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(st -> st.setTimestamp(parameterIndex, x));
  }

  @Override
  @Deprecated
  public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(
        st -> st.setUnicodeStream(parameterIndex, x, length));
  }

  @Override
  public void setURL(int parameterIndex, URL x) throws SQLException {
    executeAllMethodSafelyDelegator.executeSafely(st -> st.setURL(parameterIndex, x));
  }
}
