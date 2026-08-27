class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int n = s.length();

        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        // Try to make target greater starting from the right.
        for (int i = n - 1; i >= 0; i--) {

            // Characters available for positions i...n-1
            int[] remaining = freq.clone();

            // Use target[0...i-1] exactly.
            boolean possible = true;

            for (int j = 0; j < i; j++) {
                int idx = target.charAt(j) - 'a';

                if (remaining[idx] == 0) {
                    possible = false;
                    break;
                }

                remaining[idx]--;
            }

            if (!possible) {
                continue;
            }

            // Find the smallest character > target[i]
            int targetChar = target.charAt(i) - 'a';

            for (int c = targetChar + 1; c < 26; c++) {

                if (remaining[c] > 0) {

                    StringBuilder ans = new StringBuilder();

                    // Prefix equal to target
                    for (int j = 0; j < i; j++) {
                        ans.append(target.charAt(j));
                    }

                    // Make it strictly greater here
                    ans.append((char) ('a' + c));
                    remaining[c]--;

                    // Put remaining characters in sorted order
                    for (int x = 0; x < 26; x++) {
                        while (remaining[x] > 0) {
                            ans.append((char) ('a' + x));
                            remaining[x]--;
                        }
                    }

                    return ans.toString();
                }
            }
        }

        return "";
    }
}