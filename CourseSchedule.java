
/*
思路： 明显一看就是Topologic Sort.比较喜欢BFS方法，和自己想的一样，用Queue。用入度为0的点开始放入Queue（在后面的循环中，凡是当前课程已经没有任何前序课程的课程才可以进Queue）。每从Queue拿出一个点，就检查该点的邻居，检查过程中，不断让其邻居的入度减一（因为当前从Queue里拿出来的这门课已经检查过了，其对该邻居的影响已经没有了）。当一个课程的入度减小到0的时候，说明这个点现在也可以进Queue了。 用一个计数器记录多少课程进入了Queue，如果最后退出循环后，Queue一共进入了和课程树一样的数目，则说明课程可以休完。


另外注意题目中说The input prerequisites is a graph represented by a list of edges, not adjacency matrices.而代码接口是int[][] prerequisites，刚开始一直想不通，以为这明明是adjacency matrices。最后想明白了，这个prerequisites其实就是一个n*2的数组，每一行都只是一个只包含两个元素的一维数组(a pair) ，也就是描述了一条Edge，也就是形如[[1,4],[5,7]...]

同时还有DFS方法，但是Leetcode上评价最多的就是这个BFS方法。

*/
public class CourseSchedule {

    /**
     * Topological Sort. BFS.
     * Detect whether a cycle exists in a directed graph.
     * Also make sure it's connected.
     * Implementation:
     * Build an array of in-degrees of each node.
     * Add all 0 in-degree node to the queue.
     * Count the number of nodes visited.
     * Remove the node from graph by updating in-degrees of all nodes it connects.
     * If in-degree becomes 0, add the node to queue.
     * Return whether count is numCourses.
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        if (numCourses <= 0) {
            return false;
        }
        // Build in degrees array for each node.
        int[] inDegrees = new int[numCourses];
        for (int[] p : prerequisites) {
            inDegrees[p[0]]++; //如题意 对于prerequisites，其每个元素p就是一个包含2个元素的数组/同时，数组p的第2个课程才是先序课程，所以应该对第一个元素算入度
        }
        // Enqueue all 0 in-degree nodes.
        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < inDegrees.length; i++) {
            if (inDegrees[i] == 0) {
                queue.offer(i);
            }
        }
        // Toposort.
        int count = 0;
        while (!queue.isEmpty()) {
            int c = queue.poll(); // Dequeue node and add to result.
            count++;
            for (int[] p : prerequisites) {
                if (c == p[1]) { // Remove c from graph.
                    inDegrees[p[0]]--; // Reduce in-degree.
                    if (inDegrees[p[0]] == 0) {
                        queue.offer(p[0]);
                    }
                }
            }
        }
        return count == numCourses;
    }

    /**
     * Topological Sort. DFS.
     * Detect whether there is a cycle.
     * Build an adjacency list with edge list.
     * The DFS toposort on the graph.
     */
    public boolean canFinishB(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>(numCourses);
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        boolean[] visited = new boolean[numCourses];
        for (int i = 0; i < prerequisites.length; i++) {
            graph.get(prerequisites[i][1]).add(prerequisites[i][0]);
        }

        for (int i = 0; i < numCourses; i++) {
            if (!dfs(graph, visited, i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Toposort. DFS.
     * Check temporary mark. If temporary mark is true, there is a cycle. Return false.
     * Set temporary mark to true.
     * Visit all neighbors first.
     * Set temporary mark to false.
     * Return true.
     */
    private boolean dfs(List<List<Integer>> graph, boolean[] visited, int course) {
        if (visited[course]) { // Cycle detected.
            return false;
        }
        visited[course] = true; // Set temp mark.
        for (int n : graph.get(course)) {
            if (!dfs(graph, visited, n)) {
                return false;
            }
        }
        visited[course] = false; // Reset temp mark.
        return true;
    }

}
