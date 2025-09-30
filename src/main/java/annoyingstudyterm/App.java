package annoyingstudyterm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import annoyingstudyterm.commands.Handler;
public class App {
    static volatile boolean canExit = false;
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            while (!App.canExit) {}
        }));
        
        Handler.handle(args);
        App.canExit = true;
    }
}
