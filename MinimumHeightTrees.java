

/*

思路：思路：非常巧的方法。 本质上Root到任意叶子节点的最长距离就是高度，那么为了让一个树的高度尽可能小，可以利用双指针从两边往中间逼近的方法。也就是刚开始找出所有的叶子节点，然后对他们进行判断，将这些叶子节点删除后，就会出现一些新的叶子节点，继续对这些新的叶子节点进行操作。如此不断下去，一层一层剥洋葱，最后剩下的那两个节点，就是原Graph中最中间的离所有叶子节点都不是太远也不是太近的点了，因为高度是按照到从Root到叶子节点的最大距离来确定的，所以这样方法找到的就是Root。这个方法非常巧，有点像剥洋葱的方式！！好好体会！！


Our problem want us to find the minimum height trees and return their root labels. First we can think about a simple case -- a path graph.

For a path graph of n nodes, find the minimum height trees is trivial. Just designate the middle point(s) as roots.

Despite its triviality, let design a algorithm to find them.

Suppose we don't know n, nor do we have random access of the nodes. We have to traversal. It is very easy to get the idea of two pointers. One from each end and move at the same speed. When they meet or they are one step away, (depends on the parity of n), we have the roots we want.

This gives us a lot of useful ideas to crack our real problem.

For a tree we can do some thing similar. We start from every end, by end we mean vertex of degree 1 (aka leaves). We let the pointers move the same speed. When two pointers meet, we keep only one of them, until the last two pointers meet or one step away we then find the roots.

It is easy to see that the last two pointers are from the two ends of the longest path in the graph.

The actual implementation is similar to the BFS topological sort. Remove the leaves, update the degrees of inner vertexes. Then remove the new leaves. Doing so level by level until there are 2 or 1 nodes left. What's left is our answer!

The time complexity and space complexity are both O(n).

Note that for a tree we always have V = n, E = n-1.

*/

public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        if (n == 1) return Collections.singletonList(0);
        if (n == 2) return Arrays.asList(0, 1);
        // build graph
        List<Set<Integer>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new HashSet<>()); //转换为邻接表的形式更好操作
        for (int[] edge : edges) {
            adj.get(edge[0]).add(edge[1]);
            adj.get(edge[1]).add(edge[0]);
        }
        // find leaves
        LinkedList<Integer> leaves = new LinkedList<>(); // better memory usage
        for (int i = 0; i < n; i++) {
            if (adj.get(i).size() == 1) leaves.offer(i);
        }

        while (n > 2) { //剩下2个节点的时候，退出就行了
            int numLeaf = leaves.size();
            n -= numLeaf;
            for (int i = 0; i < numLeaf; i++) {
                // update graph
                int curNode = leaves.poll(); //拿一个叶子节点出来，把它从原图中删去，如果导致新的叶子节点出现，则继续加入
                int j = adj.get(curNode).iterator().next();
                adj.get(j).remove(curNode);
                if (adj.get(j).size() == 1) leaves.offer(j); // new leaves
            }
        }
        return leaves;
    }
