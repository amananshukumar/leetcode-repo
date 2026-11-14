
class Solution {
    public int minimumSumSubarray(List<Integer> nums, int l, int r) {
        int n = nums.size();
        int ans = Integer.MAX_VALUE;

        for (int len = l; len <= r; len++) {
            int sum = 0;

            // Calculate the first window
            for (int i = 0; i < len; i++) {
                sum += nums.get(i);
            }

            if (sum > 0) {
                ans = Math.min(ans, sum);
            }

            // Slide the window
            for (int i = len; i < n; i++) {
                sum -= nums.get(i - len);
                sum += nums.get(i);

                if (sum > 0) {
                    ans = Math.min(ans, sum);
                }
            }
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}
