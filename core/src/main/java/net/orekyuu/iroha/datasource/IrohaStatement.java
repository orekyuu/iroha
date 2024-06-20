package net.orekyuu.iroha.datasource;

import net.orekyuu.iroha.adaptor.StatementAdaptor;

import java.sql.Statement;
import java.util.List;

public class IrohaStatement extends StatementAdaptor {
    private List<Statement> statements;

    public IrohaStatement(List<Statement> statements) {
        this.statements = statements;
    }
}
