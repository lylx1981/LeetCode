    /*
    思路：这道题自己想的方法比较傻，还是对每一行进行预处理计算（也就是完全模仿之前的这个基础题的方法），看了答案以后发现最优解法的确是对之前方法的扩展，但是不是以每1行来进行预计算，而且直接预计算每个点到（0，0）的所成长方形的和，其中用来DP，也就是如何依据前面i-1,j-1步，计算当前i,j步，还是动态规划的思想。当有了这个预先计算值后，对于每个函数调用，就用这些预先计算值直接计算即可。在DP和和函数调用部分，都用掉了一点几何的知识，基本上就是大的正方形减去不必要的部分，然后再加上重合的减的多的那部分。自己画画图就明白了。

    */        
        


public class RangeSumQuery2DImmutable {
    public class NumMatrix {

        /**
         * Pre-compute sum from (0, 0) to current point in another matrix
         * Sum from (0, 0) to (row, col) is A + B - C + D
         * 1. A: sum from (0, 0) to (row - 1, col)
         * 2. B: sum from (0, 0) to (row, col - 1)
         * 3. C: sum from (0, 0) to (row - 1, col - 1)
         * 4. D: sum from (row ,col) to (row, col), which is matrix[row][col] itself
         * C is added twice in A and B
         */
        private int[][] dp;

        public NumMatrix(int[][] matrix) {
            if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return;
            // Make dp one row and one column bigger than matrix to avoid trivial range validation
            dp = new int[matrix.length + 1][matrix[0].length + 1];
            for (int r = 0; r < matrix.length; r++) {
                for (int c = 0; c < matrix[0].length; c++) {
                    dp[r + 1][c + 1] = dp[r + 1][c] + dp[r][c + 1] + matrix[r][c] - dp[r][c];
                }
            }
        }

        /**
         * A: Sum of (0, 0) to (row2, col2) is dp[row2 + 1][col2 + 1]
         * B: Sum of (0, 0) to (row1 - 1, col2) is dp[row1][col2 + 1]
         * C: Sum of (0, 0) to (row2, col1 - 1) is dp[row2 + 1][col1]
         * D: Sum of (0, 0) to (row1 - 1, col1 - 1) is dp[row1][col1]
         * Range sum is A - B - C + D (D is subtracted twice in B and C)
         */
        public int sumRegion(int row1, int col1, int row2, int col2) {
            return dp[row2 + 1][col2 + 1] - dp[row1][col2 + 1] - dp[row2 + 1][col1] + dp[row1][col1];
        }
    }

// Your NumMatrix object will be instantiated and called as such:
// NumMatrix numMatrix = new NumMatrix(matrix);
// numMatrix.sumRegion(0, 1, 2, 3);
// numMatrix.sumRegion(1, 2, 3, 4);
}
