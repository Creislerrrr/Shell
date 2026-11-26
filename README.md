# myShell

A small Unix shell written in Java, as a pet project.

## Run

```bash
./run.sh
```

Needs Java 17 or newer (no Maven/Gradle: `run.sh` compiles with `javac`).

## How it works

```
src/shell/
├── Main.java              starts the shell
├── Shell.java             the loop: prompt → read line → parse → run
├── Parser.java            splits a line into words
├── ExternalCommand.java   finds programs in $PATH and runs them
└── builtins/              commands the shell runs itself
    ├── Builtin.java       interface every builtin implements
    ├── Builtins.java      list of all builtins (register new ones here)
    └── Cd, Echo, Exit, Help, Pwd, Type
```

## What works

- prompt with the current directory, `Ctrl+D` to quit
- builtins: `cd`, `echo`, `exit`, `help`, `pwd`, `type`
- running any program from `$PATH` (`ls -la`, `git status`, …)

## Roadmap (ideas, roughly from easy to hard)

- [ ] quotes and escapes in the parser (`echo "a  b"`, `'…'`, `\ `)
- [ ] `~` and `$VAR` / `$?` expansion
- [ ] `cd -`, `echo -n`, `exit` with the right exit code
- [ ] output redirection: `>`, `>>`, `2>`; input `<`
- [ ] pipes: `ls | grep java`
- [ ] `&&`, `||`, `;`
- [ ] history (arrow keys) and tab completion
- [ ] Ctrl+C stops the running program, not the shell
- [ ] background jobs: `sleep 10 &`, `jobs`, `fg`
