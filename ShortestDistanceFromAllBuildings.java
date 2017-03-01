/*思路： 这道题猛一看觉得很像那道Meeting Place的题，但是观察后发现比那道题复杂多了，原因是这道题中，每个Buiilding的位置以及障碍的位置上均不能再建屋子，而Meeting Place那道题里我们可以选择一个人家的位置直接作为Meeting Place。另一个是不能穿过Building和障碍物，所以又牵扯到找路的问题，而Meeting Place那道题可以路径穿过任何点。

看了Leetcode那边的解法，和自己想的差不多，感觉也没有什么好的方法，因为很难。思路是遍历整个矩阵，对每个Building的位置开始进行BFS，计算其到每个0的距离。对每个Building都有一个BFS过程，所以有多少BFS过程，那么在多个BFS过程中，同时用distance[][]累积记录某个0位置到所有Building的距离之和，reach[][]记录某个0位置可以到达多少个Building。

然后最后对distance[][]遍历，对于每个0位置，如果其reach[][]对应的数目是Building的总数，也就是可以到所有Building，那么就用其distance[][]和当前Min最小值比较，如果更小，则更新。 如果没有一个满足的，表示无解。


The main idea is the following:

Traverse the matrix. For each building, use BFS to compute the shortest distance from each '0' to
this building. After we do this for all the buildings, we can get the sum of shortest distance
from every '0' to all reachable buildings. This value is stored
in 'distance[][]'. For example, if grid[2][2] == 0, distance[2][2] is the sum of shortest distance from this block to all reachable buildings.
Time complexity: O(number of 1)O(number of 0) ~ O(m^2n^2)

We also count how many building each '0' can be reached. It is stored in reach[][]. This can be done during the BFS. We also need to count how many total buildings are there in the matrix, which is stored in 'buildingNum'.

Finally, we can traverse the distance[][] matrix to get the point having shortest distance to all buildings. O(m*n)

The total time complexity will be O(m^2*n^2), which is quite high!. Please let me know if I did the analysis wrong or you have better solution.*/


public class Solution {
    public int shortestDistance(int[][] grid) {
        if (grid == null || grid[0].length == 0) return 0;
        final int[] shift = new int[] {0, 1, 0, -1, 0};
        
        int row  = grid.length, col = grid[0].length;
        int[][] distance = new int[row][col];
        int[][] reach = new int[row][col];
        int buildingNum = 0;
        
        for (int i = 0; i < row; i++) {
            for (int j =0; j < col; j++) {
                if (grid[i][j] == 1) {
                    buildingNum++; //记录出现的Building数目
                    Queue<int[]> myQueue = new LinkedList<int[]>();
                    myQueue.offer(new int[] {i,j});//最开始将Building的坐标放进Queue，从这个位置开始BFS

                    boolean[][] isVisited = new boolean[row][col]; //BFS需要，凡是已经访问过的点，就不再访问了
                    int level = 1; //从当前Building开始的Level数，也代表了最短距离，因为是BFS
                    
                    while (!myQueue.isEmpty()) {
                        int qSize = myQueue.size();
                        for (int q = 0; q < qSize; q++) {
                            int[] curr = myQueue.poll();//拿出当前一个点，对其四个邻居做判断
                            
                            for (int k = 0; k < 4; k++) {
                                int nextRow = curr[0] + shift[k]; //当前考察位置横坐标
                                int nextCol = curr[1] + shift[k + 1]; //当前考察位置纵坐标
                                
                                if (nextRow >= 0 && nextRow < row && nextCol >= 0 && nextCol < col
                                    && grid[nextRow][nextCol] == 0 && !isVisited[nextRow][nextCol]) {
                                        //The shortest distance from [nextRow][nextCol] to thic building
                                        // is 'level'. 这里不用取Min操作，因为是BFS，只要第一次被碰到，当前的Level就一定是最短距离了。
                                        distance[nextRow][nextCol] += level; //注意凡是进入这个If的，都是说明当前是个0位置，level就是当前位置到现在正在考察的Build的距离，因为是BFS，
                                        reach[nextRow][nextCol]++; //同时记录当前这个0位置到的Building数又多了一个。
                                        //这里也说明了只有0位置的点才在distance 和reach数组里有对应的值
                                        isVisited[nextRow][nextCol] = true;
                                        myQueue.offer(new int[] {nextRow, nextCol});//把该位置进入Queue，继续BFS
                                    }
                            }
                        }
                        level++;
                    }
                }
            }
        }
        
        int shortest = Integer.MAX_VALUE;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == 0 && reach[i][j] == buildingNum) { //如果当前是个0位置，且其可以到所有Building，它的distance[i][j]才是可以满足条件的Candidate，让其与当前Min比较
                    shortest = Math.min(shortest, distance[i][j]);
                }
            }
        }
        
        return shortest == Integer.MAX_VALUE ? -1 : shortest;
        
        
    }
}
