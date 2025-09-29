package annoyingstudyterm.commands;

import java.util.Arrays;

import annoyingstudyterm.question.Question;
import annoyingstudyterm.question.QuestionRepository;

class Add extends Command {
    @Override
    protected void Execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Unable to proceeed. One parameter missing, use \"--help add\"  to see the manual.");
            return;
        }
        switch(args[0]) {
            case "question":
                QuestionRepository.add(Question.prompt());
                break;
            default:
                System.out.println("Unknown parameter.");
                break;
        }
    }
}