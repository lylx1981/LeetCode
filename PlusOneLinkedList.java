
public class Solution {
    
    /** 这道题解法有好几种，都可以看看，挺有意思的。主要Solutions来自于 
     * http://www.cnblogs.com/Dylan-Java-NYC/p/5853057.html
     * 
     * Method 1:

        末位加一，若是有进位，就要在 Linked List 的前一个 Node 做改动，自然而然想到先Reverse Linked List. 从头开始做比较方便. 加完再reverse回来.
        
        Time Complexity: O(n). reverse 用O(n), reverse 两次 加上 iterate 一次.
        
        Space: O(1).
     */
     
    //Xu：个人感觉这个代码写的不怎么样 
  public ListNode plusOne(ListNode head) {
        if(head == null){
            return head;
        }
        ListNode tail = reverse(head); //先把链表反转
        ListNode cur = tail;
        ListNode pre = cur;
        int carry = 1;
        
        while(cur != null){
            pre = cur;
            int sum = cur.val + carry;
            cur.val = sum%10;
            carry = sum/10;
            if(carry == 0){
                break;
            }
            cur = cur.next;
        }
        if(carry == 1){
            pre.next = new ListNode(1);
        }
        return reverse(tail); //最后再将Tail反转回来
    }
    
    private ListNode reverse(ListNode head){
       //这部分代码可以参看之前收集到的集合即可
    }
    
    /*
            Method 2:
        
        利用递归，因为递归的终止条件就是到了末尾节点，每层向上返回一个carry数值.
        
        Time Complexity: O(n), 最多每个节点iterate两遍.
        
        Space: O(n), recursion用了n层stack.
    */
    

    public ListNode plusOne(ListNode head) {
        if(head == null){
            return head;
        }
        int carry = dfs(head); //求以head开始的链表加1操作后的余数值
        if(carry == 1){ //余数等于1的话就要生成一个新节点
            ListNode dummy = new ListNode(1);
            dummy.next = head;
            return dummy;
        }
        return head;
    }
    
    private int dfs(ListNode head){
        if(head == null){
            return 1; //head等于null的时候，说明到了数字的最后面，Return 1的效果就是从现在开始进行+1操作
        }
        int carry = dfs(head.next);//求从当前head.next进行+1操作后的余数
        int sum = head.val + carry;
        head.val = sum%10; //计算本位更新后的值，然后回来return 进位值给上一层
        return sum/10;
    
    }
    
    /*
            Method 3:
        
        和Method 2原题相同，用stack代替recursion.
        
        Time Complexity: O(n).
        
        Space: O(n). Stack 大小.
    */

    public ListNode plusOne(ListNode head) {
        if(head == null){
            return head;
        }
        Stack<ListNode> stk = new Stack<ListNode>(); //Stack其实就是先把高位都塞进去，然后后续一个个从低位开始处理，
        //原理其实和递归方法差不多
        
        ListNode cur = head;
        while(cur != null){
            stk.push(cur);
            cur = cur.next;
        }
        int carry = 1; //初始化进位为1，因为要进行加1操作
        //只要Stack不空，已经当前进位为1，那么就一直从stack中取出元素，对当前位进行计算，然后继续update 进位
        while(!stk.isEmpty() && carry == 1){
            ListNode top = stk.pop();
            int sum = top.val + carry;
            top.val = sum%10;
            carry = sum/10;
        }
        //如果while循环完毕，carry还不为空，证明原数字最高位要进位，那么要生成一个新的node,来表示最高位
        if(carry == 1){
            ListNode dummy = new ListNode(1);
            dummy.next = head;
            return dummy;
        }
        return head;
    }
    
    /*
        Method 4:
    
    从右向左寻找第一个不是9的节点，找到后在该节点加一,  若是他后面还有节点, 说明后面的节点都是9, 所以都要变成0.
    
    Time Complexity: O(n).
    
    Space: O(1).
    1. i stands for the most significant digit that is going to be incremented if there exists a carry
    2. dummy node can handle cases such as "9->9>-9" automatically
    */
    
    public ListNode plusOne(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode i = dummy;
        ListNode j = dummy;
        
        //j一直到链表末尾
        while (j.next != null) {
            j = j.next;
            //i是从低位开始算起，第一个不为9的那个高位。那么如果链表最后一位不是9，那么最后i,j都是指向最后一位，是最简单的情况
            if (j.val != 9) {
                i = j;
            }
        }
        
        if (j.val != 9) {
            j.val++; //最低位不为9的话，直接加1，就结束了，最简单的情况
        } else {
            i.val++; //否则j为9的话，会造成进位，而且一直进位到i位，因为i位后面的位都是9（根据前面的讨论可知），所以这些位传递进位后，都为0，i位自己+1，因为最后进位是体现在i位上+1的。
            i = i.next;
            while (i != null) {
                i.val = 0;
                i = i.next;
            }
        }
        //前面的进位所造成的生成一个新节点的情况，在这个程序里的效果为直接dummy的值会变为1。
        //所以这里判断如果dummy的值是0的话，那么说明不需要有新的最高位节点生成，所以还是返回以前的head即可，否则的话，返回dummy.
        if (dummy.val == 0) {
            return dummy.next;
        }
        
        return dummy;
    }
}
