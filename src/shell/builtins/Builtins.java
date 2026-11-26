package shell.builtins;

import java.util.Map;
import java.util.TreeMap;

/**
 * All builtin commands by name. To add a new one: write a class that implements Builtin and register it here.
 */
public class Builtins {
    private final Map<String, Builtin> commands = new TreeMap<>(); // sorted, so `help` lists them alphabetically

    public Builtins() {
        commands.put("exit", new Exit());
        commands.put("echo", new Echo());
        commands.put("pwd", new Pwd());
        commands.put("cd", new Cd());
        commands.put("type", new Type());
        commands.put("help", new Help());
        // TODO: history, export, alias, …
    }

    /** The builtin with this name, or null if there is none. */
    public Builtin get(String name) { return commands.get(name); }

    public Map<String, Builtin> all() { return commands; }
}
