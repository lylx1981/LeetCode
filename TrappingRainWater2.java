/*思路： 非常巧的一道题，用优先级队列，其中存的是一种新的类Cell {x,y, height}.
首先是先把外围所有位置都加入优先级队列里，然后利用木桶原理，每次总是从优先级队列里面找最最最低的那个Cell出来（用的是最小堆），比如叫Cell A，然后判断其四个还没有访问过的邻居。如果一个邻居比这个A高度还小，那么高度差就是能填进去的水，因为就是因为木桶原理，A已经是当前优先级队列里高度最小的了，所以这些填进去的水将达到A的高度（而且以后也不可能再往这个位置填入更多的水了，因为A的高度是最小的，总归当前位置水的高度无论如何不会莫过A的高度），但它们怎么都跑不出去，因为A的高度是最小的。然后，把这个邻居对应的Cell类加入到Queue里，注意，如果这个邻居原来的高度比A高，也就是没有水在这里填进去过，那么直接Cell的高度就是邻居的高度。但是如果这个邻居位置低于A，填进去水了，那么新加入Queue的Cell的高度不是这个邻居的高度，而是A的高度，这一点上最最重要的，因为现在这个邻居Anyway水已经填到A了，现在填进去的水也代表是一堵墙了，所以现在这个Cell的高度就应该变为A的高度了，这个是这一题最巧的地方。将邻居加入Queue后，再次进入下一轮循环，再次找出当前优先级队列里的最小元素，即可。


别人的思路描述：
用PriorityQueue把选中的height排序。为走位，create class Cell {x,y, height}.

注意几个理论：
1. 从matrix四周开始考虑，发现matrix能Hold住的水，取决于height低的block。
2. 必须从外围开始考虑，因为水是被包裹在里面，外面至少需要现有一层。

以上两点就促使我们用min-heap: 也就是natural order的PriorityQueue<Cell>.

process的时候，画个图也可以搞清楚，就是四个方向都走走，用curr cell的高度减去周围cell的高度。 若大于零，那么就有积水。

每个visited的cell都要mark. 去到4个方向的cell,加进queue里面继续process. 

这里，有一点，和trapping water I 想法一样。刚刚从外围，只是能加到跟外围cell高度一致的水平面。往里面，很可能cell高度变化。   
这里要附上curr cell 和 move-to cell的最大高度。*/

/*
Thoughts: same idea as the trap Rain Water I.
Since this is not 1-way run through a 1D array (2D array can go 4 directions...), need to mark visted spot.
Use PriorityQueue, sort lowest on top, because the lowest surroundings determines the best we can get.
Bukkit theory: the lowest bar determines the height of the bukkit water. So, we always process the lowest first. 
Therefore, we use a min-heap, a natural order priorityqueue based on height.
Note: when adding a new block into the queue, comparing with the checked origin, we still want to add the higher height into queue. 
(The high bar will always exist and hold the bukkit.)
Step:
1. Create Cell (x,y,h)
2. Priorityqueue on Cell of all 4 borders
3. Process each element in queue, and add surrounding blocks into queue.
4. Mark checked block
*/

public class Solution {
    class Cell {
        int x;
        int y;
        int h;
        public Cell(int x, int y, int height) {
            this.x = x;
            this.y = y;
            this.h = height;
        }
    }
    /**
     * @param heights: a matrix of integers
     * @return: an integer
     */
    public int trapRainWater(int[][] heights) {
        if (heights == null || heights.length == 0 || heights[0].length == 0) {
            return 0;
        }

        PriorityQueue<Cell> queue = new PriorityQueue<Cell>(1, new Comparator<Cell>(){
            public int compare(Cell A, Cell B) {
                return A.h - B.h;
            }
        });
        int n = heights.length;
        int m = heights[0].length;
        boolean[][] visited = new boolean[n][m];
        //把四周边界先都处理了，假如最小堆
        //LEFT-RIGHT
        for (int i = 0; i < n; i++) {
            visited[i][0] = true;
            visited[i][m - 1] = true;
            queue.offer(new Cell(i, 0, heights[i][0]));
            queue.offer(new Cell(i, m - 1, heights[i][m - 1]));
        }
        //TOP-BOTTOM
        for (int i = 0; i < m; i++) {
            visited[0][i] = true;
            visited[n - 1][i] = true;
            queue.offer(new Cell(0, i, heights[0][i]));
            queue.offer(new Cell(n - 1, i, heights[n - 1][i]));
        }

        int[] xs = {0,  0, 1, -1};
        int[] ys = {1, -1, 0,  0};
        int sum = 0;
        while (!queue.isEmpty()) {
            Cell cell = queue.poll();
            for (int i = 0; i < 4; i++) {
                int nx = cell.x + xs[i];
                int ny = cell.y + ys[i];
                if (nx >= 0 && nx < n && ny >= 0 && ny < m && !visited[nx][ny]) { //只判断还没有访问过邻居
                    visited[nx][ny] = true;
                    sum += Math.max(0, cell.h - heights[nx][ny]);
                    queue.offer(new Cell(nx, ny, Math.max(heights[nx][ny], cell.h))); //这里是这一题最巧的地方。
                }
            }
        }//end while
        return sum;
    }
}
