package net.orekyuu.iroha.delegate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import net.orekyuu.iroha.mock.StatementAdaptor;
import org.junit.jupiter.api.Test;

class ExecuteDelegatorTest {

  @Test
  void empty() throws SQLException {
    var delegator = new ExecuteDelegator<>(Collections.emptyList());
    assertThat(delegator.execute(st -> st.execute(""))).isFalse();
  }

  @Test
  void trueOnly() throws SQLException {
    var delegator =
        new ExecuteDelegator<>(List.of(new ExecuteStatement(true), new ExecuteStatement(true)));
    assertThat(delegator.execute(st -> st.execute(""))).isTrue();
  }

  @Test
  void falseOnly() throws SQLException {
    var delegator =
        new ExecuteDelegator<>(List.of(new ExecuteStatement(false), new ExecuteStatement(false)));
    assertThat(delegator.execute(st -> st.execute(""))).isFalse();
  }

  @Test
  void trueAndFalse() throws SQLException {
    var delegator =
        new ExecuteDelegator<>(List.of(new ExecuteStatement(false), new ExecuteStatement(true)));
    assertThat(delegator.execute(st -> st.execute(""))).isTrue();
  }

  @Test
  void throwException() throws SQLException {
    var delegator =
        new ExecuteDelegator<>(
            List.of(
                new StatementAdaptor() {
                  @Override
                  public boolean execute(String sql) throws SQLException {
                    throw new SQLException("");
                  }
                }));
    assertThrows(
        SQLException.class,
        () -> {
          delegator.execute(st -> st.execute(""));
        });
  }

  static class ExecuteStatement extends StatementAdaptor {
    final boolean result;

    ExecuteStatement(boolean result) {
      this.result = result;
    }

    @Override
    public boolean execute(String sql) throws SQLException {
      return result;
    }
  }
}
