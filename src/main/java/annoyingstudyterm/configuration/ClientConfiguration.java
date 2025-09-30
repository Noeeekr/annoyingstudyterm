package annoyingstudyterm.configuration;

import java.io.File;
import java.time.LocalDate;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ClientConfiguration {
    /* Static fields */
    private static ClientConfiguration clientConfiguration;
    static {
        ClientConfiguration.clientConfiguration = ClientConfiguration.load();
    }

    /* Instance fields */
    public LocalDate lastCompletionTime = LocalDate.now().minusDays(2);
    public String questionsFilePath = String.format("%s/.local/%s/questions.json", System.getenv("HOME"),
            GlobalConfiguration.applicationName);

    /* Getters */
    public LocalDate getLastCompletionTime() {
        return this.lastCompletionTime;
    }

    public static ClientConfiguration getConfiguration() {
        return ClientConfiguration.clientConfiguration;
    }

    /* Setters */
    public void setLastCompletionTime(LocalDate time) {
        this.lastCompletionTime = time;
    }

    /* Functionality */
    public static void updateLastCompletionTime() {
        ClientConfiguration.clientConfiguration.lastCompletionTime = LocalDate.now();
        ClientConfiguration.save();
    }

    /* Deserialization */
    private static ClientConfiguration load() {
        File configurationFile = new File(GlobalConfiguration.configurationFile);
        if (configurationFile.exists()) {
            return ClientConfiguration.readConfigurationFile();
        } else {
            System.out.println("Experience configuration file not found. Creating a new empty file instead.");
            ClientConfiguration.writeConfigurationFile();
            return new ClientConfiguration();
        }
    }

    private static ClientConfiguration readConfigurationFile() {
        try {
            String fileContent = "";
            File file = new File(GlobalConfiguration.configurationFile);
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                fileContent = fileContent + reader.nextLine();
            }
            reader.close();
            /* Parse file content into configuration object */
            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
            return mapper.readValue(fileContent, ClientConfiguration.class);
        } catch (Exception e) {
            // Update: Check error type and return an specific error
            System.out.println(
                    "[WARNING] Failed to read user's configuration file. "+
                    "You can create a new configuration file using \"configuration clean\" command.\n"+
                    "[WARNING] Initializing with default configuration. " + e.getMessage());
            return new ClientConfiguration();
        }
    }

    private static void writeConfigurationFile() {
        try {
            File configurationFile = new File(GlobalConfiguration.configurationFile);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(configurationFile, new ClientConfiguration());
        } catch (Exception e) {
            // Update: Check error type and return an specific error
            System.out.println(
                    "[WARNING] Failed to create experience configuration file. You can create a new configuration file using \"configuration clean\" command. Initializing with default configuration.");
        }
    }

    /* Serialization */
    public static boolean save() {
        try {
            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
            File configurationFile = new File(GlobalConfiguration.configurationFile);
            mapper.writeValue(configurationFile, ClientConfiguration.getConfiguration());
        } catch (Exception e) {
            // Update: Check error type and return an specific error
            System.out.println("Failed to save client configuration." + e.getMessage());
            return false;
        }
        return true;
    }
}
