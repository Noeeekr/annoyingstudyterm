package annoyingstudyterm.commands;

class Help extends Command {
    @Override 
    protected void Execute(String[] args) {
        if (args.length == 0) {
            System.out.println("General help command");
            return;
        }
        System.out.println("Specific help command for " + args[0]);
    }
}