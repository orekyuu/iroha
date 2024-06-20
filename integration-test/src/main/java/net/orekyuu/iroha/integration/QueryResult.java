package net.orekyuu.iroha.integration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class QueryResult {
    private final ResultSet resultSet;

    public QueryResult(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public Stream<ResultSet> stream() {
        return StreamSupport.stream(new Spliterators.AbstractSpliterator<ResultSet>(Long.MAX_VALUE,Spliterator.ORDERED) {
            @Override
            public boolean tryAdvance(Consumer<? super ResultSet> action) {
                try {
                    if(!resultSet.next()) return false;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                action.accept(resultSet);
                return true;
            }
        }, false);
    }
}
