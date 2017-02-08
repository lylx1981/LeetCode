/** 
      * 思路：因为不能用Extra Space，所以不能用Queue这样的方法。但具体的想法还是从Root开始一层一层做，对于每一层，同时从其最左边的节点开始（用cur表示），逐个处理他们的左右孩子，比如让cur的左孩子的next指向cur的右孩子，同时让cur的右孩子的next指向当前cur的next的节点的左孩子（因为cur的next指针已经在上一轮设置好了）。 做完一个节点cur就向右移动一个节点直到这一层做完。最后把下一层继续开始的节点设置为当前层开始节点的左孩子即可 （level_start=level_start.left;）。
     
     */
     public void connect(TreeLinkNode root) {
        TreeLinkNode level_start=root;
        while(level_start!=null){
            TreeLinkNode cur=level_start;
            while(cur!=null){
                if(cur.left!=null) cur.left.next=cur.right;
                if(cur.right!=null && cur.next!=null) cur.right.next=cur.next.left;
                
                cur=cur.next;
            }
            level_start=level_start.left;
        }
    }
    
    
     /** 
      * 方法二：用递归的方法，貌似更简单，就是简单的设置两个左右孩子自己内部的next指针，以及他们之间的next指针即可，就是不知道这个递归方法符不符合题目的要求。
     
     */
     
     
    public void connect(TreeLinkNode root) {
      if(root == null) return;    
      helper(root.left, root.right);    
    }
    
    void helper(TreeLinkNode node1, TreeLinkNode node2){
      if(node1 == null) return;    
      node1.next = node2;
      helper(node1.left, node1.right);
      helper(node2.left, node2.right);
      helper(node1.right, node2.left);
    }
