package annoyingstudyterm.commands;

class CommandNotFound extends Command {
    @Override
    protected void onCommand(String[] args) {
        System.out.println("Command not found. Check the command list with --help argument");
    }

    @Override
    protected void onNextCommand(String[] args) {
        System.out.println("Command not found. Check the command list with --help argument");
    }
}