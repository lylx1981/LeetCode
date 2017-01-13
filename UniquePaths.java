class UniquePaths {
    
    public static void main(String[] args) {
        System.out.println(uniquePathsMath(10, 20));
        System.out.println(uniquePathsDP(10, 20));
    }
    
    /**
     * Math, Combination
     * Equivalent to choose n-1 to go down from m - 1 + n - 1
     * other steps will go right
     * 纯数学计算方法
     */
    public static int uniquePathsMath(int m, int n) {
        int k = m > n ? n : m;
        int N = m + n - 2;
        double res = 1; // note that res can overflow
        for (int i = 1; i < k; i++) {
            res *= N--;
            res /= i;
        }
        return (int)res; // convert to int
    }
    
    /**
     * DP, top-down approach
     * use a matrix to store # of paths
     * base cases are, when m <= 0 or n <= 0, no way
     * when m == 1 or n == 1, only 1 way (straight down or straight right)
     */
    static int[][] paths = new int[m + 1][n + 1];
    public static int uniquePathsDP(int m, int n) {
        if (m <= 0 || n <= 0) return 0; // 不是一个矩阵，没有答案，返回0
        if (m == 1 || n == 1) return 1; //当一个矩阵只有一行一列的时候，则只有一种走法，返回1即可
        if (paths[m][n] == 0) 
            paths[m][n] = uniquePathsDP(m - 1, n) + uniquePathsDP(m, n - 1); //递归调用方程式，考虑更小一级别的矩阵
        return paths[m][n];
    }
    
    /**
     * DP, bottom-up approach
     */
    public static int uniquePaths(int m, int n) {
        int[][] paths = new int[m + 1][n + 1];
        paths[m - 1][n] = 1;
        for (int r = m - 1; r >= 0; r--) 
            for (int c = n - 1; c >= 0; c--) 
                paths[r][c] = paths[r + 1][c] + paths[r][c + 1]; //递归调用方程式
        return paths[0][0];
    }
    /*
    这种方法最容易懂
    The assumptions are:

    When (n==0||m==0) the function always returns 1 since the robot
    can't go left or up.
    For all other cells. The result = uniquePaths(m-1,n)+uniquePaths(m,n-1)
    Therefore I populated the edges with 1 first and use DP to get the full 2-D array.

    Please give any suggestions on improving the code.
    */
    public int uniquePaths(int m, int n) {
        Integer[][] map = new Integer[m][n];
        for(int i = 0; i<m;i++){
            map[i][0] = 1; //第一列肯定只有1种走法
        }
        for(int j= 0;j<n;j++){
            map[0][j]=1; //第一行肯定只有一种走法
        }
        for(int i = 1;i<m;i++){
            for(int j = 1;j<n;j++){
                map[i][j] = map[i-1][j]+map[i][j-1];
            }
        }
        return map[m-1][n-1];
    }
    

}
