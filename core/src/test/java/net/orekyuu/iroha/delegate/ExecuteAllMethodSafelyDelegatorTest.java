package net.orekyuu.iroha.delegate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import net.orekyuu.iroha.mock.StatementAdaptor;
import org.junit.jupiter.api.Test;

class ExecuteAllMethodSafelyDelegatorTest {

  @Test
  void empty() throws SQLException {
    var delegator = new ExecuteAllMethodSafelyDelegator<Statement>(Collections.emptyList());
    assertDoesNotThrow(
        () -> {
          delegator.executeSafely(Statement::close);
        });
  }

  @Test
  void closeAll_whenNotThrow() throws SQLException {
    var st1 = new CloseStatement(() -> null);
    var st2 = new CloseStatement(() -> null);
    var delegator = new ExecuteAllMethodSafelyDelegator<>(List.of(st1, st2));
    assertDoesNotThrow(
        () -> {
          delegator.executeSafely(Statement::close);
        });
    assertThat(st1.callCount).isEqualTo(1);
    assertThat(st2.callCount).isEqualTo(1);
  }

  @Test
  void closeAll_whenThrow() throws SQLException {
    var st1 = new CloseStatement(() -> new SQLException(""));
    var st2 = new CloseStatement(() -> null);
    var delegator = new ExecuteAllMethodSafelyDelegator<>(List.of(st1, st2));
    assertThrows(
        SQLException.class,
        () -> {
          delegator.executeSafely(Statement::close);
        });
    assertThat(st1.callCount).isEqualTo(1);
    assertThat(st2.callCount).isEqualTo(1);
  }

  static class CloseStatement extends StatementAdaptor {
    Supplier<SQLException> exceptionSupplier;
    int callCount = 0;

    public CloseStatement(Supplier<SQLException> exceptionSupplier) {
      this.exceptionSupplier = exceptionSupplier;
    }

    @Override
    public void close() throws SQLException {
      callCount++;
      var exception = exceptionSupplier.get();
      if (exception != null) {
        throw exception;
      }
    }
  }
}
