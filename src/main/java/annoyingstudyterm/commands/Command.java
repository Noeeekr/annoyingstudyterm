package annoyingstudyterm.commands;

abstract class Command {
    protected void handle(String[] args) {
        if (args.length == 0) {
            this.onCommand(args);
            return;
        }
        if (args[0].startsWith("--")) {
            this.onCommand(args);
            return;
        }
        this.onNextCommand(args);
    };
    // Only executes if there's at least one argument
    abstract protected void onNextCommand(String[] args);
    abstract protected void onCommand(String[] args);
}