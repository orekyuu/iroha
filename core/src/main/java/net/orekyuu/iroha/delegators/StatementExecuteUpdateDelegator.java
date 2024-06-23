package net.orekyuu.iroha.delegators;

import net.orekyuu.iroha.ThrowableFunction;
import net.orekyuu.iroha.datasource.Delegator;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class StatementExecuteUpdateDelegator<T extends Statement> implements Delegator<T> {

    private final List<T> statements;

    public StatementExecuteUpdateDelegator(List<T> statements) {
        this.statements = statements;
    }

    public int delegate(ThrowableFunction<T, Integer, SQLException> func) throws SQLException {
        int result = 0;
        for (T statement : statements) {
            result += func.apply(statement);
        }
        return result;
    }
}
