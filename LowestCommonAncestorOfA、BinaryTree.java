
/*

非常重要的题，好好消化，体会！！ 
思路：递归，从根节点开始上到下递归判断p,q是不是在当前判断节点的左右子树中。lowestCommonAncestor程序的作用也就是在一个树里确定树中有没有p或者q任意一个存在。

Base步的代码：
if (root == null || root == p || root == q) {
            return root;
} 
这个Base步骤决定了： 1.如果当前判断节点是null,直接返回null。如果是root当前就是p或者q（无所谓哪个），则直接返回root,root现在就直接指向p或者q

对于一个当前考察的Root点:

1) 如果其左子树递归调用的结果不为空（说明其左子树里存在p或者q），右子树递归调用结果也不为空(也说明其右子树里存在p或者q) ，则分别在左右两子树都找到了一个元素，那么当前Root一定就是最低公共父亲了。这种情况是p,q各自都不是各自的先祖，也完全在不同的树的分支里的情况。
2) 如果左右子树两边返回的结果都为空，则表明不存在公共父亲，因为根本都没找到p,q
3) 如果左右子树递归调用返回结果其中只有一个不为空，那么不为空的那个就是最低公共父亲了。原因是根据Base的步骤，不为空的话，返回的一定是指向p或者q的一个指针。但是注意，由于程序递归是从根节点从上到下开始的，那么第一个遇到的p或者q点一定就是两个点的最低公共父亲了。这种情况是p,q本身一方是另一方先祖的情况。

底下英文的描述也是大体的意思：

/**
     * Recursive.
     * Recurrence relation:
     * Search for p and q in left and right subtrees.
     * If both are found, it means the two nodes are in different subtrees, root should be their LCA.
     * If one of them is null, it means no possible LCA found for p or q.
     * Then the one that is not null should be their LCA.
     * Base case:
     * If root is null, return null; if root is p or q, return p or q, respectively.
     */
     


public class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        //发现目标节点则通过返回值标记该子树发现了某个目标结点
        if(root == null || root == p || root == q) return root;
        //查看左子树中是否有目标结点，没有为null
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        //查看右子树是否有目标节点，没有为null
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        //都不为空，说明做右子树都有目标结点，则公共祖先就是本身
        if(left!=null&&right!=null) return root;
        //如果发现了目标节点，则继续向上标记为该目标节点
        return left == null ? right : left;
    }
}
