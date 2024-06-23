package net.orekyuu.iroha.datasource;

import net.orekyuu.iroha.ThrowableFunction;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class IrohaPreparedStatement extends IrohaStatement<PreparedStatement> implements PreparedStatement {

    public IrohaPreparedStatement(IrohaConnection connection, List<PreparedStatement> statements) {
        super(statements, connection);
    }

    @Override
    public void setString(int parameterIndex, String x) throws SQLException {
        delegate((PreparedStatement s) -> s.setString(parameterIndex, x));
    }

    @Override
    public void setLong(int parameterIndex, long x) throws SQLException {
        delegate((PreparedStatement s) -> s.setLong(parameterIndex, x));
    }

    @Override
    public void setInt(int parameterIndex, int x) throws SQLException {
        delegate((PreparedStatement s) -> s.setInt(parameterIndex, x));
    }

    @Override
    public void setArray(int parameterIndex, Array x) throws SQLException {
        delegate((PreparedStatement s) -> s.setArray(parameterIndex, x));
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
        delegate((PreparedStatement s) -> s.setAsciiStream(parameterIndex, x));
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
        delegate((PreparedStatement s) -> s.setAsciiStream(parameterIndex, x, length));
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
        delegate((PreparedStatement s) -> s.setAsciiStream(parameterIndex, x, length));
    }

    @Override
    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
        delegate((PreparedStatement s) -> s.setBigDecimal(parameterIndex, x));
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
        delegate((PreparedStatement s) -> s.setBinaryStream(parameterIndex, x));
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
        delegate((PreparedStatement s) -> s.setBinaryStream(parameterIndex, x));
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
        delegate((PreparedStatement s) -> s.setBinaryStream(parameterIndex, x));
    }

    @Override
    public void setBlob(int parameterIndex, Blob x) throws SQLException {
        delegate((PreparedStatement s) -> s.setBlob(parameterIndex, x));
    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
        delegate((PreparedStatement s) -> s.setBlob(parameterIndex, inputStream));
    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
        delegate((PreparedStatement s) -> s.setBlob(parameterIndex, inputStream, length));
    }

    @Override
    public void setBoolean(int parameterIndex, boolean x) throws SQLException {
        delegate((PreparedStatement s) -> s.setBoolean(parameterIndex, x));
    }

    @Override
    public void setByte(int parameterIndex, byte x) throws SQLException {
        delegate((PreparedStatement s) -> s.setByte(parameterIndex, x));
    }

    @Override
    public void setBytes(int parameterIndex, byte[] x) throws SQLException {
        delegate((PreparedStatement s) -> s.setBytes(parameterIndex, x));
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
        delegate((PreparedStatement s) -> s.setCharacterStream(parameterIndex, reader));
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
        delegate((PreparedStatement s) -> s.setCharacterStream(parameterIndex, reader, length));
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
        delegate((PreparedStatement s) -> s.setCharacterStream(parameterIndex, reader, length));
    }

    @Override
    public void setClob(int parameterIndex, Clob x) throws SQLException {
        delegate((PreparedStatement s) -> s.setClob(parameterIndex, x));
    }

    @Override
    public void setClob(int parameterIndex, Reader reader) throws SQLException {
        delegate((PreparedStatement s) -> s.setClob(parameterIndex, reader));
    }

    @Override
    public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
        delegate((PreparedStatement s) -> s.setClob(parameterIndex, reader, length));
    }

    @Override
    public void setDate(int parameterIndex, Date x) throws SQLException {
        delegate((PreparedStatement s) -> s.setDate(parameterIndex, x));
    }

    @Override
    public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
        delegate((PreparedStatement s) -> s.setDate(parameterIndex, x, cal));
    }

    @Override
    public void setDouble(int parameterIndex, double x) throws SQLException {
        delegate((PreparedStatement s) -> s.setDouble(parameterIndex, x));
    }

    @Override
    public void setFloat(int parameterIndex, float x) throws SQLException {
        delegate((PreparedStatement s) -> s.setFloat(parameterIndex, x));
    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
        delegate((PreparedStatement s) -> s.setNCharacterStream(parameterIndex, value));
    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
        delegate((PreparedStatement s) -> s.setNCharacterStream(parameterIndex, value, length));
    }

    @Override
    public void setNClob(int parameterIndex, NClob value) throws SQLException {
        delegate((PreparedStatement s) -> s.setNClob(parameterIndex, value));
    }

    @Override
    public void setNClob(int parameterIndex, Reader reader) throws SQLException {
        delegate((PreparedStatement s) -> s.setNClob(parameterIndex, reader));
    }

    @Override
    public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
        delegate((PreparedStatement s) -> s.setNClob(parameterIndex, reader, length));
    }

    @Override
    public void setNString(int parameterIndex, String value) throws SQLException {
        delegate((PreparedStatement s) -> s.setNString(parameterIndex, value));
    }

    @Override
    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        delegate((PreparedStatement s) -> s.setNull(parameterIndex, sqlType));
    }

    @Override
    public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
        delegate((PreparedStatement s) -> s.setNull(parameterIndex, sqlType, typeName));
    }

    @Override
    public void setObject(int parameterIndex, Object x) throws SQLException {
        delegate((PreparedStatement s) -> s.setObject(parameterIndex, x));
    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
        delegate((PreparedStatement s) -> s.setObject(parameterIndex, x, targetSqlType));
    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
        delegate((PreparedStatement s) -> s.setObject(parameterIndex, x, targetSqlType, scaleOrLength));
    }

    @Override
    public void setRef(int parameterIndex, Ref x) throws SQLException {
        delegate((PreparedStatement s) -> s.setRef(parameterIndex, x));
    }

    @Override
    public void setShort(int parameterIndex, short x) throws SQLException {
        delegate((PreparedStatement s) -> s.setShort(parameterIndex, x));
    }

    @Override
    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
        delegate((PreparedStatement s) -> s.setSQLXML(parameterIndex, xmlObject));
    }

    @Override
    public void setTime(int parameterIndex, Time x) throws SQLException {
        delegate((PreparedStatement s) -> s.setTime(parameterIndex, x));
    }

    @Override
    public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
        delegate((PreparedStatement s) -> s.setTime(parameterIndex, x, cal));
    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
        delegate((PreparedStatement s) -> s.setTimestamp(parameterIndex, x));
    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
        delegate((PreparedStatement s) -> s.setTimestamp(parameterIndex, x, cal));
    }

    @Override
    public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
        delegate((PreparedStatement s) -> s.setUnicodeStream(parameterIndex, x, length));
    }

    @Override
    public void setURL(int parameterIndex, URL x) throws SQLException {
        delegate((PreparedStatement s) -> s.setURL(parameterIndex, x));
    }

    @Override
    public void setObject(int parameterIndex, Object x, SQLType targetSqlType) throws SQLException {
        delegate((PreparedStatement s) -> s.setObject(parameterIndex, x, targetSqlType));
    }

    @Override
    public void setObject(int parameterIndex, Object x, SQLType targetSqlType, int scaleOrLength) throws SQLException {
        delegate((PreparedStatement s) -> s.setObject(parameterIndex, x, targetSqlType, scaleOrLength));
    }

    @Override
    public void setRowId(int parameterIndex, RowId x) throws SQLException {
        delegate((PreparedStatement s) -> s.setRowId(parameterIndex, x));
    }

    @Override
    public ParameterMetaData getParameterMetaData() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public boolean execute() throws SQLException {
        List<Boolean> results = delegate((PreparedStatement s) -> {
            return s.execute();
        });
        return results.contains(true);
    }

    @Override
    public void addBatch() throws SQLException {
        delegate((PreparedStatement s) -> s.addBatch());
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void clearParameters() throws SQLException {
        delegate((PreparedStatement s) -> s.addBatch());
    }

    @Override
    public ResultSet executeQuery() throws SQLException {
        ArrayList<ResultSet> results = new ArrayList<>();
        for (PreparedStatement statement : statements) {
            results.add(statement.executeQuery());
        }
        return new IrohaResultSet(results, this);
    }

    @Override
    public int executeUpdate() throws SQLException {
        List<Integer> results = delegate(st -> {
            return st.executeUpdate();
        });

        return results.stream()
                .reduce(0, Integer::sum);
    }
}
