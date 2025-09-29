package annoyingstudyterm.question;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MultipleAnswerQuestion extends Question {
    public List<String> alternatives;
    public int correctAlternativeIndex;

    @JsonCreator
    public MultipleAnswerQuestion(
        @JsonProperty("subject") String subject,
        @JsonProperty("description") String description,
        @JsonProperty("alternatives") List<String> alternatives,
        @JsonProperty("correctAlternativeIndex") int correctAlternativeIndex
    ) {
        super(subject, description, Question.QuestionDisplayFormat.MULTIPLE_ANSWER);
        this.alternatives = alternatives;
        this.correctAlternativeIndex = correctAlternativeIndex;
    }

    public MultipleAnswerQuestion() {
        super();
        this.alternatives = this.promptQuestionAlternatives();
        this.correctAlternativeIndex = this.promptCorrectAlternativeIndex();
    }

    private int promptCorrectAlternativeIndex() {
        System.out.print("What's the number of the correct alternative? ");
        do {
            Scanner scanner = new Scanner(System.in);
            int number = scanner.nextInt();
            if (number > this.alternatives.size() || number < 1) {
                System.out.printf("%d is not in the alternatives. Try again: \n", number);
            } else {
                return number - 1;
            }
        } while(true);
    }
    private List<String> promptQuestionAlternatives() {
        System.out.println("[WARNING] At least two alternatives are necessary for multiple answer format.");
        System.out.println("[INFO] You can write \"stop\" to end alternative prompt after the second prompt.");
        List<String> alternatives = new ArrayList<>();
        for (var i = 1; true; i++) {
            System.out.printf("    [%dº] Alternative answer: ", i);
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            // Guarantee that at least two alternatives are prompted
            if (i <= 2) {
                alternatives.add(input);
                continue;
            }

            if (input.equals("stop")) {
                break;
            }
            alternatives.add(input);
        }
        return alternatives;
    }
    @Override
    public boolean execute() {
        System.out.println("Answers:");
        for (var i = 0; i < this.alternatives.size(); i++) {
            System.out.printf("[%dª] %s\n",i + 1,this.alternatives.get(i));
        }
        Question.lockPrompt();
        System.out.print("What's the number of the correct answer? ");
        Scanner scanner = new Scanner(System.in);
        int answer = scanner.nextInt();

        if (answer - 1 == this.correctAlternativeIndex) {
            System.out.println("Congrats! Right answer");
            return true;
        }
        System.out.println("Wrong answer!! :(");
        return false;
    }
}
