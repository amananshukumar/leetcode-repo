class Solution {
    public String shortestBeautifulSubstring(String s, int k) {
        int n = s.length();

        int left = 0;
        int ones = 0;

        int minLen = Integer.MAX_VALUE;
        String answer = "";

        for (int right = 0; right < n; right++) {

            if (s.charAt(right) == '1') {
                ones++;
            }

            // We have exactly k ones
            while (ones == k) {

                int len = right - left + 1;
                String current = s.substring(left, right + 1);

                // Better length, or same length but lexicographically smaller
                if (len < minLen ||
                    (len == minLen && current.compareTo(answer) < 0)) {

                    minLen = len;
                    answer = current;
                }

                // Try to shrink the window
                if (s.charAt(left) == '1') {
                    ones--;
                }

                left++;
            }
        }

        return answer;
    }
}