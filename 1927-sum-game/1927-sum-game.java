class Solution {
    public boolean sumGame(String num) {
        int n = num.length();
        int half = n / 2;

        int sum = 0;
        int q = 0;

        for (int i = 0; i < n; i++) {
            if (num.charAt(i) == '?') {
                if (i < half) {
                    q++;
                } else {
                    q--;
                }
            } else {
                int digit = num.charAt(i) - '0';
                if (i < half) {
                    sum += digit;
                } else {
                    sum -= digit;
                }
            }
        }

        // Alice wins if the net '?' count is odd
        if (q % 2 != 0) {
            return true;
        }

        // Bob wins if and only if 2 * sum + 9 * q == 0
        return (2 * sum + 9 * q) != 0;
    }
}