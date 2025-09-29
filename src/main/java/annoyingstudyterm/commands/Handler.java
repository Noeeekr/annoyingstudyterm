package annoyingstudyterm.commands;

import java.util.Arrays;

public class Handler {
    public static void HandleCommand(String[] args) {
        if (args.length == 0) {
            System.out.println("Please input a command");
            return;
        }

        String[] nextArgs = Arrays.copyOfRange(args, 1, args.length); 
        switch(args[0]) {
            case "--help":
            case "-h":
            case "help":
                new Help().Execute(nextArgs);
                break;
            case "quiz":
                new Quiz().Execute(nextArgs);
                break;
            case "add":
                new Add().Execute(nextArgs);
                break;
            case "configuration":
                new Configuration().Execute(nextArgs);
                break;
            default:
                new CommandNotFound().Execute(nextArgs);
                break;
        }
    }
}