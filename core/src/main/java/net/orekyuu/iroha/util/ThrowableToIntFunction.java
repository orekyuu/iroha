package net.orekyuu.iroha.util;

public interface ThrowableToIntFunction<T, E extends Throwable> {
  int applyAsInt(T t) throws E;
}
