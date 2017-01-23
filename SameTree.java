//递归
public static boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null || q == null) return p == q; // if one of them is null, it will return false. both null, true.
        return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right); // equal val, equal subtrees
    }
