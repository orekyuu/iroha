package net.orekyuu.iroha.datasource;

import net.orekyuu.iroha.adaptor.DataSourceAdaptor;
import net.orekyuu.iroha.selector.DataSourceSelector;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IrohaDataSource extends DataSourceAdaptor {
    private final DataSourceSelector selector;

    public IrohaDataSource(DataSourceSelector selector) {
        this.selector = selector;
    }

    @Override
    public Connection getConnection() throws SQLException {
        List<Connection> connections = new ArrayList<>();
        List<DataSource> selectedDataSource = selector.select();
        for (DataSource dataSource : selectedDataSource) {
            connections.add(dataSource.getConnection());
        }
        return new IrohaConnection(connections);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        List<Connection> connections = new ArrayList<>();
        List<DataSource> selectedDataSource = selector.select();
        for (DataSource dataSource : selectedDataSource) {
            connections.add(dataSource.getConnection(username, password));
        }
        return new IrohaConnection(connections);
    }
}
