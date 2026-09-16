class Solution {
    public int numberOfSets(int n, int k) {
        final long MOD = 1000000007L;

        int m = n + k - 1;
        long[] dp = new long[2 * k + 1];

        dp[0] = 1;

        for (int i = 1; i <= m; i++) {
            for (int j = Math.min(i, 2 * k); j >= 1; j--) {
                dp[j] = (dp[j] + dp[j - 1]) % MOD;
            }
        }

        return (int) dp[2 * k];
    }
}