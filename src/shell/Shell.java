package shell;

import shell.builtins.Builtin;
import shell.builtins.Builtins;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.List;

/**
 * The shell itself: the read → parse → execute loop (REPL).
 *
 * One round of the loop:
 *   1. print the prompt and read one line,
 *   2. split the line into words (Parser),
 *   3. run the first word as a builtin (cd, echo, …) or as a program found in $PATH (ExternalCommand).
 */
public class Shell {
    /** Current working directory. Java can't change the process's own directory, so the shell keeps its own. */
    private Path currentDir = Path.of(System.getProperty("user.dir"));
    /** Set to false by the `exit` builtin to leave the loop. */
    private boolean running = true;
    /** Exit code of the last command (0 = success), like $? in bash. */
    private int lastExitCode = 0;

    private final Builtins builtins = new Builtins();

    /** Runs the loop until `exit` or end of input (Ctrl+D). */
    public void run() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (running) {
            System.out.print(prompt());
            System.out.flush();

            String line;
            try {
                line = in.readLine();
            } catch (IOException e) {
                System.err.println("read error: " + e.getMessage());
                break;
            }
            if (line == null) { // Ctrl+D
                System.out.println();
                break;
            }
            execute(line);
        }
    }

    /** Parses one input line and runs it. */
    void execute(String line) {
        List<String> words = Parser.parse(line);
        if (words.isEmpty()) return;

        String name = words.get(0);
        List<String> args = words.subList(1, words.size());

        Builtin builtin = builtins.get(name);
        if (builtin != null) {
            lastExitCode = builtin.run(this, args);
        } else {
            lastExitCode = ExternalCommand.run(this, words);
        }
        // TODO: pipes (a | b), redirection (> file, < file), && and ||
    }

    /** The text in front of the cursor, e.g. "~/Shell $ ". */
    String prompt() {
        // TODO: make it configurable, show the exit code of the last command in colour, …
        String home = System.getProperty("user.home");
        String dir = currentDir.toString();
        if (dir.startsWith(home)) dir = "~" + dir.substring(home.length());
        return dir + " $ ";
    }

    // ---------- State that builtins and external commands need ----------
    public Path getCurrentDir() { return currentDir; }
    public void setCurrentDir(Path dir) { currentDir = dir; }
    public void stop() { running = false; }
    public int getLastExitCode() { return lastExitCode; }
    public Builtins getBuiltins() { return builtins; }
}
