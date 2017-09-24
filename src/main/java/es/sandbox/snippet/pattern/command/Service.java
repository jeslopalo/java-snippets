package es.sandbox.snippet.pattern.command;

/**
 * Created by jeslopalo on 24/9/17.
 */
public class Service {

    private boolean active;

    private Service(boolean active) {
        this.active = active;
    }

    public static Service off() {
        return new Service(false);
    }

    public static Service on() {
        return new Service(true);
    }

    public boolean isActive() {
        return this.active;
    }

    public void toggle() {
        this.active = !this.active;
    }
}
