package shell.builtins;

import shell.Shell;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/** cd [dir]: changes the current directory (without argument: to the home directory). */
public class Cd implements Builtin {
    @Override
    public int run(Shell shell, List<String> args) {
        String target = args.isEmpty() ? System.getProperty("user.home") : args.get(0);
        // TODO: "cd ~/x", "cd -" (previous directory)

        // relative paths start from the shell's current directory; normalize() removes "." and ".."
        Path dir = shell.getCurrentDir().resolve(target).normalize();
        if (!Files.isDirectory(dir)) {
            System.err.println("cd: " + target + ": No such file or directory");
            return 1;
        }
        shell.setCurrentDir(dir);
        return 0;
    }

    @Override
    public String description() { return "cd [dir]      change directory"; }
}
