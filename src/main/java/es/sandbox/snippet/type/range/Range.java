package es.sandbox.snippet.type.range;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * It can be:
 * - Completely bounded: [a,b]
 * - Partially bounded: [a, -), (-, b]
 * - Always inclusive
 *
 * @param <T>
 */
public final class Range<T extends Comparable<T>> {

    private Optional<T> from;
    private Optional<T> to;

    private Range(Optional<T> from, Optional<T> to) {
        this.from = from;
        this.to = to;
    }

    public static final <T extends Comparable<T>> Range<T> between(T from, T to) {
        Objects.requireNonNull(from, "Range start may not be null");
        Objects.requireNonNull(to, "Range end may not be null");
        return new Range<>(Optional.of(from), Optional.of(to));
    }

    public static final <T extends Comparable<T>> Range<T> from(T from) {
        Objects.requireNonNull(from, "Range start may not be null");
        return new Range<>(Optional.of(from), Optional.empty());
    }

    public static final <T extends Comparable<T>> Range<T> to(T to) {
        Objects.requireNonNull(to, "Range end may not be null");
        return new Range<>(Optional.empty(), Optional.of(to));
    }

    Optional<T> from() {
        return this.from;
    }

    Optional<T> to() {
        return this.to;
    }

    public void ifLeftBoundary(Consumer<T> consumer) {
        Objects.requireNonNull(consumer);
        this.from.ifPresent(consumer);
    }

    public void ifRightBoundary(Consumer<T> consumer) {
        Objects.requireNonNull(consumer);
        this.to.ifPresent(consumer);
    }

    @Override
    public String toString() {
        return String.format("[%s, %s]",
            this.from.map(Object::toString).orElse("-"),
            this.to.map(Object::toString).orElse("-"));
    }
}
