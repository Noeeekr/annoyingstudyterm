package annoyingstudyterm.commands;

class Configuration extends Command {
    @Override
    protected void onCommand(String[] args) {
        System.out.println("Unable to proceed. Missing 1 parameter. Try using \"--help configuration\" to see the manual");
    }

    @Override
    protected void onNextCommand(String[] args) {
        switch(args[0]) {
            case "set":
                System.out.println("Using set command to override default configuration.");
            default:
                System.out.println("Unable to proceed. Unknown command.");
        }
    }
}