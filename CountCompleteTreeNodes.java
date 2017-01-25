
       /**假如左子树高度等于右子树高度，则右子树为完全二叉树，左子树为满二叉树。
    假如高度不等，则左字数为完全二叉树，右子树为满二叉树。
    求高度的时候只往左子树来找。
    
    完全二叉树Complete Binary Tree是指除了最后一行以外，每一行都是满的。如果最后一行存在节点，那么这些节点满足从左到右排列。
    满二叉树Full Binary Tree是指所有的节点的子节点要么为空要么有两个子节点。而满二叉树的节点总数是 (2 ^ h) - 1，这里h是满二叉树的高度。
    知道了以上性质，我们就可以进行解题
    先构造一个getHeight方法， 用来求出二叉树的高度。这里我们只用求从根节点到最左端节点的长度。
    求出根节点左子树高度leftHeight和根节点右子树高度rightHeight
    假如两者相等，那么说明左子树是满二叉树，而右子树可能是完全二叉树。
    我们可以返回  (2 ^ leftHeight) - 1 + 1  + countNodes(root.right)
    这里+1是因为把根节点也算进去，化简一下就是 1 << leftHeight + countNodes(root.right)，返回结果
    否则两者不等，说明左子树是完全二叉树，右子树是满二叉树
    我们可以返回 (2^ rightHeight) - 1 + 1 + countNodeS(root.left)
    化简以后得到 1 << rightHeight + countNodes(root.left)，返回结果
    这里getHeight()方法的时间复杂度是O(logn)， countNodes()方法的时间复杂度也是O(logn)，所以总的时间复杂度是O(logn * logn)
    空间复杂度是递归栈的深度，是O(logn)
    
    做2的幂时不要用Math.pow，这样会超时。用1<<height这个方法来得到2的幂

     */
     
    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftHeight = fullTreeHeight(root.left);
        int rightHeight = fullTreeHeight(root.right);

        if (leftHeight == rightHeight) {
            return (1 << leftHeight) + countNodes(root.right);
        } else {
            return (1 << rightHeight) + countNodes(root.left);
        }
    }

    private int fullTreeHeight(TreeNode root) {
        if (root == null) return 0;
        int res = 0;
        while (root != null) {
            root = root.left;
            res++;
        }
        return res;
    }
