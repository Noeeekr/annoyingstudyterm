package annoyingstudyterm.commands;

import java.util.List;
import java.util.Scanner;

import annoyingstudyterm.question.Question;
import annoyingstudyterm.question.QuestionList;
import annoyingstudyterm.question.QuestionRepository;

class Quiz extends Command {
    @Override
    protected void onCommand(String[] args) {
        QuestionList list = new QuestionList(QuestionRepository.getAll());
        if (list.length() == 0) {
            System.out.println("Unable to start quiz. Failed to get questionList.");
            return;
        }
        if (list.length() == 0) {
            handleNoQuestionsFound();
        }
        handleQuiz(list);
    }
    @Override
    protected void onNextCommand(String[] args) {
        this.onCommand(args);
    }
    private void handleNoQuestionsFound() {
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

    private void handleQuiz(QuestionList list) {
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