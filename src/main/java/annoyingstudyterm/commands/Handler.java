package annoyingstudyterm.commands;

import java.util.Arrays;

public class Handler {
    public static void handle(String[] args) {
        if (args.length == 0) {
            System.out.println("Please input a command");
            return;
        }
        Handler.handleCommand(args);
    }

    private static void handleCommand(String[] args) {
        String[] nextArgs = Arrays.copyOfRange(args, 1, args.length); 
        switch(args[0]) {
            case "--help":
            case "-h":
            case "help":
                new Help().handle(nextArgs);
                break;
            case "quiz":
                new Quiz().handle(nextArgs);
                break;
            case "remove":
                new Remove().handle(nextArgs);
                break;
            case "add":
                new Add().handle(nextArgs);
                break;
            case "configuration":
                new Configuration().handle(nextArgs);
                break;
            case "list":
                new CommandList().handle(nextArgs);
                break;
            default:
                new CommandNotFound().handle(nextArgs);
                break;
        }
    }
}