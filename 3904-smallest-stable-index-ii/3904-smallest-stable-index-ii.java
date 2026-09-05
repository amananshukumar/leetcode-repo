class Solution {
    public int firstStableIndex(int[] nums, int k) {
        int n = nums.length;
        if (n == 0) return -1;

        // Step 1: Precompute suffix minimums
        int[] suffixMin = new int[n];
        suffixMin[n - 1] = nums[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            suffixMin[i] = Math.min(nums[i], suffixMin[i + 1]);
        }

        // Step 2: Traverse from left to right, maintaining prefix maximum
        int prefMax = nums[0];
        for (int i = 0; i < n; i++) {
            prefMax = Math.max(prefMax, nums[i]);
            if (prefMax - suffixMin[i] <= k) {
                return i;
            }
        }

        return -1;
    }
}