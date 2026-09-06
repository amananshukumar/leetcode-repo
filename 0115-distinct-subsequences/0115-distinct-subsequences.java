class Solution {
    public int numDistinct(String s, String t) {
        int m = s.length();
        int n = t.length();

        // If target is longer than source, impossible to form
        if (n > m) {
            return 0;
        }

        // dp[j] stores the number of subsequences of s that match t[0...j-1]
        // Using int[] since the problem guarantees the final answer fits in a 32-bit signed integer
        int[] dp = new int[n + 1];
        
        // Base case: an empty string t can always be matched in 1 way
        dp[0] = 1;

        for (int i = 0; i < m; i++) {
            char cs = s.charAt(i);
            // Traverse backwards to avoid overwriting values needed from the previous iteration
            for (int j = n; j >= 1; j--) {
                if (cs == t.charAt(j - 1)) {
                    dp[j] += dp[j - 1];
                }
            }
        }

        return dp[n];
    }
}
