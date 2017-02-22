/*思路：这道题非常好，尤其是考虑问题的方法，非常值得学习。主要思路是根据seqs里面的子序列构建出一个图，然后对这个图进行Topoligical Sort（依赖Queue和BFS），看看对这个图进行Topological Sort后得出的排序序列结果是不是org, 非常巧！！！！ 特别的，排序过程中推出的每一个排序节点都和当前org对应的节点比较（比如排序出的第一个节点，就和org的第一个节点比较），如果不同，那么说明存在多余一个的 Topoligical Sort的排序序列，那么就返回False。

另外可以看看207题，也是Topoligical Sort的应用
*/


public class Solution {
    public boolean sequenceReconstruction(int[] org, int[][] seqs) {
        Map<Integer, Integer> indegree = new HashMap<>();
        Map<Integer, List<Integer>> graph = new HashMap<>();
        // set up graph
        for (int[] seq: seqs) {
            if (seq.length == 1) {
                if (!indegree.containsKey(seq[0]))    indegree.put(seq[0], 0);
                if (!graph.containsKey(seq[0]))   graph.put(seq[0], new ArrayList<Integer>());
            } else {
                for (int i = 0; i < seq.length-1; i++) {
                    int prev = seq[i];
                    int next = seq[i+1];
                    // indegree-related
                    if (!indegree.containsKey(prev))    indegree.put(prev, 0);
                    if (!indegree.containsKey(next))    indegree.put(next, 0);
                    indegree.put(next, indegree.get(next)+1); //统计每个节点的入度，因为Topoligical排序从依照入度进行BFS
                    // edge-related
                    if (!graph.containsKey(prev))   graph.put(prev, new ArrayList<Integer>());
                    if (!graph.containsKey(next))   graph.put(next, new ArrayList<Integer>());
                    graph.get(prev).add(next);
                }
            }
        }
        // topological sort
        Queue<Integer> queue = new LinkedList<>();
        for (int node: indegree.keySet()) {
            if (indegree.get(node) == 0)    queue.offer(node);
        }
        int index = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            if (size > 1)   return false; // when size > 1, it means that there multiple ways of polling elements 也就是每一次Queue里面一直都只有一个元素
            int poll = queue.poll();
            // index shouldnot arrive to the end of array yet
            // check whether index==org.length to avoid the invalid getting element of org[index]
            // uniqueness of sequence, we need to compare with org's corresponding element
            if (index == org.length || poll != org[index])  return false; //Poll出来的这个元素一定必须就是org[index]，这样org才继续有可能是这个图的唯一的Topological排序序列
            index++;
            for (int nb: graph.get(poll)) {
                int nb_indegree = indegree.get(nb) - 1;
                indegree.put(nb, nb_indegree); //更新入度，如果当前节点入度将为0，则入Queue
                if (nb_indegree == 0)   queue.offer(nb);
            }
        }
        return index == org.length && index == indegree.size(); // check cycle index必须和Org长度一样，也就是图中输出的Topological 序列已经完全覆盖了org里面的所有节点了。另外，index == indegree.size()这一句应该是为了保证所有在Seq里面的出现过的点，都被覆盖到了）。
    }
}
