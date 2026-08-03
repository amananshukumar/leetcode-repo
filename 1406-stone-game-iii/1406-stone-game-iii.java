class Solution {
    public String stoneGameIII(int[] stoneValue) {

        int n = stoneValue.length;
        int[] dp = new int[n + 1];

        for (int i = n - 1; i >= 0; i--) {

            int sum = 0;
            dp[i] = Integer.MIN_VALUE;

            // Take 1 stone
            sum += stoneValue[i];
            dp[i] = Math.max(dp[i], sum - dp[i + 1]);

            // Take 2 stones
            if (i + 1 < n) {
                sum += stoneValue[i + 1];
                dp[i] = Math.max(dp[i], sum - dp[i + 2]);
            }

            // Take 3 stones
            if (i + 2 < n) {
                sum += stoneValue[i + 2];
                dp[i] = Math.max(dp[i], sum - dp[i + 3]);
            }
        }

        if (dp[0] > 0)
            return "Alice";
        else if (dp[0] < 0)
            return "Bob";
        else
            return "Tie";
    }
}