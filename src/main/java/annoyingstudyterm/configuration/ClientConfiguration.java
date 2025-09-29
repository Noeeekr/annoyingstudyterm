package annoyingstudyterm.configuration;

import java.io.File;

import java.util.Scanner;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ClientConfiguration {
    /* Static fields */
    private static ClientConfiguration clientConfiguration;
    static {
        ClientConfiguration.clientConfiguration = ClientConfiguration.load();
    }
    
    /* Instance fields */
    public String questionsFilePath = String.format("%s/.local/%s/questions.json", System.getenv("HOME"), GlobalConfiguration.applicationName);

    /* Getters */
    public static ClientConfiguration getConfiguration() {
        return ClientConfiguration.clientConfiguration;
    }

    /* Deserialization */
    private static ClientConfiguration load() {
        try {
            /* Get configuration file content */
            String content = "";
            File configurationFile = new File(GlobalConfiguration.configurationFile);
            Scanner reader = new Scanner(configurationFile);
            while (reader.hasNextLine()) {
                content = content + reader.nextLine();
            }
            reader.close();

            /* Parse file content into configuration object */
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(GlobalConfiguration.configurationFile, ClientConfiguration.class);
        } catch(Exception e) {
            // Update: Check error type and return an specific error
            System.out.println("[WARNING] No experience configuration file was found. You can create a new configuration file using \"configuration clean\" command. Initializing with default configuration.");
            return new ClientConfiguration();
        }
    }

    /* Serialization */
    public static boolean save() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File configurationFile = new File(GlobalConfiguration.configurationFile);
            mapper.writeValue(configurationFile, ClientConfiguration.class);
        } catch(Exception e) {
            // Update: Check error type and return an specific error
            System.out.println("Failed to save client configuration.");
            return false;
        }
        return true;
    }
}
