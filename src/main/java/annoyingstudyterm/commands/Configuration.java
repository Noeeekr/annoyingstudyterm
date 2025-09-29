package annoyingstudyterm.commands;

class Configuration extends Command {
    @Override
    protected void Execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Unable to proceed. Missing 1 parameter. Try using \"--help configuration\" to see the manual");
        }
        switch(args[0]) {
            case "set":
                System.out.println("Using set command to override default configuration.");
            default:
                System.out.println("Unable to proceed. Unknown command.");
        }
    }
}