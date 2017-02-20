/* 思路： 注意这道题的意思是，如果有山泉水从每个格子冒出来，哪些能同时流到两个海洋去。但是做题的时候，要反着来，从离海洋最近的层开始往中间判断。

这道题反而自己往DP方向去想了，但是Leetcode上指导的方法就是用BFS，对照自己的DP思路，发现也其实是BFS的样子，，因为涉及到两个大洋，所以就单独分别处理就行。也就是把所有最外层的点都初始化为True（也就是从他们出来的水都能流到某个海里）。然后逐步向内延展就行了。
具体这里面用了因为是BFS，所以就用Queue是最合理的。对于某个特定的大洋，每次从当前Queue拿出一个元素判断其邻居（注意每个加入Queue里元素已经是一个其山泉可以流向那个大洋的格子了），如果他们的邻居还没有被访问过（访问过就代表其也是一个其山泉可以流向那个大洋的格子了），如这个邻居高度比当前点大，则这个邻居的水也能流向当前这个节点故也就能流向大洋，也就是一个能符合条件的格子了，那么就把这个邻居也加入Queue.

最后判断一下，看看哪个格子同时可以其山泉都流向2个大洋，那么这些格子就是所需要的结果。


Two Queue and add all the Pacific border to one queue; Atlantic border to another queue.
Keep a visited matrix for each queue. In the end, add the cell visited by two queue to the result.
BFS: Water flood from ocean to the cell. Since water can only flow from high/equal cell to low cell, add the neighboor cell with height larger or equal to current cell to the queue and mark as visited.


*/

public class Solution {
    int[][]dir = new int[][]{{1,0},{-1,0},{0,1},{0,-1}};
    public List<int[]> pacificAtlantic(int[][] matrix) {
        List<int[]> res = new LinkedList<>();
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0){
            return res;
        }
        int n = matrix.length, m = matrix[0].length;
        //One visited map for each ocean
        boolean[][] pacific = new boolean[n][m];
        boolean[][] atlantic = new boolean[n][m];
        Queue<int[]> pQueue = new LinkedList<>();
        Queue<int[]> aQueue = new LinkedList<>();
        for(int i=0; i<n; i++){ //Vertical border
            pQueue.offer(new int[]{i, 0});
            aQueue.offer(new int[]{i, m-1});
            pacific[i][0] = true;
            atlantic[i][m-1] = true;
        }
        for(int i=0; i<m; i++){ //Horizontal border
            pQueue.offer(new int[]{0, i});
            aQueue.offer(new int[]{n-1, i});
            pacific[0][i] = true;
            atlantic[n-1][i] = true;
        }
        bfs(matrix, pQueue, pacific);
        bfs(matrix, aQueue, atlantic);
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                if(pacific[i][j] && atlantic[i][j])
                    res.add(new int[]{i,j});
            }
        }
        return res;
    }
    public void bfs(int[][]matrix, Queue<int[]> queue, boolean[][]visited){
        int n = matrix.length, m = matrix[0].length;
        while(!queue.isEmpty()){
            int[] cur = queue.poll();
            for(int[] d:dir){
                int x = cur[0]+d[0];
                int y = cur[1]+d[1];
                if(x<0 || x>=n || y<0 || y>=m || visited[x][y] || matrix[x][y] < matrix[cur[0]][cur[1]]){
                    continue;
                }
                visited[x][y] = true;
                queue.offer(new int[]{x, y});
            } 
        }
    }
}
