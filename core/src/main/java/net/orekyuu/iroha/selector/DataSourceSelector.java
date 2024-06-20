package net.orekyuu.iroha.selector;

import javax.sql.DataSource;
import java.util.List;

public interface DataSourceSelector {

    List<DataSource> select();
}
