public class UniquePaths2 {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        if (obstacleGrid == null || obstacleGrid.length == 0 || obstacleGrid[0].length == 0) {
            return 0;
        }
        
        int n = obstacleGrid.length;
        int m = obstacleGrid[0].length;
        int[][] paths = new int[n][m];
        //仅需要在对第一行第一列初始化的地方稍加修改即可，把障碍点的初始化考虑进去即可，其他基本不用动。
        for (int i = 0; i < n; i++) {
            if (obstacleGrid[i][0] != 1) {
                paths[i][0] = 1;
            } else {
                //直接退出即可，因为第一列只要碰到一个0，它下面的点都无法到达，
                //所以path的当前为是0，因为数组生成时候默认值就是0，所以直接break就行了
                break; 
            }
        }
        
        for (int i = 0; i < m; i++) {
            if (obstacleGrid[0][i] != 1) {
                paths[0][i] = 1; 
            } else {
                break; //直接退出即可，因为第一行只要碰到一个0，它后面的点都无法到达
            }
        }
        
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                if (obstacleGrid[i][j] != 1) { //判断是不是障碍点
                    paths[i][j] = paths[i - 1][j] + paths[i][j - 1];
                } else {
                    paths[i][j] = 0;
                }
            }
        }
        
        return paths[n - 1][m - 1];
    }
    
    /**
     * DP, bottom-up approach
     * build from end point to start point
     * for the grid paths at the rth row and cth column
     * paths[r][c] = obstacleGrid[r][c] == 1 ? 0 
     * : paths[r + 1][c] + paths[r][c + 1];
     */
    public static int uniquePathsWithObstacles(int[][] obstacleGrid) {
        if (obstacleGrid == null) return 0;
        int m = obstacleGrid.length;
        if (m == 0) return 0;
        int n = obstacleGrid[0].length;
        int[][] paths = new int[m + 1][n + 1];
        
        paths[m - 1][n] = 1;
        for (int r = m - 1; r >= 0; r--) {
            for (int c = n - 1; c >= 0; c--) {
                paths[r][c] = obstacleGrid[r][c] == 1 ? 0 : paths[r + 1][c] + paths[r][c + 1];
            }
        }
        return paths[0][0];
    }
    
}
