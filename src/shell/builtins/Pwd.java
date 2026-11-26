package shell.builtins;

import shell.Shell;

import java.util.List;

/** pwd: prints the current directory. */
public class Pwd implements Builtin {
    @Override
    public int run(Shell shell, List<String> args) {
        System.out.println(shell.getCurrentDir());
        return 0;
    }

    @Override
    public String description() { return "pwd           print the current directory"; }
}
