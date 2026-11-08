class Solution {
    public int countServers(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        
        // Arrays to keep track of the number of servers in each row and column
        int[] rowCounts = new int[m];
        int[] colCounts = new int[n];
        
        // First pass: Count servers in each row and column
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == 1) {
                    rowCounts[r]++;
                    colCounts[c]++;
                }
            }
        }
        
        int communicatingServers = 0;
        
        // Second pass: Count how many servers can communicate
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == 1) {
                    // If row count > 1 or column count > 1, this server is not isolated
                    if (rowCounts[r] > 1 || colCounts[c] > 1) {
                        communicatingServers++;
                    }
                }
            }
        }
        
        return communicatingServers;
    }
}
