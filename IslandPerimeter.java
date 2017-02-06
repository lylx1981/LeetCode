/*/
思路： 数学题，看了图就明白，，每个Grid都有4条边，如果这个边不和其他Grid连接，那么它就一定在周长上，所以题目的核心就是把Grid相连的边给剔除掉。
其实每两个Grid如果重合一条边，其实相应的要剔除掉2条边。这样，代码可以只考察对于一个Grid,它的右边或者下边是不是和其他Grid重合即可。如果重合，计数，然后最后数字乘以2， 就是一共要剔除掉的边。




loop over the matrix and count the number of islands;
if the current dot is an island, count if it has any right neighbour or down neighbour;
the result is islands * 4 - neighbours * 2

*/


public class Solution {
    public int islandPerimeter(int[][] grid) {
        int islands = 0, neighbours = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    islands++; // count islands
                    if (i < grid.length - 1 && grid[i + 1][j] == 1) neighbours++; // count down neighbours
                    if (j < grid[i].length - 1 && grid[i][j + 1] == 1) neighbours++; // count right neighbours
                }
            }
        }

        return islands * 4 - neighbours * 2;
    }
}

