package net.orekyuu.iroha.delegate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import net.orekyuu.iroha.mock.StatementAdaptor;
import org.junit.jupiter.api.Test;

class StatementSettingsDelegatorTest {

  @Test
  void empty() throws SQLException {
    var delegator = new StatementSettingsDelegator<>(List.of());
    assertDoesNotThrow(
        () -> {
          delegator.set(st -> st.setFetchSize(0));
        });
    assertThrows(
        SQLException.class,
        () -> {
          delegator.get(Statement::getFetchSize);
        },
        "No statements found");
  }

  @Test
  void setAll() throws SQLException {
    var statement1 = new FetchSizeStatement(1);
    var statement2 = new FetchSizeStatement(2);
    var delegator = new StatementSettingsDelegator<>(List.of(statement1, statement2));
    assertDoesNotThrow(
        () -> {
          delegator.set(st -> st.setFetchSize(10));
        });
    assertThat(statement1.fetchSize).isEqualTo(10);
    assertThat(statement2.fetchSize).isEqualTo(10);
  }

  @Test
  void getFirst() throws SQLException {
    var statement1 = new FetchSizeStatement(1);
    var statement2 = new FetchSizeStatement(2);
    var delegator = new StatementSettingsDelegator<>(List.of(statement1, statement2));
    var result = delegator.get(st -> st.fetchSize);
    assertThat(result).isEqualTo(1);
  }

  static class FetchSizeStatement extends StatementAdaptor {
    int fetchSize;

    public FetchSizeStatement(int fetchSize) {
      this.fetchSize = fetchSize;
    }

    @Override
    public void setFetchSize(int fetchSize) {
      this.fetchSize = fetchSize;
    }

    @Override
    public int getFetchSize() {
      return fetchSize;
    }
  }
}
