  /*
        思路：经典backtracking DFS
        
    */
    
     public List<List<Integer>> pathSum(TreeNode root, int sum) {
        //ArrayList<ArrayList<Integer>> rst = new ArrayList<ArrayList<Integer>>(); //九章原来的代码
        List<List<Integer>> rst = new ArrayList<>(); //要想Leetcode通过，必须这样写
        ArrayList<Integer> solution = new ArrayList<Integer>(); //存放当前的路径

        findSum(rst, solution, root, sum);
        return rst;
    }

    private void findSum( List<List<Integer>> result, ArrayList<Integer> solution, TreeNode root, int sum){
        if (root == null) {
            return;
        }

        sum -= root.val;//将Sum减去当前考查Root的值

        if (root.left == null && root.right == null) { //说明是一个叶子节点
            if (sum == 0){
                solution.add(root.val);
                result.add(new ArrayList<Integer>(solution));
                solution.remove(solution.size()-1); //回溯操作
            }
            return;
        }

        solution.add(root.val);
        //分别对左右子树继续进行递归操作，sum值是减去当前root的值后的更新值
        findSum(result, solution, root.left, sum);
        findSum(result, solution, root.right, sum);
        solution.remove(solution.size()-1);//回溯操作
    }
}
