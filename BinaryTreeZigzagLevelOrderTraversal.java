public class Solution {
    /*
        用两个Stack，对当前层对应的Stack1逐个取出每个节点时，将每个节点的左右孩子分别再加入另一个Stack2里。每层结束后，将Stack2赋值给Stack1，继续下一层。。
        同时一层是先加左孩子再加右孩子，一层是先加右孩子再加左孩子，交替执行。
        
    */
    
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
       // ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();//九章的源代码是这样的，但是Leetcode过不去，必须使用下面一行代码，另外九章的函数返回值也是ArrayList<ArrayList<Integer>>类型的，而Leetcode上原来的题目要求返回值是如上List<List<Integer>>类型的。注意List是抽象类型
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Stack<TreeNode> currLevel = new Stack<TreeNode>(); //当前层的节点，取出他们的同时，将他们的孩子加入到nextLevel Stack 里面
        Stack<TreeNode> nextLevel = new Stack<TreeNode>();
        Stack<TreeNode> tmp;
        
        currLevel.push(root);
        boolean normalOrder = true; //标记位，标记应该按什么顺序加入孩子节点，正常顺序是先左孩子后右孩子

        while (!currLevel.isEmpty()) {
            ArrayList<Integer> currLevelResult = new ArrayList<Integer>();

            while (!currLevel.isEmpty()) {
                TreeNode node = currLevel.pop();
                currLevelResult.add(node.val);

                if (normalOrder) {
                    if (node.left != null) {
                        nextLevel.push(node.left);
                    }
                    if (node.right != null) {
                        nextLevel.push(node.right);
                    }
                } else {
                    if (node.right != null) {
                        nextLevel.push(node.right);
                    }
                    if (node.left != null) {
                        nextLevel.push(node.left);
                    }
                }
            }

            result.add(currLevelResult);
            tmp = currLevel;//其实现在currLevel里面没有元素，只是一个空Stack
            currLevel = nextLevel; //将nextLevel赴给currLevel，进入下一层
            nextLevel = tmp;
            normalOrder = !normalOrder; //同时改变加入孩子节点的顺序
        }

        return result;

    }
}
