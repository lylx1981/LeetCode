/**
     * DFS. 方法二：经典递归法，参数只是一个树节点，但每次递归调用过程中，不停的有paths类型的ArrayList生成(而回溯法是通篇只用一个结果集paths) 。而且因为不用回溯，所以参数里也不用记忆当前path到哪了，也就是不用有上面backtrack的第二个参数
     * Recurrence relation:
     * The paths consist of root + all subtrees paths.
     * Base case:
     * If root == null, the resukt list would be empty.
     * If root is a leaf node, then its value would be in list.
     * <p>
     * Just concatenate root's value with those paths returned from subtrees.
     */
    public List<String> binaryTreePaths2(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<String> paths = new ArrayList<>(); //没调用一次，就生成一个paths
        if (root.left == null && root.right == null) {
            paths.add(Integer.toString(root.val));
            return paths;
        }
        // Paths from left subtree.
        for (String path : binaryTreePaths2(root.left)) {
            paths.add(root.val + "->" + path); // Concat root with each path. 把root加到最前面去
        }
        // Paths from right subtree.
        for (String path : binaryTreePaths2(root.right)) {
            paths.add(root.val + "->" + path);
        }
        return paths;
    }
    
    //方法三：九章的另外一个Divide Conquer方法
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> paths = new ArrayList<>();
        if (root == null) {
            return paths;
        }
        
        List<String> leftPaths = binaryTreePaths(root.left);
        List<String> rightPaths = binaryTreePaths(root.right);
        for (String path : leftPaths) {
            paths.add(root.val + "->" + path);
        }
        for (String path : rightPaths) {
            paths.add(root.val + "->" + path);
        }
        
        // root is a leaf
        if (paths.size() == 0) {
            paths.add("" + root.val);
        }
        
        return paths;
    }
}
