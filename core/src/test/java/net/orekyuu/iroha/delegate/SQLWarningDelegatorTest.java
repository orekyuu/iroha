package net.orekyuu.iroha.delegate;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import net.orekyuu.iroha.mock.StatementAdaptor;
import org.junit.jupiter.api.Test;

class SQLWarningDelegatorTest {

  @Test
  void empty() throws SQLException {
    var delegator = new SQLWarningDelegator<>(Collections.emptyList(), Statement::getWarnings);
    assertThat((Object) delegator.getSQLWarnings()).isNull();
  }

  @Test
  void single() throws SQLException {
    var warn1 = new SQLWarning("1");
    var delegator =
        new SQLWarningDelegator<>(List.of(new SQLWarningStatement(warn1)), Statement::getWarnings);

    assertThat((Iterable<Throwable>) delegator.getSQLWarnings()).containsExactly(warn1);
  }

  @Test
  void reduceWarnings() throws SQLException {
    var warn1 = new SQLWarning("1");
    var warn2 = new SQLWarning("2");
    var warn3 = new SQLWarning("3");
    var delegator =
        new SQLWarningDelegator<>(
            List.of(
                new SQLWarningStatement(warn1),
                new SQLWarningStatement(warn2),
                new SQLWarningStatement(warn3)),
            Statement::getWarnings);

    assertThat((Iterable<Throwable>) delegator.getSQLWarnings())
        .containsExactly(warn3, warn2, warn1);
  }

  @Test
  void reduceWarnings_whenNull() throws SQLException {
    var warn1 = new SQLWarning("1");
    var warn3 = new SQLWarning("3");
    var delegator =
        new SQLWarningDelegator<>(
            List.of(
                new SQLWarningStatement(warn1),
                new SQLWarningStatement(null),
                new SQLWarningStatement(warn3)),
            Statement::getWarnings);

    assertThat((Iterable<Throwable>) delegator.getSQLWarnings()).containsExactly(warn3, warn1);
  }

  @Test
  void reduceWarnings_whenNullOnly() throws SQLException {
    var delegator =
        new SQLWarningDelegator<>(
            List.of(
                new SQLWarningStatement(null),
                new SQLWarningStatement(null),
                new SQLWarningStatement(null)),
            Statement::getWarnings);

    assertThat((Iterable<Throwable>) delegator.getSQLWarnings()).isNull();
  }

  static class SQLWarningStatement extends StatementAdaptor {
    final SQLWarning warning;

    public SQLWarningStatement(SQLWarning warning) {
      this.warning = warning;
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
      return warning;
    }
  }
}
