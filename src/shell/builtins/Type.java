package shell.builtins;

import shell.ExternalCommand;
import shell.Shell;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

/** type name…: says whether a name is a builtin or which program in $PATH it runs. */
public class Type implements Builtin {
    @Override
    public int run(Shell shell, List<String> args) {
        int exitCode = 0;
        for (String name : args) {
            if (shell.getBuiltins().get(name) != null) {
                System.out.println(name + " is a shell builtin");
                continue;
            }
            Optional<Path> program = ExternalCommand.findInPath(name);
            if (program.isPresent()) {
                System.out.println(name + " is " + program.get());
            } else {
                System.err.println(name + ": not found");
                exitCode = 1;
            }
        }
        return exitCode;
    }

    @Override
    public String description() { return "type name…    show what a command name runs"; }
}
