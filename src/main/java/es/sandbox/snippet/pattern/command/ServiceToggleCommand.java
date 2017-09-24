package es.sandbox.snippet.pattern.command;

import java.util.Optional;

/**
 * Created by jeslopalo on 24/9/17.
 */
public class ServiceToggleCommand implements TargetedCommand<Boolean, Service> {

    @Override
    public Optional<Boolean> executeOn(Service target) {
        target.toggle();
        return Optional.of(target.isActive());
    }
}
