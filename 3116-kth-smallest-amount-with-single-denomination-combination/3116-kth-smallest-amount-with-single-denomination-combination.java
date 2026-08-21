class Solution {

    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }

    private long lcm(long a, long b) {
        return (a / gcd(a, b)) * b;
    }

    private long count(long x, int[] coins) {
        int n = coins.length;
        long total = 0;

        // Inclusion-Exclusion over all non-empty subsets
        for (int mask = 1; mask < (1 << n); mask++) {
            long currentLCM = 1;
            int bits = 0;
            boolean overflow = false;

            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    bits++;
                    currentLCM = lcm(currentLCM, coins[i]);

                    // If LCM exceeds x, floor(x / LCM) is 0
                    if (currentLCM > x) {
                        overflow = true;
                        break;
                    }
                }
            }

            if (overflow) continue;

            long contribution = x / currentLCM;

            if ((bits & 1) == 1) {
                total += contribution;
            } else {
                total -= contribution;
            }
        }

        return total;
    }

    public long findKthSmallest(int[] coins, int k) {
        long minCoin = coins[0];
        for (int coin : coins) {
            minCoin = Math.min(minCoin, coin);
        }

        long low = 1;
        long high = minCoin * k; // Upper bound: k-th multiple of the smallest coin

        while (low < high) {
            long mid = low + (high - low) / 2;

            if (count(mid, coins) >= k) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        return low;
    }
}