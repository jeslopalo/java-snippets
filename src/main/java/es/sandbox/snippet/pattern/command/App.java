package es.sandbox.snippet.pattern.command;

/**
 * Created by jeslopalo on 24/9/17.
 */
public class App {

    public static void main(String[] args) {

        CommandExecutor commandExecutor = new CommandExecutor();
        RepeaterCommand repeaterCommand = new RepeaterCommand("hello word!");
        commandExecutor.execute(repeaterCommand)
            .ifPresent(System.out::println);

        TargetedCommandExecutor targetedCommandExecutor = new TargetedCommandExecutor(Service.off());
        ServiceToggleCommand serviceToggleCommand = new ServiceToggleCommand();
        targetedCommandExecutor.execute(serviceToggleCommand)
            .ifPresent(System.out::println);
    }
}
