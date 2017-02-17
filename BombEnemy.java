
/*
思路：感觉这题没什么技巧性可言，还是对数组进行遍历。对每个点进行了考察，就是稍微复杂了一点。只是边遍历，边随时更新Row和Col能够炸掉的敌人数，进行计算后随时更新最大值。
这道题的思想就是遍历数组中的每一个点，当前点能够得到的最大值为从左边的头（边界或wall）到右边的头（遇到边界或wall）的值（row值）＋从上面的头（边界或wall）到下面的头（遇到边界或wall）的值（col值）。每一个row范围内的点都共用当前的row值，每一个col范围内的点都共用当前col值。当从新的边界或者wall开始时，相当于进入了新的一段范围，要重新计算row值或者col值。
遍历数组中每一个点，若为0则开始计算
若当前点为第一列或者左边一个点为wall，表明进入了一个新的区间，需要重新计算。从该点开始一直向右直到遇到边界或者wall，在该过程中，每遇到一个E就将row值＋1
若当前点为第一行或者上边一个点为wall，表明进入了一个新的区间，需要重新计算。从该点开始一直向下直到遇到边界或者wall，在该过程中，每遇到一个E就将col值＋1
重复2-3步骤

Walk through the matrix. At the start of each non-wall-streak (row-wise or column-wise), count the number of hits in that streak and remember it. O(mn) time, O(n) space.
*/
int maxKilledEnemies(vector<vector<char>>& grid) {
    int m = grid.size(), n = m ? grid[0].size() : 0;
    int result = 0, rowhits, colhits[n];
    for (int i=0; i<m; i++) {
        for (int j=0; j<n; j++) {
            if (!j || grid[i][j-1] == 'W') {
                rowhits = 0;
                for (int k=j; k<n && grid[i][k] != 'W'; k++)
                    rowhits += grid[i][k] == 'E';
            }
            if (!i || grid[i-1][j] == 'W') {
                colhits[j] = 0;
                for (int k=i; k<m && grid[k][j] != 'W'; k++)
                    colhits[j] += grid[k][j] == 'E';
            }
            if (grid[i][j] == '0')
                result = max(result, rowhits + colhits[j]);
        }
    }
    return result;
}
