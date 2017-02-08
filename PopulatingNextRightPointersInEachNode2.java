/* 思路，就是对每一层遍历的时候，有一个遍历指针，同时对于要建立的下一层，同时也建立双指针，一个指向下一层的Head，一个指向下一层的最近刚刚被检查的元素，然后While循环当前层即可便能逐步构建出下一层的一条链表了。循环继续，下一次就从继续判断这个构建出来的链表即可。


*/


public class Solution {
    
    //based on level order traversal
    public void connect(TreeLinkNode root) {

        TreeLinkNode head = null; //head of the next level 下一层的头元素
        TreeLinkNode prev = null; //the leading node on the next level 就是下一层的刚刚被检查过的那个元素
        TreeLinkNode cur = root;  //current node of current level 正在考察的当前层的元素

        while (cur != null) {
            //对于当前层每个元素检查其左右孩子
            while (cur != null) { //iterate on the current level
                //left child
                if (cur.left != null) { //左孩子不为空
                    if (prev != null) {
                        prev.next = cur.left; //如果Pre不是空的话，pre.next现在就应该指向当前这个左孩子
                    } else {
                        head = cur.left; //如果Pre是空的话，那么就初始化下一层的Head
                    }
                    prev = cur.left; //因为cur.left已经被检查过了，现在它就是prev
                }
                //right child //右孩子和上面的左孩子一样
                if (cur.right != null) {
                    if (prev != null) {
                        prev.next = cur.right;
                    } else {
                        head = cur.right;
                    }
                    prev = cur.right;
                }
                //move to next node 当前层的正在考察元素的左右孩子都检查完后，就继续检查当前层的下一个元素
                cur = cur.next;
            }
            
            //move to next level
            cur = head; //head是下一层的第一个元素，所以把它付给Cur，然后外层While循环就会继续开始下一层的判断
            //head,prev重新设置为null
            head = null;
            prev = null;
        }
        
    }
}
