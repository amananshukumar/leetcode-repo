class Solution {
    public int maxProduct(int[] nums) {
        int maxProd = nums[0];
        int minProd = nums[0];
        int result = nums[0];

        for (int i = 1; i < nums.length; i++) {
            int current = nums[i];

            // Multiplying by a negative number flips signs
            if (current < 0) {
                int temp = maxProd;
                maxProd = minProd;
                minProd = temp;
            }

            // Either extend the previous subarray or start a new one
            maxProd = Math.max(current, maxProd * current);
            minProd = Math.min(current, minProd * current);

            // Update overall maximum result
            result = Math.max(result, maxProd);
        }

        return result;
    }
}