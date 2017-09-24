package es.sandbox.snippet.pattern.command;

import java.util.Optional;

/**
 * Created by jeslopalo on 24/9/17.
 */
public interface TargetedCommand<R, T> {

    Optional<R> executeOn(T target);
}
