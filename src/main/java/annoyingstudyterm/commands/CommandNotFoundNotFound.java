package annoyingstudyterm.commands;

class CommandNotFound extends Command {
    @Override 
    protected void Execute(String[] args) {
        System.out.println("Command not found. Check the command list with --help argument");
    }
} 