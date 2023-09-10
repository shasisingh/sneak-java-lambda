package nl.shashi.playground.sneak;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 Idea based on
 <a href="https://4comprehension.com/sneakily-throwing-exceptions-in-lambda-expressions-in-java/">4comprehension</a>
 */
public class Sneaky {

    public static <R, E extends Throwable> R sneak(SneakSupplier<R, E> supplier) {
        return sneaked(supplier).get();
    }

    public static <E extends Throwable> void sneak(SneakRunnable<E> runnable) {
        sneaked(runnable).run();
    }

    public static <T, R, E extends Throwable> Function<T, R> sneaked(SneakFunction<T, R, E> supplier) {
        return data -> {
            SneakFunction<T, R, RuntimeException> castedSupplier = (SneakFunction<T, R, RuntimeException>) supplier;
            return castedSupplier.apply(data);
        };
    }

    public static <R, E extends Throwable> Supplier<R> sneaked(SneakSupplier<R, E> supplier) {
        return () -> {
            SneakSupplier<R, RuntimeException> castedSupplier = (SneakSupplier<R, RuntimeException>) supplier;
            return castedSupplier.retrieve();
        };
    }

    public static <E extends Throwable> Runnable sneaked(SneakRunnable<E> runnable) {
        return () -> {
            SneakRunnable<RuntimeException> sneakRunnable = (SneakRunnable<RuntimeException>) runnable;
            sneakRunnable.run();
        };
    }

    public static <T, E extends Throwable> UnaryOperator<T> sneaked(SneakUnaryOperator<T, E> unaryOperator) {
        return data -> {
            SneakUnaryOperator<T, RuntimeException> castedSupplier = (SneakUnaryOperator<T, RuntimeException>) unaryOperator;
            return castedSupplier.apply(data);
        };
    }

    @FunctionalInterface
    public interface SneakSupplier<T, E extends Throwable> {
        T retrieve() throws E;
    }

    @FunctionalInterface
    public interface SneakFunction<T, R, E extends Throwable> {
        R apply(T p) throws E;
    }

    @FunctionalInterface
    public interface SneakRunnable<E extends Throwable> {
        void run() throws E;
    }

    @FunctionalInterface
    public interface SneakUnaryOperator<T, E extends Throwable> extends SneakFunction<T, T, E> { }
}
