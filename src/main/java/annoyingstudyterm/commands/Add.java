package annoyingstudyterm.commands;

import annoyingstudyterm.question.Question;
import annoyingstudyterm.question.QuestionRepository;

class Add extends Command {
    @Override
    protected void onCommand(String[] args) {
        System.out.println("Unable to proceed. One parameter missing, use \"--help add\"  to see the manual.");
    }

    @Override
    protected void onNextCommand(String[] args) {
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