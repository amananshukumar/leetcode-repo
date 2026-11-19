
import java.util.Arrays;

class Solution {
    public int minimizeSum(int[] nums) {
        Arrays.sort(nums);

        int n = nums.length;

        int ans = Integer.MAX_VALUE;

        // Change the two smallest elements
        ans = Math.min(ans, nums[n - 1] - nums[2]);

        // Change the two largest elements
        ans = Math.min(ans, nums[n - 3] - nums[0]);

        // Change one smallest and one largest element
        ans = Math.min(ans, nums[n - 2] - nums[1]);

        return ans;
    }
}