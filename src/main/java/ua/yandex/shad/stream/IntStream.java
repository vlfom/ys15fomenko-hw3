package ua.yandex.shad.stream;

import ua.yandex.shad.function.IntBinaryOperator;
import ua.yandex.shad.function.IntConsumer;
import ua.yandex.shad.function.IntPredicate;
import ua.yandex.shad.function.IntToIntStreamFunction;
import ua.yandex.shad.function.IntUnaryOperator;

public interface IntStream {

    double average();

    int max();

    int min();

    IntStream flatMap(IntToIntStreamFunction func);

    long count();

    IntStream filter(IntPredicate predicate);

    void forEach(IntConsumer action);

    IntStream map(IntUnaryOperator mapper);

    int reduce(int identity, IntBinaryOperator op);

    int sum();

    int[] toArray();
}
