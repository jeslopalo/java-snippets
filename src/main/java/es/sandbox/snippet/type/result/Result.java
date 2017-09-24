package es.sandbox.snippet.type.result;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * - Capture executable exceptions
 * - Holds the executable result
 * - Register executable events
 */
public final class Result<T, E> {

    private T result;

    private final EventLogger<E> eventLogger;
    private Optional<? extends Throwable> exception;

    private Result(Executable<T, E> executable) {
        this.eventLogger = new EventLogger<>();
        this.exception = Optional.empty();
        execute(executable);
    }

    private final void execute(Executable<T, E> executable) {
        Objects.requireNonNull(executable, "Executable may not be null");
        try {
            this.result = executable.execute(this.eventLogger);
        } catch (Throwable e) {
            this.exception = Optional.of(e);
        }
    }

    public static <T, E> Result<T, E> empty() {
        return of(events -> null);
    }

    public static <T, E> Result<T, E> of(Executable<T, E> executable) {
        return new Result<>(executable);
    }

    public boolean isPresent() {
        return this.result != null;
    }

    public void ifPresent(Consumer<? super T> consumer) {
        if (isPresent()) {
            consumer.accept(this.result);
        }
    }

    public boolean isFailed() {
        return this.exception.isPresent();
    }

    public void ifFailed(Consumer<? super Throwable> consumer) {
        this.exception.ifPresent(consumer);
    }

    public Result forEachEvent(Consumer<E> consumer) {
        Objects.requireNonNull(consumer);
        if (!isFailed()) {
            this.eventLogger.stream().forEach(consumer);
        }
        return this;
    }

    public T getOrThrows() throws RuntimeException {
        if (isFailed()) {
            return throwException();
        }
        return this.result;
    }

    private T throwException() {
        throw this.exception
            .map(throwable -> {
                if (throwable instanceof RuntimeException) {
                    return (RuntimeException) throwable;
                }
                return new RuntimeException(throwable);
            }).orElse(new IllegalArgumentException(""));
    }

    public Optional<? extends Throwable> getThrowable() {
        return this.exception;
    }

    @Override
    public String toString() {
        if (isFailed()) {
            return String.format("Result[exception=%s]", this.exception.get());
        }
        return this.result == null
            ? String.format("Result[empty, %s]", this.eventLogger)
            : String.format("Result[%s, %s]", this.result, this.eventLogger);
    }

    public static final class EventLogger<T> {

        private final List<T> events;

        private EventLogger() {
            this.events = new ArrayList<>();
        }

        private void copy(EventLogger<T> eventLogger) {
            eventLogger.stream().forEach(this::log);
        }

        public void log(T event) {
            this.events.add(event);
        }

        private Stream<T> stream() {
            return this.events.stream();
        }

        @Override
        public String toString() {
            return String.format("{events=%s}", this.events);
        }
    }

    @FunctionalInterface
    public interface Executable<T, E> {

        T execute(EventLogger<E> events);
    }
}
