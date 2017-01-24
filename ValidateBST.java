/**     方法一：非常巧的方法，设定min,max来递归判断。min,max每次都以当前判断节点的值进行相应更新，然后再去判断当前节点的左右子树看符不符合BST的要求
     * Recursive.
     * Build a helper function with range.
     * Check whether root's val is in range.
     * Then check left and right recursively.
     * Will fail if input include Integer MAX and Integer MIN.
     */

    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE); //注意这里要用Long，否则Leetcode过不去
    }
    
    public boolean isValidBST(TreeNode root, long minVal, long maxVal) {
        if (root == null) return true;
        if (root.val >= maxVal || root.val <= minVal) return false;
        return isValidBST(root.left, minVal, root.val) && isValidBST(root.right, root.val, maxVal);
    }


    /** 方法二：这个方法也不错，因为按照中序便利一个BST后，就是一个有序数组，只需要判断这个数组是不是有序的就行了。
     * Recursive, in-order.
     * Inorder traversal, generate a list.
     * The list should be in increasing order.
     */
    public boolean isValidBSTC(TreeNode root) {
        if (root == null) {
            return true;
        }
        List<Integer> result = new ArrayList<Integer>();
        inOrderList(root, result);
        for (int i = 0; i < result.size() - 1; i++) {
            if (result.get(i) >= result.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    private void inOrderList(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }
        inOrderList(root.left, res);
        res.add(root.val);
        inOrderList(root.right, res);
    }

    /** 方法三：先序遍历，根据BST性质，给定一个Root，就判断它是不是比左子数最右边那个大，且比右子数最左边那个小就行了。
     * //如果以上两个条件都满足，那么在此基础上继续判断Root左右子树是不是BST就行了。
     * Recursive, pre-order.
     * Check if root.val is bigger than value of rightmost node in left subtree.
     * And smaller than value of leftmost node in right subtree.
     */
    public boolean isValidBSTD(TreeNode root) {
        if (root == null) {
            return true;
        }
        TreeNode temp = null;
        if (root.left != null) {
            temp = root.left; //找左子数最右边那个
            while (temp.right != null) {
                temp = temp.right;
            }
            if (temp.val >= root.val) {
                return false;
            }
        }
        if (root.right != null) {
            temp = root.right; //找右子数最左边那个
            while (temp.left != null) {
                temp = temp.left;
            }
            if (temp.val <= root.val) {
                return false;
            }
        }
        //如果以上两个条件都满足，那么在此基础上继续判断Root左右子树是不是BST就行了。
        return isValidBST(root.left) && isValidBST(root.right); 
    }
