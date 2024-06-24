package net.orekyuu.iroha.delegate;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import net.orekyuu.iroha.mock.StatementAdaptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ExecuteUpdateDelegatorTest {

  @Test
  void empty() throws Exception {
    var delegator = new ExecuteUpdateDelegator<>(Collections.emptyList());
    assertThat(delegator.delegate(st -> st.executeUpdate(""))).isEqualTo(0);
  }

  @Test
  void reduceUpdateCount() throws Exception {
    var delegator =
        new ExecuteUpdateDelegator<>(
            List.of(new ExecuteUpdateStatement(1), new ExecuteUpdateStatement(2)));
    assertThat(delegator.delegate(st -> st.executeUpdate(""))).isEqualTo(3);
  }

  @Test
  void throwException() throws Exception {
    var delegator =
        new ExecuteUpdateDelegator<>(
            List.of(
                new StatementAdaptor() {
                  @Override
                  public int executeUpdate(String sql) throws SQLException {
                    throw new SQLException();
                  }
                }));

    Assertions.assertThrows(
        SQLException.class, () -> delegator.delegate(st -> st.executeUpdate("")));
  }

  static class ExecuteUpdateStatement extends StatementAdaptor {
    private int updateCount;

    public ExecuteUpdateStatement(int updateCount) {
      this.updateCount = updateCount;
    }

    @Override
    public int executeUpdate(String sql) throws SQLException {
      return updateCount;
    }
  }
}
