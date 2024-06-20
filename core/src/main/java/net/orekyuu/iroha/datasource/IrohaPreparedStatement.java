package net.orekyuu.iroha.datasource;

import net.orekyuu.iroha.adaptor.PreparedStatementAdaptor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IrohaPreparedStatement extends PreparedStatementAdaptor {

    private final List<PreparedStatement> statements;

    public IrohaPreparedStatement(List<PreparedStatement> statements) {
        this.statements = statements;
    }

    @Override
    public ResultSet executeQuery() throws SQLException {
        List<ResultSet> resultSets = new ArrayList<>();
        for (PreparedStatement statement : statements) {
            resultSets.add(statement.executeQuery());
        }
        try {
            return resultSets.get(0);
        } finally {
            List<Exception> errors = new ArrayList<>();
            for (ResultSet resultSet : resultSets) {
                try {
                    resultSet.close();
                } catch (Exception e) {
                    errors.add(e);
                }
            }
            if (!errors.isEmpty()) {
                throw new SQLException(errors.toString());
            }
        }
    }
}
