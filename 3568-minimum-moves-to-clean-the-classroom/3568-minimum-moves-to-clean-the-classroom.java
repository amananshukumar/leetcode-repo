import java.util.*;

class Solution {

    static class State {
        int r, c, energy, mask, moves;

        State(int r, int c, int energy, int mask, int moves) {
            this.r = r;
            this.c = c;
            this.energy = energy;
            this.mask = mask;
            this.moves = moves;
        }
    }

    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();

        int startR = 0, startC = 0;
        Map<Integer, Integer> litterIndex = new HashMap<>();
        int litterCount = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char ch = classroom[i].charAt(j);
                if (ch == 'S') {
                    startR = i;
                    startC = j;
                } else if (ch == 'L') {
                    litterIndex.put(i * n + j, litterCount++);
                }
            }
        }

        int targetMask = (1 << litterCount) - 1;
        if (targetMask == 0) return 0;

        // maxEnergy[r][c][mask] stores the highest remaining energy seen so far
        int[][][] maxEnergy = new int[m][n][1 << litterCount];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(maxEnergy[i][j], -1);
            }
        }

        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(startR, startC, energy, 0, 0));
        maxEnergy[startR][startC][0] = energy;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (!queue.isEmpty()) {
            State cur = queue.poll();

            if (cur.mask == targetMask) {
                return cur.moves;
            }

            if (cur.energy == 0) {
                continue;
            }

            for (int d = 0; d < 4; d++) {
                int nr = cur.r + dr[d];
                int nc = cur.c + dc[d];

                if (nr < 0 || nr >= m || nc < 0 || nc >= n) continue;
                char cell = classroom[nr].charAt(nc);
                if (cell == 'X') continue;

                int newEnergy = cur.energy - 1;
                int newMask = cur.mask;

                if (cell == 'L') {
                    int index = litterIndex.get(nr * n + nc);
                    newMask |= (1 << index);
                } else if (cell == 'R') {
                    newEnergy = energy;
                }

                // Only visit if we arrive with strictly more energy than previously recorded
                if (newEnergy > maxEnergy[nr][nc][newMask]) {
                    maxEnergy[nr][nc][newMask] = newEnergy;
                    queue.offer(new State(nr, nc, newEnergy, newMask, cur.moves + 1));
                }
            }
        }

        return -1;
    }
}