package shell;

import java.util.ArrayList;
import java.util.List;

/**
 * Splits an input line into words: `echo hello   world` → [echo, hello, world].
 *
 * Right now it only splits on whitespace. Good next steps:
 *   - TODO: single quotes  'a  b'  → one word, nothing inside is special
 *   - TODO: double quotes  "a  b"  → one word, but $VAR still works
 *   - TODO: backslash escapes  a\ b → one word
 *   - TODO: $VAR / $? expansion and ~ → home directory
 */
public class Parser {
    private Parser() { }

    /** Returns the words of the line (empty list for an empty line). */
    public static List<String> parse(String line) {
        List<String> words = new ArrayList<>();
        StringBuilder current = new StringBuilder();

        for (char c : line.toCharArray()) {
            if (Character.isWhitespace(c)) {
                // end of a word
                if (!current.isEmpty()) {
                    words.add(current.toString());
                    current.setLength(0);
                }
            } else {
                current.append(c);
            }
        }
        if (!current.isEmpty()) words.add(current.toString());
        return words;
    }
}
