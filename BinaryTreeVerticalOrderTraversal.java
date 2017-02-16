

/*

思路：非常好的一道题。自己的思路和下面的完全一样，刚开始觉得比较难，但是仔细想想后发现BFS非常简单。一层一层考察，并且记录每个节点的列，每个左孩子会比现在的列更靠左一列，每个右孩子会比现在的列更靠右一列。

用两个Queue，一个存放要判断的节点，一个存放该节点所属的列


The following solution takes 5ms.

BFS, put node, col into queue at the same time
Every left child access col - 1 while right child col + 1
This maps node into different col buckets
Get col boundary min and max on the fly
Retrieve result from cols
Note that TreeMap version takes 9ms.

Here is an example of [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15]. Notice that every child access changes one column bucket id. So 12 actually goes ahead of 11.



*/

public List<List<Integer>> verticalOrder(TreeNode root) {
    List<List<Integer>> res = new ArrayList<>();
    if (root == null) {
        return res;
    }
    
    Map<Integer, ArrayList<Integer>> map = new HashMap<>(); //存放每个列里面应该包含的节点列表
    Queue<TreeNode> q = new LinkedList<>(); //存放节点
    Queue<Integer> cols = new LinkedList<>(); //存放节点对应的列

    q.add(root); 
    cols.add(0);

    int min = 0;
    int max = 0;
    
    while (!q.isEmpty()) {
        TreeNode node = q.poll(); //取出当前q里面的一个节点
        int col = cols.poll(); //同时取出它对应的列值
        
        if (!map.containsKey(col)) {
            map.put(col, new ArrayList<Integer>()); //如果该列目前还没有在Map里有相应的键值对，就生成一个
        }
        map.get(col).add(node.val); //将当前节点加入到对应列的节点列表里，因为是先处理左孩子，后处理右孩子，那么自动保证了题目的另一个要求，也就是If two nodes are in the same row and column, the order should be from left to right.

        if (node.left != null) { //如果左孩子不空
            q.add(node.left); //继续把左孩子加入q
            cols.add(col - 1); //同时左孩子对应的列应该是col-1,同时把这个列也加入到Cols里
            min = Math.min(min, col - 1); //如果有必要，更新最小列值
        }
        
        if (node.right != null) {
            q.add(node.right);
            cols.add(col + 1);
            max = Math.max(max, col + 1);
        }
    }
    //对于所有出现的列，把其在Map里对应的节点列表依次取出放入res里，就是最终结果了
    for (int i = min; i <= max; i++) {
        res.add(map.get(i));
    }

    return res;
}
