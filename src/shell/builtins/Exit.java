package shell.builtins;

import shell.Shell;

import java.util.List;

/** exit [code]: leaves the shell. */
public class Exit implements Builtin {
    @Override
    public int run(Shell shell, List<String> args) {
        shell.stop();
        if (args.isEmpty()) return shell.getLastExitCode();
        try {
            return Integer.parseInt(args.get(0));
        } catch (NumberFormatException e) {
            System.err.println("exit: " + args.get(0) + ": numeric argument required");
            return 2;
        }
        // TODO: actually exit the JVM with this code (System.exit) after the loop ends
    }

    @Override
    public String description() { return "exit [code]   leave the shell"; }
}
