package annoyingstudyterm.question;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "displayFormat",
    visible = true
)
@JsonSubTypes(
    @JsonSubTypes.Type(value = MultipleAnswerQuestion.class, name = "MULTIPLE_ANSWER")
)
@JsonAutoDetect(
    fieldVisibility = Visibility.NONE
)
public abstract class Question {
    public static enum QuestionDisplayFormat {
        MULTIPLE_ANSWER
    }

    @JsonProperty("subject")
        public String subject;
    @JsonProperty("description")
        public String description;
    @JsonProperty("displayFormat")
        public QuestionDisplayFormat displayFormat;

    public Question(QuestionDisplayFormat displayFormat) {
        this.displayFormat = displayFormat;
        this.subject = Question.promptSubject();
        this.description = Question.promptDescription();
    }

    public Question(String subject, String description, QuestionDisplayFormat displayFormat) {
        this.subject = subject;
        this.description = description;
        this.displayFormat = displayFormat;
    }

    protected static String promptDescription() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Write the question description: ");
        String description = scanner.nextLine(); // nextLine to allow spaces
        return description;
    }

    protected static String promptSubject() {
        System.out.print("What's the question subject? ");
        Scanner scanner = new Scanner(System.in);
        String subject = scanner.nextLine();
        return subject;
    }

    public static Question prompt() {
        QuestionDisplayFormat format = Question.promptDisplayFormat();
        switch(format){
            case MULTIPLE_ANSWER:
            default:
                return new MultipleAnswerQuestion();
        }
    }
    public static QuestionDisplayFormat promptDisplayFormat() {
        System.out.println("--- QuestionHeader display format ");
        System.out.println("    [0] Multiple answer question");
        System.out.print("Choose the question display format: ");

        Scanner scanner = new Scanner(System.in);
        int retries = 0;
        while (retries < 3) {
            try {
                int format = scanner.nextInt();
                scanner.nextLine(); // clear newline

                switch (format) {
                    case 0:
                        System.out.println("Choosen option: Multiple answer format. ");
                        return QuestionDisplayFormat.MULTIPLE_ANSWER;
                    default:
                        System.out.println("Invalid choice. Please try again: ");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number.");
                scanner.nextLine(); // clear invalid input
            }
            retries++;
        }

        System.out.println("Too many failed attempts. Defaulting to MULTIPLE_ANSWER.");
        return QuestionDisplayFormat.MULTIPLE_ANSWER;
    }

    // Asks if user wants to redo. Returns a boolean with the result.
    protected static boolean handleFailedQuestion() {
        System.out.println("Invalid input. Failed to create question. Do you wish to try again? (y/n) ");
        try {
            int b = System.in.read();
            System.in.skip(System.in.available()); // flush leftover input
            return (char) b == 'y' || (char) b == 'Y';
        } catch (IOException e) {
            System.out.println("Failed to understand input.");
            return false;
        }
    }

    protected static void lockPrompt() {
        String text = "[ Answer prompt will be unlocked soon ]\n";
        int timePerLetter = 5000 / text.length();
        
        for (var i = 0; i < text.length(); i++) {
            try {
                Thread.sleep(timePerLetter);
                System.out.print(text.charAt(i));
            } catch(Exception e) {}
        }
    }
    // Executes the questions interactive resolution prompt. Returns a boolean indicating if the user got it right or not
    public abstract boolean execute();
    public abstract String getAnswer();
}