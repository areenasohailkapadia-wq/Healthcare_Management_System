package controller;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class IdGenerator {
    private IdGenerator() {}

    /**
     * Generates next ID like P001, A012, RX003 based on existing ids.
     * Prefix may include letters (e.g., "RX").
     */
    public static String next(String prefix, Collection<String> existingIds) {
        int max = 0;

        Pattern p = Pattern.compile("^" + Pattern.quote(prefix) + "(\\d+)$");
        for (String id : existingIds) {
            if (id == null) continue;
            Matcher m = p.matcher(id.trim());
            if (m.matches()) {
                try {
                    int n = Integer.parseInt(m.group(1));
                    if (n > max) max = n;
                } catch (Exception ignored) {}
            }
        }
        int next = max + 1;
        return prefix + String.format("%03d", next);
    }
}
