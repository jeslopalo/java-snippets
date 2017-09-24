package es.sandbox.snippet.pattern.command;

import java.util.Optional;

/**
 * Created by jeslopalo on 24/9/17.
 */
public class RepeaterCommand implements Command<String> {

    private final String message;

    public RepeaterCommand(String message) {
        this.message = message;
    }

    @Override
    public Optional<String> execute() {
        return Optional.ofNullable(this.message);
    }
}
