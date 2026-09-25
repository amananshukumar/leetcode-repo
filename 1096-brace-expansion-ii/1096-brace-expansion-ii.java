
import java.util.*;

class Solution {

    int index = 0;

    public List<String> braceExpansionII(String expression) {
        Set<String> result = parse(expression);

        List<String> ans = new ArrayList<>(result);
        Collections.sort(ans);

        return ans;
    }

    // Parse an expression until '}' or end
    private Set<String> parse(String s) {

        Set<String> result = new HashSet<>();
        Set<String> current = new HashSet<>();
        current.add("");

        while (index < s.length() && s.charAt(index) != '}') {

            char ch = s.charAt(index);

            // Union
            if (ch == ',') {
                result.addAll(current);
                current.clear();
                current.add("");
                index++;
            }

            // Braced expression
            else if (ch == '{') {
                index++;

                Set<String> inside = parse(s);

                index++; // skip '}'

                current = concatenate(current, inside);
            }

            // Letter
            else {
                Set<String> letter = new HashSet<>();
                letter.add(String.valueOf(ch));

                current = concatenate(current, letter);
                index++;
            }
        }

        result.addAll(current);

        return result;
    }

    // Cartesian product + concatenation
    private Set<String> concatenate(Set<String> a, Set<String> b) {

        Set<String> result = new HashSet<>();

        for (String x : a) {
            for (String y : b) {
                result.add(x + y);
            }
        }

        return result;
    }
}

