package net.orekyuu.iroha.datasource;

import net.orekyuu.iroha.adaptor.ConnectionAdaptor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IrohaConnection extends ConnectionAdaptor {
    private final List<Connection> connections;

    public IrohaConnection(List<Connection> connection) {
        this.connections = connection;
    }

    @Override
    public Statement createStatement() throws SQLException {
        List<Statement> statements = new ArrayList<>();
        for (Connection connection : connections) {
            statements.add(connection.createStatement());
        }
        return new IrohaStatement(statements);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        List<PreparedStatement> statements = new ArrayList<>();
        for (Connection connection : connections) {
            statements.add(connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability));
        }
        return new IrohaPreparedStatement(statements);
    }

    @Override
    public void commit() throws SQLException {
        for (Connection connection : connections) {
            connection.commit();
        }
    }

    @Override
    public void rollback() throws SQLException {
        for (Connection connection : connections) {
            connection.rollback();
        }
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        for (Connection connection : connections) {
            connection.rollback(savepoint);
        }
    }

    @Override
    public void close() throws SQLException {
        for (Connection connection : connections) {
            connection.close();
        }
    }
}
