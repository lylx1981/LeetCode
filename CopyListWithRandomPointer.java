
    /**  
     * 思路：
     * 
/*第一遍扫的时候巧妙运用next指针， 开始数组是1->2->3->4  。 然后扫描过程中 先建立copy节点 1->1`->2->2`->3->3`->4->4`, 然后第二遍copy的时候去建立边的copy， 拆分节点, 一边扫描一边拆成两个链表，这里用到两个dummy node。第一个链表变回  1->2->3 , 然后第二变成 1`->2`->3`  

An intuitive solution is to keep a hash table for each node in the list, via which we just need to iterate the list in 2 rounds respectively to create nodes and assign the values for their random pointers. As a result, the space complexity of this solution is O(N), although with a linear time complexity.

As an optimised solution, we could reduce the space complexity into constant. The idea is to associate the original node with its copy node in a single linked list. In this way, we don't need extra space to keep track of the new nodes.

The algorithm is composed of the follow three steps which are also 3 iteration rounds.
//重要！！！ 
1. Iterate the original list and duplicate each node. The duplicate of each node follows its original immediately.
2. Iterate the new list and assign the random pointer for each duplicated node.
3. Restore the original list and extract the duplicated nodes.

*/

//No HashMap version
public class Solution {
    private void copyNext(RandomListNode head) {
        while (head != null) {
            RandomListNode newNode = new RandomListNode(head.label);
            newNode.random = head.random;
            newNode.next = head.next;
            head.next = newNode;
            head = head.next.next;
        }
    }

    private void copyRandom(RandomListNode head) {
        while (head != null) {
            if (head.next.random != null) { //head.next其实就是head的复制对象，而且只对random指针不为null的情况进行处理就够了
                head.next.random = head.random.next; //head.random就是head的random所指对象，然后，那个对象的next,其实就是它的复制对象，这个地方很巧！！！
            }
            head = head.next.next;
        }
    }
    //怎么把复制对象从长链表中Extrace出来，这里需要感受一下
    private RandomListNode splitList(RandomListNode head) {
        RandomListNode newHead = head.next;
        while (head != null) {
            RandomListNode temp = head.next;
            head.next = temp.next;
            head = head.next;
            if (temp.next != null) {
                temp.next = temp.next.next;
            }
        }
        return newHead;
    }

    public RandomListNode copyRandomList(RandomListNode head) {
        if (head == null) {
            return null;
        }
        copyNext(head); //对数组进行拷贝
        copyRandom(head); // 对Random指针进行处理
        return splitList(head); 把数组Split
    }
    
//HashMap version， 这种方法更简单，就是用Hashmap一直保存原节点和Copy节点的映射
//但是因为用了Hashmap，会有O（n）的复杂度
public class Solution {
    public RandomListNode copyRandomList(RandomListNode head) {
        if (head == null) {
            return null;
        }

        HashMap<RandomListNode, RandomListNode> map = new HashMap<RandomListNode, RandomListNode>();
        RandomListNode dummy = new RandomListNode(0);
        RandomListNode pre = dummy, newNode;
        while (head != null) {
            if (map.containsKey(head)) {
                newNode = map.get(head);
            } else {
                newNode = new RandomListNode(head.label);
                map.put(head, newNode);
            }
            pre.next = newNode;

            if (head.random != null) {
                if (map.containsKey(head.random)) {
                    newNode.random = map.get(head.random);
                } else {
                    newNode.random = new RandomListNode(head.random.label);
                    map.put(head.random, newNode.random);
                }
            }

            pre = newNode;
            head = head.next;
        }

        return dummy.next;
    }
}


}
