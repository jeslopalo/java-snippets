package es.sandbox.snippet.pattern.command;

import java.util.Objects;
import java.util.Optional;

/**
 * Created by jeslopalo on 25/9/17.
 */
public class TargetedCommandExecutor<T> {

    private T target;

    public TargetedCommandExecutor(T target) {
        this.target = target;
    }

    public <R> Optional<R> execute(TargetedCommand<R, T> command) {
        Objects.requireNonNull(command);
        return Objects.requireNonNull(command.executeOn(this.target));
    }
}
