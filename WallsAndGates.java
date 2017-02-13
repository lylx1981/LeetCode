/*  思路： 思路： 比较简单的一道题，用BFS和Queue。先找到所有的0出口，从0出口开始一层一层判断，同时对每个位置只会被访问并且计算一次，也就是说如果一个点已经从INF更新为一个数量，这个位置的值已经确定了，不用再对这个点进行判断了。代码过程是，先把所有的0出口如Queue。然后进入While循环，每拿出Queue里面的一个元素，就对其上下左右四个位置中只要不是INF的位置进行计算并更新数值。当Queue为空的时候推出While循环，然后返回就是最终结果了。

     */
public class WallsAndGates {

    private static final int EMPTY = Integer.MAX_VALUE;
    private static final int GATE = 0;
    private static final int WALL = -1;
    private static final int[][] DIRS = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    private WallsAndGates w;

    /**
     * BFS.
     * Search from gate to rooms.
     * As its breadth first search, it makes sure that:
     * The rooms are visited level by level.
     * So that rooms at distance 1 won't be visited until all gates are visited.
     * Also use EMPTY room to indicate whether a room has been visited.
     * <p>
     * Implementations:
     * Add all gates to the queue first (first level).
     * Update its 4 adjacent empty rooms with new distance.
     * Then add these rooms to the queue.
     * Stop when the queue is empty.
     */
    public void wallsAndGates(int[][] rooms) {
        Queue<int[]> queue = new LinkedList<>();
        // Add all zeros(gates) to queue.
        for (int i = 0; i < rooms.length; i++) {
            for (int j = 0; j < rooms[i].length; j++) {
                if (rooms[i][j] == GATE) {
                    queue.offer(new int[]{i, j});
                }
            }
        }
        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            int r = pos[0];
            int c = pos[1];
            // Visit 4 adjacent nodes that are empty.
            if (r > 0 && rooms[r - 1][c] == EMPTY) {
                rooms[r - 1][c] = rooms[r][c] + 1; // Update distance before enqueue.
                queue.offer(new int[]{r - 1, c});
            }
            if (r < rooms.length - 1 && rooms[r + 1][c] == EMPTY) {
                rooms[r + 1][c] = rooms[r][c] + 1;
                queue.offer(new int[]{r + 1, c});
            }
            if (c > 0 && rooms[r][c - 1] == EMPTY) {
                rooms[r][c - 1] = rooms[r][c] + 1;
                queue.offer(new int[]{r, c - 1});
            }
            if (c < rooms[0].length - 1 && rooms[r][c + 1] == EMPTY) {
                rooms[r][c + 1] = rooms[r][c] + 1;
                queue.offer(new int[]{r, c + 1});
            }
        }
    }

    /**
     * BFS. Level-order Traversal.
     * Use queue size to pull each level.
     */
    public void wallsAndGatesB(int[][] rooms) {
        Queue<int[]> queue = new LinkedList<>();
        // Add all zeros to queue.
        for (int i = 0; i < rooms.length; i++) {
            for (int j = 0; j < rooms[i].length; j++) {
                if (rooms[i][j] == GATE) {
                    queue.offer(new int[]{i, j});
                }
            }
        }
        while (!queue.isEmpty()) {
            for (int i = queue.size(); i > 0; i--) { // Level-order.
                int[] pos = queue.poll();
                for (int j = 0; j < DIRS.length; j++) {
                    int nextI = pos[0] + DIRS[j][0];
                    int nextJ = pos[1] + DIRS[j][1];
                    if (0 <= nextI && nextI < rooms.length
                        && 0 <= nextJ && nextJ < rooms[0].length
                        && rooms[nextI][nextJ] == Integer.MAX_VALUE) {
                        rooms[nextI][nextJ] = rooms[pos[0]][pos[1]] + 1;
                        queue.offer(new int[]{nextI, nextJ});
                    }
                }
            }
        }
    }

    @Before
    public void setUp() {
        w = new WallsAndGates();
    }

    @Test
    public void testExamples() {
        int[][]
            input =
            {{2147483647, -1, 0, 2147483647}, {2147483647, 2147483647, 2147483647, -1},
             {2147483647, -1, 2147483647, -1}, {0, -1, 2147483647, 2147483647}};
        w.wallsAndGates(input);
        for (int[] row :
            input) {
            System.out.println(Arrays.toString(row));
        }
    }

    @After
    public void tearDown() {
        w = null;
    }
}
