package annoyingstudyterm.question;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import annoyingstudyterm.configuration.ClientConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class QuestionRepository {
    private static List<Question> list;
    public static int length = 0;
    static {
        try {
            ObjectMapper mapper = new ObjectMapper();
            byte[] fileContent = Files.readAllBytes(Paths.get(ClientConfiguration.getConfiguration().questionsFilePath));
            QuestionRepository.list = mapper.readValue(fileContent, new TypeReference<List<Question>>(){});
            QuestionRepository.length = QuestionRepository.list.size();
        } catch(Exception e) {
            System.out.println("[ERROR] Failed to load user's question file." + e.getMessage());
            handleQuestionFileNotFound();
            QuestionRepository.list = new ArrayList<Question>();
        }
    }
    public static Question at(int index) {
        if (index > QuestionRepository.list.size() - 1) {
            return null;
        }
        return list.get(index);
    }
    public static List<Question> getAll() {
        return List.copyOf(QuestionRepository.list);
    }
    public static void add(Question question) {
        if (question == null) {
            return;
        }
        QuestionRepository.list.add(question);
        QuestionRepository.save();
        return;
    }
    private static void handleQuestionFileNotFound() {
        System.out.println("[WARNING] Failed to find user's question file. You can change the directory for the question file using the \"configuration\" command.");
        System.out.print("[WARNING] A question file is necessary to continue, do you wish to create a new empty one for this user? (y/n): ");
        byte[] input = new byte[4];
        try {
            System.in.read(input);
        } catch (IOException e) {
            System.out.println("Unable to properly read the given input. Failed to proceed.");
            System.exit(1);
        }
        char letter = (char)(input[0]);
        if (letter == 'y') {
            System.out.println("Attempting to create a new question file on path: " + ClientConfiguration.getConfiguration().questionsFilePath);
            createNewQuestionFile();
            return;
        }
    }
    private static boolean createNewQuestionFile() {
        boolean exists = true;
        try {
            File file = new File(ClientConfiguration.getConfiguration().questionsFilePath);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(file, new Question[0]);
        } catch(Exception e) {
            System.out.println("Failed to create new file: " + e.getMessage());
            return false;
        }
        if (exists) {
            System.out.println("Successfully created new file.");
            return true;
        }
        return true;
    }
    private static boolean save() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(ClientConfiguration.getConfiguration().questionsFilePath), QuestionRepository.list);
        } catch (Exception e) {
            System.out.println("Failed to save the following questions: [register not done]");
            return false;
        }
        return true;
    }
    public static boolean remove(int index) {
        if (QuestionRepository.list.remove(index) == null) {
            return false;
        }
        return QuestionRepository.save();
    }
}