package annoyingstudyterm.commands;

class Help extends Command {
    @Override
    protected void onCommand(String[] args) {
        System.out.println("General help command");
    }

    @Override
    protected void onNextCommand(String[] args) {
        System.out.println("Specific help command for " + args[0]);
        System.out.println("Unable to proceed. Missing 1 parameter. Try using \"--help configuration\" to see the manual");
    }
}