class Solution {
    public int largestInteger(int[] nums, int k) {
        int n = nums.length;

        int[] count = new int[51];

        // Check every subarray of size k
        for (int i = 0; i <= n - k; i++) {

            // To avoid counting the same number twice
            // in the same subarray
            boolean[] seen = new boolean[51];

            for (int j = i; j < i + k; j++) {
                int x = nums[j];

                if (!seen[x]) {
                    seen[x] = true;
                    count[x]++;
                }
            }
        }

        // Find largest integer appearing in exactly one window
        for (int x = 50; x >= 0; x--) {
            if (count[x] == 1) {
                return x;
            }
        }

        return -1;
    }
}