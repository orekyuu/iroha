package net.orekyuu.iroha.integration.scenario;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import javax.sql.DataSource;

public class DatabaseScript {

  private static final String SCHEMA_PATH = "schema.sql";
  private static final String TRUNCATE_PATH = "truncate.sql";

  private String getSqlFileContent(String filename) {
    try (InputStream in = DatabaseScript.class.getResourceAsStream("/" + filename)) {
      if (in == null) {
        throw new IOException("Script file not found: " + filename);
      }
      return new String(in.readAllBytes(), StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  private void executeSqlFile(String filename, DataSource dataSource) {
    var script = getSqlFileContent(filename);
    try (var conn = dataSource.getConnection();
        var statement = conn.createStatement()) {
      statement.execute(script);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void setupDatabase(DataSource dataSource) {
    executeSqlFile(SCHEMA_PATH, dataSource);
  }

  public void truncateAllTables(DataSource dataSource) {
    executeSqlFile(TRUNCATE_PATH, dataSource);
  }
}
