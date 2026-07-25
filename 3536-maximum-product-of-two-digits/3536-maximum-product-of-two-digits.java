class Solution {
    public int maxProduct(int n) {
        int max1 = Integer.MIN_VALUE;
        int max2 = Integer.MIN_VALUE;

        while (n > 0) {
            int s = n % 10;

            if (s >= max1) {
                max2 = max1;
                max1 = s;
            } else if (s > max2) {
                max2 = s;
            }

            n /= 10;
        }

        return max1 * max2;
    }
}