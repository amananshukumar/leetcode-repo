import java.util.Arrays;

class Solution {
    public String smallestNumber(String num, long t) {
        // Step 1: Factorize t into prime factors 2, 3, 5, 7
        long tempT = t;
        int[] counts = new int[8]; // indices 2, 3, 5, 7 used
        int[] primes = {2, 3, 5, 7};
        
        for (int p : primes) {
            while (tempT % p == 0) {
                counts[p]++;
                tempT /= p;
            }
        }
        
        // If t has prime factors other than 2, 3, 5, 7, it's impossible
        if (tempT > 1) {
            return "-1";
        }

        int n = num.length();
        int firstZero = num.indexOf('0');
        int maxPrefixLen = (firstZero == -1) ? n : firstZero;

        // Digit factor representations: {2s, 3s, 5s, 7s}
        int[][] digitFactors = {
            {0, 0, 0, 0}, // 0
            {0, 0, 0, 0}, // 1
            {1, 0, 0, 0}, // 2
            {0, 1, 0, 0}, // 3
            {2, 0, 0, 0}, // 4
            {0, 0, 1, 0}, // 5
            {1, 1, 0, 0}, // 6
            {0, 0, 0, 1}, // 7
            {3, 0, 0, 0}, // 8
            {0, 2, 0, 0}  // 9
        };

        // Compute prefix requirements for prefix lengths 0 to maxPrefixLen
        int[][] prefixReqs = new int[maxPrefixLen + 1][8];
        prefixReqs[0][2] = counts[2];
        prefixReqs[0][3] = counts[3];
        prefixReqs[0][5] = counts[5];
        prefixReqs[0][7] = counts[7];

        for (int i = 0; i < maxPrefixLen; i++) {
            int d = num.charAt(i) - '0';
            prefixReqs[i + 1][2] = Math.max(0, prefixReqs[i][2] - digitFactors[d][0]);
            prefixReqs[i + 1][3] = Math.max(0, prefixReqs[i][3] - digitFactors[d][1]);
            prefixReqs[i + 1][5] = Math.max(0, prefixReqs[i][5] - digitFactors[d][2]);
            prefixReqs[i + 1][7] = Math.max(0, prefixReqs[i][7] - digitFactors[d][3]);
        }

        // Check if the exact number `num` is already valid
        if (maxPrefixLen == n && isSatisfied(prefixReqs[n])) {
            return num;
        }

        // Step 2: Try building a number of length n by diverging at index L
        for (int L = maxPrefixLen; L >= 0; L--) {
            int startD = (L < n) ? (num.charAt(L) - '0' + 1) : 1;

            for (int d = startD; d <= 9; d++) {
                int rem2 = Math.max(0, prefixReqs[L][2] - digitFactors[d][0]);
                int rem3 = Math.max(0, prefixReqs[L][3] - digitFactors[d][1]);
                int rem5 = Math.max(0, prefixReqs[L][5] - digitFactors[d][2]);
                int rem7 = Math.max(0, prefixReqs[L][7] - digitFactors[d][3]);

                int[] minDigits = getMinDigits(rem2, rem3, rem5, rem7);
                int remLen = n - 1 - L;

                if (minDigits.length <= remLen) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(num, 0, L);
                    sb.append(d);
                    for (int i = 0; i < remLen - minDigits.length; i++) {
                        sb.append('1');
                    }
                    for (int digit : minDigits) {
                        sb.append(digit);
                    }
                    return sb.toString();
                }
            }
        }

        // Step 3: If length n is insufficient, increase length to n + 1 (or longer if needed)
        int[] minDigits = getMinDigits(counts[2], counts[3], counts[5], counts[7]);
        int targetLen = Math.max(n + 1, minDigits.length);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < targetLen - minDigits.length; i++) {
            sb.append('1');
        }
        for (int digit : minDigits) {
            sb.append(digit);
        }
        return sb.toString();
    }

    private boolean isSatisfied(int[] reqs) {
        return reqs[2] == 0 && reqs[3] == 0 && reqs[5] == 0 && reqs[7] == 0;
    }

    private int[] getMinDigits(int c2, int c3, int c5, int c7) {
        int n9 = c3 / 2; c3 %= 2;
        int n8 = c2 / 3; c2 %= 3;
        int n7 = c7;     c7 = 0;

        int n6 = 0;
        if (c2 > 0 && c3 > 0) {
            n6 = 1;
            c2--;
            c3--;
        }

        int n5 = c5;     c5 = 0;
        int n4 = c2 / 2; c2 %= 2;
        int n3 = c3;     c3 = 0;
        int n2 = c2;     c2 = 0;

        int total = n9 + n8 + n7 + n6 + n5 + n4 + n3 + n2;
        int[] digits = new int[total];
        int idx = 0;

        for (int i = 0; i < n2; i++) digits[idx++] = 2;
        for (int i = 0; i < n3; i++) digits[idx++] = 3;
        for (int i = 0; i < n4; i++) digits[idx++] = 4;
        for (int i = 0; i < n5; i++) digits[idx++] = 5;
        for (int i = 0; i < n6; i++) digits[idx++] = 6;
        for (int i = 0; i < n7; i++) digits[idx++] = 7;
        for (int i = 0; i < n8; i++) digits[idx++] = 8;
        for (int i = 0; i < n9; i++) digits[idx++] = 9;

        return digits;
    }
}