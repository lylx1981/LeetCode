public class Solution {
    /*
    思路：用递归。找到当前链表的中点作为划分点（因为要满足BST左右子数是平衡的，平衡的意思就是高度差最多为1，这样的话，最简单的方法就是把链表从中间均匀分开），然后分别对前半部和后半部继续用递归求其BST。   
    
    这种题其实不太好编，因为题不难，但是很容易出现空指针。

    */
   public TreeNode sortedListToBST(ListNode head) {
        if(head==null) return null;
        return toBST(head,null); //注意toBST的参数的意义是所构成子树的第一个节点是head,最后一个节点是tail参数所指节点之前的那个节点。因为一个链表最后一个节点的后面一个节点是null,所以这里设置为null， 也就是说我要对整个链表从头到尾求BST”
    }
    
    public TreeNode toBST(ListNode head, ListNode tail){
        ListNode slow = head;
        ListNode fast = head;
        if(head==tail) return null;
        //这个While循环就是找中点以及链表尾部的。
        while(fast!=tail&&fast.next!=tail){
            fast = fast.next.next;
            slow = slow.next;
        }
        //生成一个新的TreeNode（作为Root），其值就是当前Slow指针对应的点
        TreeNode thead = new TreeNode(slow.val);
        thead.left = toBST(head,slow);//第二个参数设置为Slow，如上面所说，也就是这个构成的BST是不包括Slow所指向的节点的
        thead.right = toBST(slow.next,tail); //第二个参数设置为tail,在第一次循环中这里Tail其实还是null,因为是求整个链表的后半部分所构成的BST 
        return thead;

    }
    
    //上面的是top-down的（每次都要用快慢指针找中点，很慢），还有一种down-top的，复杂度更低，只有O（n），但是比较难理解。
    //可以参看 http://bangbingsyb.blogspot.com/2014/11/leetcode-convert-sorted-list-to-binary.html
     /**
     * Bottom up approach, O(n) (Instead of top-down, O(nlogn)) 
     * 
     * STEP 1: Take left n/2 nodes and recursively construct the left sub tree.
     * 
     * STEP 2: After left sub tree is constructed, we allocate memory for root
     * and link the left sub tree with root.
     * 
     * STEP 3: Finally, we recursively construct the right sub tree and link it
     * with root.
     */
    public static TreeNode buildTree(int n) {
        if (n == 0) return null;
        TreeNode node = new TreeNode(0);
        node.left = buildTree(n / 2);
        node.val = cur.val;
        cur = cur.next;
        node.right = buildTree(n - n / 2 - 1); // why n - n/2 - 1? The # of nodes in right subtreeis total nodes - nodes in left subtree - root
        return node;
    }
}
