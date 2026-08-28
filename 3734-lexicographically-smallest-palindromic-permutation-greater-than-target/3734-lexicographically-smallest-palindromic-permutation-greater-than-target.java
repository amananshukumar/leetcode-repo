import java.util.Arrays;

class Solution {

    public String lexPalindromicPermutation(String s, String target) {
        int n = s.length();
        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        int odd = 0;
        int middle = -1;

        for (int i = 0; i < 26; i++) {
            if (freq[i] % 2 != 0) {
                odd++;
                middle = i;
            }
        }

        // Invalid input: more than one character has odd frequency
        if (odd > 1) {
            return "";
        }

        int[] halfFreq = new int[26];
        for (int i = 0; i < 26; i++) {
            halfFreq[i] = freq[i] / 2;
        }

        StringBuilder half = new StringBuilder();
        String result = dfs(0, n / 2, halfFreq, half, target, middle, n, false);

        return result == null ? "" : result;
    }

    private String dfs(
        int pos,
        int halfLen,
        int[] freq,
        StringBuilder half,
        String target,
        int middle,
        int n,
        boolean isGreater
    ) {
        if (pos == halfLen) {
            String fullPalin = buildPalindrome(half, middle, n);
            return (isGreater || fullPalin.compareTo(target) > 0) ? fullPalin : null;
        }

        int startChar = isGreater ? 0 : (target.charAt(pos) - 'a');

        for (int c = startChar; c < 26; c++) {
            if (freq[c] == 0) continue;

            boolean nextIsGreater = isGreater || (c > target.charAt(pos) - 'a');

            // Pruning: Check if the largest possible completion can beat target
            if (!nextIsGreater && !canExceedTarget(pos + 1, halfLen, freq, c, half, target, middle, n)) {
                continue;
            }

            freq[c]--;
            half.append((char) ('a' + c));

            String res = dfs(pos + 1, halfLen, freq, half, target, middle, n, nextIsGreater);
            if (res != null) {
                return res; // Greedy choice succeeds immediately
            }

            // Backtrack
            half.deleteCharAt(half.length() - 1);
            freq[c]++;
        }

        return null;
    }

    private boolean canExceedTarget(
        int nextPos,
        int halfLen,
        int[] freq,
        int chosenChar,
        StringBuilder half,
        String target,
        int middle,
        int n
    ) {
        // Construct the upper bound palindrome (largest possible extension)
        StringBuilder maxHalf = new StringBuilder(half);
        maxHalf.append((char) ('a' + chosenChar));

        int[] tempFreq = freq.clone();
        tempFreq[chosenChar]--;

        for (int i = 25; i >= 0; i--) {
            while (tempFreq[i] > 0) {
                maxHalf.append((char) ('a' + i));
                tempFreq[i]--;
            }
        }

        String maxPalin = buildPalindrome(maxHalf, middle, n);
        return maxPalin.compareTo(target) > 0;
    }

    private String buildPalindrome(StringBuilder half, int middle, int n) {
        StringBuilder sb = new StringBuilder(half);
        if (n % 2 == 1) {
            sb.append((char) ('a' + middle));
        }
        for (int i = half.length() - 1; i >= 0; i--) {
            sb.append(half.charAt(i));
        }
        return sb.toString();
    }
}