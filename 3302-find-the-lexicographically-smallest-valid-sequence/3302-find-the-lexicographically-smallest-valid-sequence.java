class Solution {
    public int[] validSequence(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();

        /*
         * exact[i] = maximum number of characters from the suffix
         * of word2 that can be matched exactly using word1[i...].
         *
         * almost[i] = maximum number of characters from the suffix
         * of word2 that can be matched using at most one mismatch.
         */
        int[] exact = new int[m + 1];
        int[] almost = new int[m + 1];

        // Build exact[] from right to left
        for (int i = m - 1; i >= 0; i--) {
            int matched = exact[i + 1];

            if (matched < n &&
                word1.charAt(i) == word2.charAt(n - 1 - matched)) {
                exact[i] = matched + 1;
            } else {
                exact[i] = matched;
            }
        }

        // Build almost[] from right to left
        for (int i = m - 1; i >= 0; i--) {
            int best = almost[i + 1];

            // Option 1: match word1[i] exactly
            int matchedAlmost = almost[i + 1];

            if (matchedAlmost < n &&
                word1.charAt(i) == word2.charAt(n - 1 - matchedAlmost)) {
                best = Math.max(best, matchedAlmost + 1);
            }

            // Option 2: use our one mismatch at word1[i]
            int matchedExact = exact[i + 1];

            if (matchedExact < n &&
                word1.charAt(i) != word2.charAt(n - 1 - matchedExact)) {
                best = Math.max(best, matchedExact + 1);
            }

            almost[i] = best;
        }

        int[] ans = new int[n];

        int prev = 0;
        boolean usedMismatch = false;

        for (int j = 0; j < n; j++) {
            boolean found = false;

            for (int i = prev; i < m; i++) {
                boolean same = word1.charAt(i) == word2.charAt(j);

                int remaining = n - j - 1;

                if (usedMismatch) {
                    // No mismatch left.
                    // Current character must match exactly.
                    if (same && exact[i + 1] >= remaining) {
                        ans[j] = i;
                        prev = i + 1;
                        found = true;
                        break;
                    }
                } else {
                    if (same) {
                        // Don't use mismatch here.
                        // Remaining part may use one mismatch.
                        if (almost[i + 1] >= remaining) {
                            ans[j] = i;
                            prev = i + 1;
                            found = true;
                            break;
                        }
                    } else {
                        // Use our one mismatch here.
                        // Remaining part must be exact.
                        if (exact[i + 1] >= remaining) {
                            ans[j] = i;
                            prev = i + 1;
                            usedMismatch = true;
                            found = true;
                            break;
                        }
                    }
                }
            }

            if (!found) {
                return new int[0];
            }
        }

        return ans;
    }
}