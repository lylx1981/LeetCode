/*
思路： 99%和Course Schedule这道题一样，用Topological Sort和BFS Queue，只是每次每个Course出Queue的时候，把这门课加入到最终修课表里就行了。如果最终有可解的修课表，那么就返回修课表，如果不能修完所有课，也就是无解的话，那直接返回一个空修课表就行了。
*/
public class Solution {

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
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] resSchedule = new int[numCourses];
        int[] emptySchedule = new int[0]; //定义一个空数组，用于无解的时候返回用。注意这里定义一个空数组的方法！！！
        if (numCourses <= 0) {
            return emptySchedule;
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
            resSchedule[count] = c; //把这个课加入最终修课表即可 -----------------和Course Schedule地方不同的一行代码
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
        if(count == numCourses) { //没有解的话就返回空数组 -----------------和Course Schedule地方不同的一行代码
            return resSchedule;
        } else {
            return emptySchedule;
        }
        
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
