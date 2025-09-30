package annoyingstudyterm.configuration;


public class GlobalConfiguration {
    // Might come from a designed file that is run at build time to parse these values from a single instance of truth
    public static final String applicationName = "annoyingstudyterm";
    public static final String installationFolder = String.format("/usr/local/lib/%s.jar", applicationName);
    public static final String configurationFile = String.format("%s/.config/%s/configuration.json", System.getenv("HOME"), applicationName);
}