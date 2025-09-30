package annoyingstudyterm.commands;

import java.util.Arrays;
import java.util.List;

import annoyingstudyterm.question.Question;
import annoyingstudyterm.question.QuestionRepository;

public class CommandList extends Command {
    @Override
    protected void onCommand(String[] args) {
        CommandList.displayQuestionList(args);
    }

    @Override
    protected void onNextCommand(String[] args) {
        CommandList.handleNextCommand(args);
    }
    
    private static void handleNextCommand(String[] args) {
        String[] nextArgs = Arrays.copyOfRange(args, 1, args.length);
        switch(args[0]) {
            case "--format":
                CommandList.displayFormatList(nextArgs);
                break;
        }
    }
    private static void displayQuestionList(String[] args) {
        if (QuestionRepository.length == 0) {
            System.out.println("No questions found.");
            return;
        }
        boolean showAnswers = false;
        for (String arg : args) {
            if (arg.equals("--show-answers")) {
                showAnswers = true;
            }
        }

        System.out.println("_______________\n");
        
        List<Question> questions = QuestionRepository.getAll();
        for (var i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            System.out.printf("<%d> Subject: %s \n", i + 1, question.subject);
            System.out.printf("<%d> Description: %s \n", i + 1, question.description);
            if (showAnswers) {
                System.out.printf("<%d> Answer: %s \n", i + 1,question.getAnswer());
            }
            System.out.println("_______________\n");
        }
    }

    private static void displayFormatList(String[] args) {
        if (args.length == 0) {
            System.out.println("Available question formats: ");
            System.out.println("> MULTIPLE ANSWER FORMAT (MAF)");
            System.out.println("\nYou can see more about these formats using the command \"list --format MAF\"");
            return;
        }
        switch(args[0]) {
            case "MAF":
                CommandList.displayMultipleAnswerFormat();
                return;
        }
    }
    private static void displayMultipleAnswerFormat() {
        System.out.println("Subject: Example");
        System.out.println("Description: Example of the question description");
        System.out.println("");
        System.out.println("    [1ª] First answer");
        System.out.println("    [2ª] Second answer");
        System.out.println("    [3ª] Third answer");
        System.out.println("    [4ª] Fourth answer");
        System.out.println("    [n...] N'th answer");
    }
}
