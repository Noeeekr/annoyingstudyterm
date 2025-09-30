package annoyingstudyterm.commands;

import java.util.Arrays;

import annoyingstudyterm.question.QuestionRepository;

public class Remove extends Command {
    @Override
    protected void onCommand(String[] args) {
        System.out.println(
                "Unable to proceed. Missing 1 necessary argument. Use \"--help remove\" command to check the options.");
        return;
    }
    protected void onNextCommand(String[] args) {
        handleCommand(args);
    }
    
    private static void handleCommand(String[] args) {
        String[] nextArgs = Arrays.copyOfRange(args, 1, args.length);
        switch (args[0]) {
            case "question":
                Remove.removeQuestion(nextArgs);
                break;
            default:
                System.out.println("Unknown command. Check the \"--help remove\" command to check the options.");
                break;
        }
    }
    private static void removeQuestion(String[] args) {
        if (args.length == 0) {
            System.out.println("Unable to proceed. Missing 1 necessary argument. Please specify the number of the question to remove.");
            return;
        }
        int questionIndex;
        try {
            questionIndex = Integer.parseInt(args[0]) - 1;
        } catch (Exception e) {
            System.out.printf("Failed to remove question. Value %s is unexpected input.\n");
            return;
        }

        if (QuestionRepository.at(questionIndex) == null) {
            System.out.println("Failed to remove question. There's not question at given position.");
            return;
        };

        if (QuestionRepository.remove(questionIndex)) {
            System.out.println("Question successfully removed.");
        } else {
            System.out.println("Failed to remove question.");
        };
    };
}
