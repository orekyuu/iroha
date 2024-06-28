package net.orekyuu.iroha.delegate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import net.orekyuu.iroha.AggregateResultSet;
import net.orekyuu.iroha.AggregateStatement;
import net.orekyuu.iroha.util.ThrowableFunction;

public class ResultSetDelegator<T extends Statement, S extends AggregateStatement<T>> {
  final List<T> instance;
  private final S statement;

  public ResultSetDelegator(List<T> instance, S statement) {
    this.instance = instance;
    this.statement = statement;
  }

  public ResultSet delegate(ThrowableFunction<T, ResultSet, SQLException> func)
      throws SQLException {
    var results = new ArrayList<ResultSet>();
    for (T t : instance) {
      results.add(func.apply(t));
    }
    return new AggregateResultSet(results, statement);
  }
}
