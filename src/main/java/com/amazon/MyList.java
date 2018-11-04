package com.amazon;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

class MyList<T> {
    private final T[] data;

    MyList(T... data) {
        this.data = data;
    }
    T[] getData() {
        return data;
    }
    MyList<T> reverse() {
        int length = data.length;
        T[] reversed = (T[]) new Object[data.length];
        for (int i = 0; i <= length-1 ; i++) {
            reversed[i] = data[length - i - 1];
        }
        return new MyList<>(reversed);
    }
    public MyList<T> filter(Predicate<T> o) {
        if (null == o) throw new NullPointerException("Predicate is null!");
        T[] filtered = (T[]) new Object[data.length];
        int filteredIndex = 0;
        for (int i = 0; i < data.length ; i++) {
            if (o.test(data[i])) {
                filtered[filteredIndex] = data[i];
                filteredIndex++;
            }
        }
        T[] ints = Arrays.copyOf(filtered, filteredIndex);
        return new MyList(ints);
    }
    <R> MyList<R> map(Function<T,R> m) {
        R[] mapped = (R[]) new Object[data.length];
        for (int i = 0; i < data.length ; i++) {
            mapped[i] = m.apply(data[i]);
        }
        return new MyList(mapped);
    }
    <R> R foldLeft(R init, FoldFunction<T,R> m) {
        if (getData() == null || getData().length == 0)
            return init;
        for (T item: getData()) {
            init = m.apply(item, init);
        }
        return init;
    }

    /**
     * Holds the function to be applied using <code>{@link MyList}</code> fold action.
     */
    @FunctionalInterface
    public interface FoldFunction<T, U> {
        U apply(T a, U b);
    }
}