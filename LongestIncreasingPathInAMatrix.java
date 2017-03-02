/*思路： 思路基本一样，对每个位置进行DFS判断从该位置开始的最长路经的长度，然后最后再对所有位置的结果取最大。
里面主要的优化是用Cache保存中间结果。特别的Dfs函数的返回是以（i,j）为起点的最长递增序列的长度。另外一个优化就是不用设置Visisted矩阵，因为只要邻居节点比当前判断节点小，该邻居节点就不可能是下一个要考察的目标，因为不符合递增自序列的定义。

这道题不错，还是要好好看看！

To get max length of increasing sequences:

Do DFS from every cell
Compare every 4 direction and skip cells that are out of boundary or smaller
Get matrix max from every cell's max
Use matrix[x][y] <= matrix[i][j] so we don't need a visited[m][n] array
The key is to cache the distance because it's highly possible to revisit a cell
Hope it helps!

*/

public static final int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

public int longestIncreasingPath(int[][] matrix) {
    if(matrix.length == 0) return 0;
    int m = matrix.length, n = matrix[0].length;
    int[][] cache = new int[m][n];
    int max = 1;
    for(int i = 0; i < m; i++) {
        for(int j = 0; j < n; j++) {
            int len = dfs(matrix, i, j, m, n, cache); //对每个位置i,j进行DFS考察求得以其位起点的最长递增自序列长度。
            max = Math.max(max, len); //对所有位置的结果进行取最大
        }
    }   
    return max;
}

//Dfs函数的返回是以（i,j）为起点的最长递增序列的长度
public int dfs(int[][] matrix, int i, int j, int m, int n, int[][] cache) {
    if(cache[i][j] != 0) return cache[i][j]; //如果已经有Cache的值了，直接用即可。
    int max = 1;
    for(int[] dir: dirs) {
        int x = i + dir[0], y = j + dir[1];
        if(x < 0 || x >= m || y < 0 || y >= n || matrix[x][y] <= matrix[i][j]) continue; //因为现在是从i,j判断下一步的位置，所以下一步的位置必须比当前matrix[i][j]大。
        int len = 1 + dfs(matrix, x, y, m, n, cache); //以（i,j）为起点的并且经过（x,y）邻居的最长递增序列的长度其实就是以x,y这个满足条件的邻居的最长递增序列的长度+1。而以（x,y）为起点的最长递增序列的长度其实就是继续调用dfs(matrix, x, y, m, n, cache)，这里非常巧！~！！
        max = Math.max(max, len);
    }
    cache[i][j] = max;
    return max;
}
