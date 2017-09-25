package es.sandbox.snippet.pattern.command;

import java.util.Objects;
import java.util.Optional;

/**
 * Created by jeslopalo on 24/9/17.
 */
public class CommandExecutor {

    public <R> Optional<R> execute(Command<R> command) {
        Objects.requireNonNull(command);
        return Objects.requireNonNull(command.execute());
    }
}
