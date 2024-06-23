package net.orekyuu.iroha;

public interface ThrowableFunction<T, R, E extends Throwable> {
    public R apply(T t) throws E;
}
