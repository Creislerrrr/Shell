package shell.builtins;

import shell.Shell;

import java.util.List;

/** help: lists the builtin commands. */
public class Help implements Builtin {
    @Override
    public int run(Shell shell, List<String> args) {
        System.out.println("Builtin commands:");
        shell.getBuiltins().all().values().forEach((builtin) -> System.out.println("  " + builtin.description()));
        System.out.println("Everything else is looked up in $PATH (ls, git, …).");
        return 0;
    }

    @Override
    public String description() { return "help          list the builtin commands"; }
}
