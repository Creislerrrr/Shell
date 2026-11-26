package shell.builtins;

import shell.Shell;

import java.util.List;

/**
 * A command that the shell runs itself instead of starting a program.
 * Some commands MUST be builtins: `cd` and `exit` change the shell's own state, a child process couldn't do that.
 */
public interface Builtin {
    /** Runs the command. `args` are the words after the command name. Returns the exit code (0 = success). */
    int run(Shell shell, List<String> args);

    /** One line for `help`. */
    String description();
}
