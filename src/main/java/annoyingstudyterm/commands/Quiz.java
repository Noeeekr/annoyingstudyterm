package annoyingstudyterm.commands;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import annoyingstudyterm.configuration.ClientConfiguration;
import annoyingstudyterm.question.Question;
import annoyingstudyterm.question.QuestionList;
import annoyingstudyterm.question.QuestionRepository;

class Quiz extends Command {
    @Override
    protected void onCommand(String[] args) {
        LocalDate lastCompletionTime = ClientConfiguration.getConfiguration().lastCompletionTime;
        if (lastCompletionTime.plusDays(1).isAfter(LocalDate.now())) {
            if (args.length == 0 || !args[0].equals("--force")) {
                System.out.println("[WARNING] You have already completed a quiz in the last 24 hours. Good job! If you want to do it again please use the --force flag.");
                return;
            }
        }
        QuestionList list = new QuestionList(QuestionRepository.getAll());
        if (list.length() == 0) {
            handleNoQuestionsFound();
        }
        handleQuiz(list);
        ClientConfiguration.updateLastCompletionTime();
    }
    @Override
    protected void onNextCommand(String[] args) {
        this.onCommand(args);
    }
    private static void handleNoQuestionsFound() {
        System.out.println("[WARNING] No questions were found in the register, unable to start quiz. You can insert question with \"add\" command.");
        String input;
        do {
            System.out.print("[QUESTION] Do you wish to create a new question? (y/n) ");
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextLine();
            if (input.charAt(0) != 'y') {
                return;
            }
            QuestionRepository.add(Question.prompt());
        } while(input.charAt(0) == 'y');
    }

    private static void handleQuiz(QuestionList list) {
        List<Question> questions = list.getRandomN(10);
        System.out.println("");
        System.out.println("");
        for (var i = 0; i < questions.size(); i++) {
            System.out.printf("-------------( %d/%d )-------------\n", i + 1, questions.size());
            Question question = questions.get(i);
            System.out.printf("Subject: %s\n", question.subject);
            System.out.printf("Description: %s\n", question.description);
            question.execute();
        }
    }
}