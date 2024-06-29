package net.orekyuu.iroha;

import java.util.List;
import javax.sql.DataSource;

public interface DataSourceSelector {

  List<DataSource> select();
}
