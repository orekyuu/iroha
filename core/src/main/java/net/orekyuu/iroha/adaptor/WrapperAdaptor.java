package net.orekyuu.iroha.adaptor;

import java.sql.SQLException;
import java.sql.Wrapper;

public class WrapperAdaptor implements Wrapper {
    @Override
    @SuppressWarnings("unchecked")
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (iface.isInstance(this)) {
            return (T) this;
        }
        throw new SQLException("[ "+getClass().getName()+"] cannot be unwrapped as ["+iface.getName()+"]");
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return iface.isInstance(this);
    }
}
