package ua.yandex.shad.stream;

import ua.yandex.shad.collections.DynamicArray;
import ua.yandex.shad.function.IntBinaryOperator;
import ua.yandex.shad.function.IntConsumer;
import ua.yandex.shad.function.IntPredicate;
import ua.yandex.shad.function.IntToIntStreamFunction;
import ua.yandex.shad.function.IntUnaryOperator;

public class AsIntStream implements IntStream {
    private DynamicArray<Integer> values;

    private AsIntStream(DynamicArray<Integer> values) {
        this.values = values;
    }

    public static IntStream of(int... values) {
        DynamicArray<Integer> newValues = new DynamicArray<>();
        for (int value : values) {
            newValues.add(value);
        }
        return new AsIntStream(newValues);
    }

    @Override
    public double average() {
        long sum = 0;
        for (int value : values) {
            sum += value;
        }
        return sum * 1.0 / Math.max(values.size(), 1);
    }

    @Override
    public int max() {
        int max = Integer.MIN_VALUE;
        for (int value : values) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    @Override
    public int min() {
        int min = Integer.MAX_VALUE;
        for (int value : values) {
            if (value < min) {
                min = value;
            }
        }
        return min;
    }

    @Override
    public long count() {
        return values.size();
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        DynamicArray<Integer> filtered = new DynamicArray<>();
        for (int value : values) {
            if (predicate.test(value)) {
                filtered.add(value);
            }
        }
        return new AsIntStream(filtered);
    }

    @Override
    public void forEach(IntConsumer action) {
        for (int value : values) {
            action.accept(value);
        }
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        DynamicArray<Integer> newValues = new DynamicArray<>();
        for (int i = 0; i < values.size(); ++i) {
            newValues.add(mapper.apply(values.get(i)));
        }
        return new AsIntStream(newValues);
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        int result = identity;
        for (int value : values) {
            result = op.apply(result, value);
        }
        return result;
    }

    @Override
    public int sum() {
        return reduce(0, (x, y) -> x + y);
    }

    @Override
    public int[] toArray() {
        int[] array = new int[values.size()];
        for (int i = 0; i < values.size(); ++i) {
            array[i] = values.get(i);
        }
        return array;
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        DynamicArray<Integer> newValues = new DynamicArray<>();
        for (int value : values) {
            for (int newValue : func.applyAsIntStream(value).toArray()) {
                newValues.add(newValue);
            }
        }
        return new AsIntStream(newValues);
    }

}
