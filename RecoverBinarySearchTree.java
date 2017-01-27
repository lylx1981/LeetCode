/*
思路：这道题有更麻烦的办法，所谓Morris Traversal。当前这个方法比较巧，而且可以学到很多技巧。主要是利用中序遍历，因为中序遍历BST的话应该是个有序的序列


例子：1，5，3，4，2，6，7
大致策略如下：
1.每次我们记录下前一个元素，如果当前节点的值比前一个值要小，说明我们就找到了第一个错误节点的位置。我们的例子中就是3比5小，5就是第一个错误的节点（注意这里稍微在程序里体现5是在preElement所值的位置上）。
2.然后继续往下搜索，找到第二个这样的元素，也就是2，那么注意，这里2是第二个错误的节点（注意这里稍微在程序里体现2是在Root所值的位置上）。



但是这道题最重要学习的是，如何在递归方法里进行一些改动和操作，比如如何在中序递归的程序里，设置一个前驱标记？？

 private void traverse(TreeNode root) {
        
        if (root == null)
            return;
            
        traverse(root.left);
        
       //只要在这里（也就是两个对左右子树遍历的递归调用之间）写上关于root的相关操作，就一定造成了中序的效果

        // 这里只需要考虑如何对Root进行操作就可以了。
        
        prevElement = root; //比如程序中的这一句代码已经足可以让prevElement指向当前考察节点的前驱功能了。巧！注意这个方法，这道题最重要的就是这一步，牢牢记住这个用法。

        traverse(root.right);
        
        
以下英文Leetcode的分析也是大体意思。

This question appeared difficult to me but it is really just a simple in-order traversal! I got really frustrated when other people are showing off Morris Traversal which is totally not necessary here.

Let's start by writing the in order traversal:

private void traverse (TreeNode root) {
   if (root == null)
      return;
   traverse(root.left);
   // Do some business
   traverse(root.right);
}
So when we need to print the node values in order, we insert System.out.println(root.val) in the place of "Do some business".

What is the business we are doing here?
We need to find the first and second elements that are not in order right?

How do we find these two elements? For example, we have the following tree that is printed as in order traversal:

6, 3, 4, 5, 2

We compare each node with its next one and we can find out that 6 is the first element to swap because 6 > 3 and 2 is the second element to swap because 2 < 5.

Really, what we are comparing is the current node and its previous node in the "in order traversal".

Let us define three variables, firstElement, secondElement, and prevElement. Now we just need to build the "do some business" logic as finding the two elements. See the code below:
*/

public class Solution {
    
    TreeNode firstElement = null;
    TreeNode secondElement = null;
    // The reason for this initialization is to avoid null pointer exception in the first comparison when prevElement has not been initialized
    TreeNode prevElement = new TreeNode(Integer.MIN_VALUE);
    
    public void recoverTree(TreeNode root) {
        
        // In order traversal to find the two elements
        traverse(root);
        
        // Swap the values of the two nodes
        int temp = firstElement.val;
        firstElement.val = secondElement.val;
        secondElement.val = temp;
    }
    
    private void traverse(TreeNode root) {
        
        if (root == null)
            return;
            
        traverse(root.left);
        //在两个traverse之间写代码，就能保证是中序遍历
        // Start of "do some business", 
        // If first element has not been found, assign it to prevElement (refer to 6 in the example above)
        if (firstElement == null && prevElement.val >= root.val) {
            firstElement = prevElement; //第一个异常点是当前prevElement
        }
    
        // If first element is found, assign the second element to the root (refer to 2 in the example above)
        if (firstElement != null && prevElement.val >= root.val) {
            secondElement = root;////而第2个异常点是当前root
        }        
        prevElement = root;

        // End of "do some business"

        traverse(root.right);
}
