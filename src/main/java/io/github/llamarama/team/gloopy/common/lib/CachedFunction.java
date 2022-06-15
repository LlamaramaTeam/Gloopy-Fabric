package io.github.llamarama.team.gloopy.common.lib;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class CachedFunction<T, U> implements Function<T, U> {

    private final Function<@NotNull T, @NotNull U> func;
    @Nullable
    private U value;

    private CachedFunction(Function<@NotNull T, @NotNull U> func) {
        this.func = func;
        this.value = null;
    }

    public static <T, U> CachedFunction<T, U> of(Function<T, U> func) {
        return new CachedFunction<>(func);
    }

    @NotNull
    @Override
    public U apply(T t) {
        if (value == null) {
            this.value = this.func.apply(t);
        }

        return this.value;
    }
}
