/* 思路: 非常好的一道题， 主要用BFS。主要思想是对每一个遇到1进行考察（记录遇到一个新的Island），同时每遇到一个1，就对其所有相连的1都进行摸排，并将他们设置为0表示已经Visisted，这样后面就不会继续考察他们了。注意这里对所有相连的1进行摸排的时候应用了BFS方法，就是用Queue将当前Queue的第一个元素拿出来，对其四个方向都进行考察（代码dirs数组预先定义了计算四个方向所需要的Offset）。

自己观察了一下数组，本来发现的现象是对于遇到一个1，仅需要检查看它的上面以及左边有没有1，没有的话那么这就是一个新的Island。但是发现这个现象有时候是不对的，比如如下这种情况
111
010
*11
如果第三行*的那个位置是个1的话，这个1其实不能计算为一个新的Island（虽然其左边上面都是0）。所以正确的程序里的确是应该向4个方向都摸排的。

*/

public class NumberOfIslands {

    private static final int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private NumberOfIslands n;

    /**
     * BFS.
     * Start from the top-left corner of the grid.
     * Go through each position row by row and check if it is island.
     * If it is not, skip.
     * If it is, BFS to find the whole region.
     * Mark the region as "0" when finished.
     * Number of islands increment by 1.
     */
    public int numIslands(char[][] grid) {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '0') { // Not an island
                    continue;
                }
                // bfsRecursive(grid, i, j);
                bfs(grid, i, j);
                count++;
            }
        }
        return count;
    }

    /**
     * Iterative.
     * Set the starting grid to '0' to mark it as visited.
     * Add it to queue to start BFS.
     * Find the region and mark all grids as '0'.
     */
    private void bfs(char[][] grid, int i, int j) {
        Queue<int[]> queue = new ArrayDeque<>();
        grid[i][j] = '0';
        queue.add(new int[]{i, j});
        while (!queue.isEmpty()) {
            int[] p = queue.poll();
            for (int[] dir : dirs) { //对四个方向进行摸排
                int row = p[0] + dir[0];
                int col = p[1] + dir[1];
                // Within range and not visited.
                if (row >= 0 && row < grid.length && col >= 0 && col < grid[row].length
                    && grid[row][col] == '1') {
                    grid[row][col] = '0';
                    queue.add(new int[]{row, col});
                }
            }
        }
    }

    /**
     * Recursive.
     * Base case:
     * If out of the grid or is not an island, return.
     * If it is an island, set it to '0' as visited.
     * Then recursively search the 4 adjacent neighbors.
     */
    private void bfsRecursive(char[][] grid, int i, int j) {
        // Out of range or not going to visit.
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[i].length || grid[i][j] == '0') {
            return;
        }
        grid[i][j] = '0'; // Set to 0 can both remove this island and set to visited.
        bfsRecursive(grid, i + 1, j);
        bfsRecursive(grid, i - 1, j);
        bfsRecursive(grid, i, j + 1);
        bfsRecursive(grid, i, j - 1);
    }

    /**
     * Union find.
     * Build an Union Find data structure first.
     * Then iterate all grids row by row.
     */
    public int numIslandsUnionFind(char[][] grid) {
        if (grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        int m = grid.length, n = grid[0].length;
        UnionFind uf = new UnionFind(m, n, grid); // Build union find.

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '0') {
                    continue;
                }
                int p = i * n + j; // Id of current island. i * column + j.
                int q; // Id of adjacent island.
                if (i > 0 && grid[i - 1][j] == '1') {
                    q = p - n;
                    uf.union(p, q);
                }
                if (i < m - 1 && grid[i + 1][j] == '1') { // i is not last row.
                    q = p + n;
                    uf.union(p, q);
                }
                if (j > 0 && grid[i][j - 1] == '1') {
                    q = p - 1;
                    uf.union(p, q);
                }
                if (j < n - 1 && grid[i][j + 1] == '1') { // j is not last column.
                    q = p + 1;
                    uf.union(p, q);
                }
            }
        }
        return uf.count;
    }

    private char[][] buildGrid(String[] val) {
        char[][] grid = new char[val.length][val[0].length()];
        for (int i = 0; i < val.length; i++) {
            for (int j = 0; j < val[i].length(); j++) {
                grid[i][j] = val[i].charAt(j);
            }
        }
        return grid;
    }

    @Before
    public void setUp() {
        n = new NumberOfIslands();
    }

    @Test
    public void testExamples() {
        char[][] grid = buildGrid(new String[]{"11110", "11010", "11000", "00000"});
        Assert.assertEquals(1, n.numIslands(grid));
        grid = buildGrid(new String[]{"11000", "11000", "00100", "00011"});
        Assert.assertEquals(3, n.numIslands(grid));
    }

    @After
    public void tearDown() {
        n = null;
    }

    /**
     * Data structure to keep track of number of connected components in the grid.
     * The count is initialized as the number of island in the grid.
     * Every time two islands are unified, the count will decrease by 1.
     */
    private class UnionFind {

        /**
         * Count of connected components.
         * Initialized as number of 1's.
         * Whenever there is an union, decrement count by 1.
         */
        public int count;
        /**
         * Connected component id array.
         * The index is mapped from 2d array.
         */
        public int[] id = null;

        public UnionFind(int m, int n, char[][] grid) {
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == '1') {
                        count++;
                    }
                }
            }
            // Initialize id array by mapping each position in grid.
            id = new int[m * n];
            for (int i = 0; i < m * n; i++) {
                id[i] = i;
            }
        }

        /**
         * O(n), find the root id.
         * If p equals id[p], p is root.
         */
        public int find(int p) {
            while (p != id[p]) {
                id[p] = id[id[p]]; // Cut unnecessary branches.
                p = id[p]; // Move on to the next one
            }
            return p;
        }

        /**
         * Check whether two points are in the same edges component.
         * If connected, they will have the same id.
         */
        public boolean isConnected(int p, int q) {
            int pRoot = find(p);
            int qRoot = find(q);
            return pRoot == qRoot;
        }

        /**
         * O(n), connect two points to the same root.
         * Every time we union two points, the count will decrease by 1.
         */
        public void union(int p, int q) {
            int pRoot = find(p);
            int qRoot = find(q);
            if (pRoot == qRoot) {
                return;
            }
            id[pRoot] = qRoot; // Set p's root to q's
            count--; // IMPORTANT!! Decrement count by 1 after union.
        }
    }

}
