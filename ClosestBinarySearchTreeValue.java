/*
使用inorder traversal来遍历树，因为中序便利是有序的，那么如果我一定要填满k个值的话，初始开始就是最小的和为头k个最小元素的和。
接下来，我只需要不停的像滑动窗口一样，一次往右挪一位看看是不是待新加入的元素是不是比滑动窗口的第一个元素离target更近（因为滑动窗口中的其他元素都仍然会保留，所以不用管他们，只需要看当前滑动窗口的第一个和要新加的元素，谁离target近就行了。注意判断近的话，需要用Math.abs() 函数）。
如果待新加入窗口的元素并不离Target更近的话，那么原来的已经是最优解了，因为
继续向右滑动，只会越来越比原来的差，因为他们的和只会越来越大。

另外，这里Inorder使用的是递归的写法，这里再次体现了如何在递归的程序里，进行相关代码的编写。
比如这里的所有重要代码都是在中序便利左，右子树之间编写的。




*/

public class Solution {
    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        List<Integer> res = new LinkedList<Integer>();
        getClosestKValues(res, root, target, k);
        return res;
    }
    public void getClosestKValues(List<Integer> res, TreeNode root, double target, int k){
        if(root == null) return;
        
        getClosestKValues(res, root.left, target, k); //先处理完左子树
        
        //-----
        
        if(res.size() == k){ //如果左子树的个数已经是k个了，那么就开始进行滑动窗口的判断
            if(Math.abs(res.get(0) - target) > Math.abs(root.val - target)){
                res.remove(0); //如果当前Root离target更近，则删除第一个元素，加入当前元素，也就是滑动窗口向右挪一位，继续判断
                res.add(root.val);
            }
            else
                return; //否则的话，说明当前已经是最优结果了，直接返回即可
        }
        else //如果res.size() < k, 窗口还没有填满，那么就直接继续填就行了。同时注意，不会出现res.size() > k的情况
            res.add(root.val);
            
        //----------
        
        getClosestKValues(res, root.right, target, k); //上述对Root处理完后，再处理右子树，这样就达到了中序遍历的效果
    }
}
