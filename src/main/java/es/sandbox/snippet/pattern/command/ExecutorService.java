package es.sandbox.snippet.pattern.command;

import java.util.Objects;
import java.util.Optional;

/**
 * Created by jeslopalo on 24/9/17.
 */
public class ExecutorService {

    public <T> Optional<T> execute(Command<T> command) {
        Objects.requireNonNull(command);
        return Objects.requireNonNull(command.execute());
    }

    public <R, T> Optional<R> execute(TargetedCommand<R, T> command, T target) {
        Objects.requireNonNull(command);
        return Objects.requireNonNull(command.executeOn(target));
    }
}
