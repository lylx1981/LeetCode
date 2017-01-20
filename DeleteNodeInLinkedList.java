public class DeleteNodeInLinkedList {
    //这道题重点是说，如果只能access a given node, 如何做删除。这也就限制了不能访问given node 前面的点
    public void deleteNode(ListNode node) { 
        if (node == null || node.next == null) return;
        node.val = node.next.val; //所以策略就是把node下一个节点的值挪到当前node上，并且让当前节点指向下一个下一个节点，这就相当于删除了当前node节点的效果，而且不用使用或者访问任何node前面的点 
        node.next = node.next.next;
    }
}
