import java.util.*;

class Solution {
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {

        Map<Integer, Integer> map = new HashMap<>();

        // Mark reserved seats using bits
        for (int[] seat : reservedSeats) {
            int row = seat[0];
            int col = seat[1];

            map.put(row, map.getOrDefault(row, 0) | (1 << col));
        }

        long answer = 2L * n;

        // Masks for:
        // 2,3,4,5
        int left = (1 << 2) | (1 << 3) | (1 << 4) | (1 << 5);

        // 4,5,6,7
        int middle = (1 << 4) | (1 << 5) | (1 << 6) | (1 << 7);

        // 6,7,8,9
        int right = (1 << 6) | (1 << 7) | (1 << 8) | (1 << 9);

        for (int reserved : map.values()) {

            boolean canLeft = (reserved & left) == 0;
            boolean canMiddle = (reserved & middle) == 0;
            boolean canRight = (reserved & right) == 0;

            if (canLeft && canRight) {
                // Two groups can sit on the two sides.
                continue;
            }

            if (canLeft || canMiddle || canRight) {
                // Only one group can be placed.
                answer--;
            } else {
                // No group can be placed.
                answer -= 2;
            }
        }

        return (int) answer;
    }
}