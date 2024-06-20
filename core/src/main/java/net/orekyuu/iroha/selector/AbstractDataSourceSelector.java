package net.orekyuu.iroha.selector;

import javax.sql.DataSource;
import java.util.List;

public abstract class AbstractDataSourceSelector<T> implements DataSourceSelector {

    private final ThreadLocal<T> valueHolder = new ThreadLocal<>();

    protected abstract List<DataSource> doSelect(T selectKey);

    public final void clear() {
        valueHolder.remove();
    }

    public final void setValue(T value) {
        valueHolder.set(value);
    }

    @Override
    public final List<DataSource> select() {
        T selectKey = valueHolder.get();
        return doSelect(selectKey);
    }
}
