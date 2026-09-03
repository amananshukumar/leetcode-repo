class Solution {
    public int maxSubArray(int[] nums) {
        int currentSum = nums[0];
        int maxSum = nums[0];

        for (int i = 1; i < nums.length; i++) {
            // Either extend the existing subarray or start a new one from nums[i]
            currentSum = Math.max(nums[i], currentSum + nums[i]);
            // Track the maximum subarray sum found so far
            maxSum = Math.max(maxSum, currentSum);
        }

        return maxSum;
    }
}