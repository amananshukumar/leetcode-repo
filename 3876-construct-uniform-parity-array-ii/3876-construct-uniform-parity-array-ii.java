class Solution {
    public boolean uniformArray(int[] nums1) {
        int minVal = Integer.MAX_VALUE;
        boolean hasOdd = false;

        for (int x : nums1) {
            minVal = Math.min(minVal, x);
            if (x % 2 != 0) {
                hasOdd = true;
            }
        }

        // If there are no odds, it's already all even.
        // Otherwise, the global minimum must be odd to make everything odd.
        return !hasOdd || (minVal % 2 != 0);
    }
}