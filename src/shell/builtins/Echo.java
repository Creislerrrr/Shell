package shell.builtins;

import shell.Shell;

import java.util.List;

/** echo words…: prints the words separated by one space. */
public class Echo implements Builtin {
    @Override
    public int run(Shell shell, List<String> args) {
        System.out.println(String.join(" ", args));
        return 0;
        // TODO: option -n (no newline at the end)
    }

    @Override
    public String description() { return "echo words…   print the words"; }
}
