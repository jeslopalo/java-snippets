package es.sandbox.snippet.pattern.command;

/**
 * Created by jeslopalo on 24/9/17.
 */
public class App {

    public static void main(String[] args) {

        ExecutorService executorService = new ExecutorService();

        RepeaterCommand repeaterCommand = new RepeaterCommand("hello word!");
        executorService.execute(repeaterCommand)
            .ifPresent(System.out::println);

        ServiceToggleCommand serviceToggleCommand = new ServiceToggleCommand();
        executorService.execute(serviceToggleCommand, Service.off())
            .ifPresent(System.out::println);
    }
}
