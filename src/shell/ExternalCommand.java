package shell;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

/**
 * Runs programs that are not builtins (ls, git, java, …).
 *
 * The program is looked up in the directories of $PATH, then started as a child process that uses
 * the same terminal (inheritIO), so its output appears directly and it can read keyboard input.
 */
public class ExternalCommand {
    private ExternalCommand() { }

    /** Starts the program words[0] with the other words as arguments and waits for it. Returns its exit code. */
    public static int run(Shell shell, List<String> words) {
        String name = words.get(0);
        Optional<Path> program = findInPath(name);
        if (program.isEmpty()) {
            System.err.println(name + ": command not found");
            return 127; // same code bash uses
        }

        ProcessBuilder builder = new ProcessBuilder(words)
                .directory(shell.getCurrentDir().toFile())
                .inheritIO();
        try {
            Process process = builder.start();
            return process.waitFor();
        } catch (IOException e) {
            System.err.println(name + ": " + e.getMessage());
            return 126;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return 130;
        }
        // TODO: Ctrl+C should stop the child process, not the shell
    }

    /**
     * Finds an executable file: a name with a "/" is taken as a path, otherwise every directory in $PATH is searched.
     */
    public static Optional<Path> findInPath(String name) {
        if (name.contains("/")) {
            Path path = Path.of(name);
            return Files.isExecutable(path) ? Optional.of(path) : Optional.empty();
        }
        String pathVar = System.getenv("PATH");
        if (pathVar == null) return Optional.empty();

        for (String dir : pathVar.split(File.pathSeparator)) {
            Path candidate = Path.of(dir, name);
            if (Files.isRegularFile(candidate) && Files.isExecutable(candidate)) return Optional.of(candidate);
        }
        return Optional.empty();
    }
}
