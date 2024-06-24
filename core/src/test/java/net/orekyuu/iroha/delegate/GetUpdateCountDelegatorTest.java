package net.orekyuu.iroha.delegate;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import net.orekyuu.iroha.mock.StatementAdaptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GetUpdateCountDelegatorTest {

  @Test
  void empty() throws SQLException {
    var delegator = new GetUpdateCountDelegator<>(Collections.emptyList());
    assertThat(delegator.delegate(Statement::getUpdateCount)).isEqualTo(-1);
  }

  @Test
  void allMinus() throws SQLException {
    var delegator =
        new GetUpdateCountDelegator<>(
            List.of(new GetUpdateCountStatement(-1), new GetUpdateCountStatement(-1)));
    assertThat(delegator.delegate(Statement::getUpdateCount)).isEqualTo(-1);
  }

  @Test
  void minusAndPositive() throws SQLException {
    var delegator =
        new GetUpdateCountDelegator<>(
            List.of(new GetUpdateCountStatement(-1), new GetUpdateCountStatement(2)));
    assertThat(delegator.delegate(Statement::getUpdateCount)).isEqualTo(2);
  }

  @Test
  void allPositive() throws SQLException {
    var delegator =
        new GetUpdateCountDelegator<>(
            List.of(new GetUpdateCountStatement(1), new GetUpdateCountStatement(2)));
    assertThat(delegator.delegate(Statement::getUpdateCount)).isEqualTo(3);
  }

  @Test
  void exception() throws SQLException {
    var delegator =
        new GetUpdateCountDelegator<>(
            List.of(
                new StatementAdaptor() {
                  @Override
                  public int getUpdateCount() throws SQLException {
                    throw new SQLException();
                  }
                }));
    Assertions.assertThrows(
        SQLException.class,
        () -> {
          delegator.delegate(Statement::getUpdateCount);
        });
  }

  static class GetUpdateCountStatement extends StatementAdaptor {
    final int result;

    GetUpdateCountStatement(int result) {
      this.result = result;
    }

    @Override
    public int getUpdateCount() throws SQLException {
      return result;
    }
  }
}
