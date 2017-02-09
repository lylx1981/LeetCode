/* 思路: 个人比较倾向于使用HashMap以及BFS的方法。BFS就是用Queue。Idea就是从第一个节点开始，对其每一个邻居进行考察（这些邻居都是原来图里的Node），每遇到一个新的Label，如果它不在现在的Map里，则做如下2个动作：1）就生成一个对应它的新的Clone Node，并加入到Map里，Map的Key就是Label值，Value就是对应的新的Clone Node节点，注意Map里存放的都是Clone的节点。2）对于这个邻居，也就是原来图里的Node，同时将其加入到Queue里即可（注意Queue里面存放都是老节点）。 

当然，如果判断当前的这个邻居已经被Clone过，同时其Clone对象已经在Map里面了，那么这个时候，只需要更新其Clone节点的Neibor就行了，注意其Clone节点的Neighbor是应该从Map里面找的，而不是原来的老节点，这个是特别要注意的（只要用Lable去找就行了，因为老节点之间是用Label去体现的，而Clone节点之间也是用Lable是体现的）。  

感觉这道题特别重要，思路要特别清晰，一定要认真掌握！！！
         
*/

public class CloneGraph {

    /**
     * BFS. O(V) Time. O(V) Space.
     * Use map<Integer, UndirectedGraphNode> to represent the new graph and a visited set.
     * For each visit, connect the node with neighboring nodes.
     * If neighboring nodes not in new graph yet, need to create them.
     * Visit:
     * Check whether current label is in new graph:
     * | If not, create a new node with current label and put in map.
     * If neighbors exist:
     * | For each neighbor:
     * |   If its not visited, add it to queue and create a new node.
     * |   Connect current node with this neighbor.
     */
    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        if (node == null) {
            return null;
        }
        Queue<UndirectedGraphNode> q = new ArrayDeque<>();
        Map<Integer, UndirectedGraphNode> graph = new HashMap<>(); // New graph, also a visited set.
        q.offer(node);
        graph.put(node.label, new UndirectedGraphNode(node.label));
        while (!q.isEmpty()) {
            UndirectedGraphNode cur = q.poll();
            if (cur.neighbors != null) {
                for (UndirectedGraphNode n : cur.neighbors) {
                    // Add all unvisited neighbors to the queue.
                    if (!graph.containsKey(n.label)) {
                        q.offer(n); //注意q里面存放的都是老节点，不是Clone的节点，特别注意！！
                        graph.put(n.label, new UndirectedGraphNode(n.label));
                    }
                    // Connect new node with its neighbor.
                    graph.get(cur.label).neighbors.add(graph.get(n.label));//这里注意是从graph里面找对应的点，因为graph里的才是Clone的节点
                }
            }
        }
        return graph.get(node.label); //最后返回Graph里面对应node节点的Clone对象即可。
    }

    /**
     * DFS. Backtracking.
     * Pass the node and map to its neighbors.
     * Add neighbors backtrack result to its neighbors and return.
     */
    public UndirectedGraphNode cloneGraphB(UndirectedGraphNode node) {
        Map<Integer, UndirectedGraphNode> map = new HashMap<>();
        return dfs(node, map);
    }

    /**
     * Statement: Given a node, and a graph map to build, return the cloned node.
     * Sub-problem: Build neighbors.
     * Complete task: Build current node. Build neighbors. Connect current node with its neighbors.
     * Base case: If current node is null, return null.
     * Implementation:
     * For each node in original node's neighbors:
     * | If new graph doesn't have it:
     * |   DFS to copy it and add returned copy node to clone's neighbor.
     * | If already have, means it's built:
     * |   Add it to clone's neighbor.
     * Return cloned node.
     */
    private UndirectedGraphNode dfs(UndirectedGraphNode node, Map<Integer, UndirectedGraphNode> map) {
        if (node == null) {
            return null;
        }
        if (!map.containsKey(node.label)) { // Not visited.
            map.put(node.label, new UndirectedGraphNode(node.label)); // Add to new graph.
        }
        UndirectedGraphNode clone = map.get(node.label);
        for (UndirectedGraphNode n : node.neighbors) {
            if (!map.containsKey(n.label)) { // Only DFS unvisited neighbors.
                clone.neighbors.add(dfs(n, map));
            } else { // Add visited neighbors from map directly.
                clone.neighbors.add(map.get(n.label));
            }
        }
        return clone;
    }

    class UndirectedGraphNode {

        int label;
        List<UndirectedGraphNode> neighbors;

        UndirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<>();
        }
    }
}
