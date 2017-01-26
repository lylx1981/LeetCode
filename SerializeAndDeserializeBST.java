/*
思路：这道题没有太多的技巧，主要就是考虑BST的性质，然后决定用Preorder来序列化树就行了，数组出来的形式是第一个是Root，然后后面一直比他小的都是左子树，当出现比Root大的，就是右子树的节点了。
然后在反序列化的时候，采用递归+queue就行。每次对一个当前考察的Root，求出其后面所有比其小的，那么继续进行递归调用。


Hi all, I think my solution is pretty straightforward and easy to understand, not that efficient though. And the serialized tree is compact.
Pre order traversal of BST will output root node first, then left children, then right.

root left1 left2 leftX right1 rightX
Pre order traversal is BST's serialized string. I am doing it iterativ
To deserialized it, use a queue to recursively get root node, left subtree and right subtree.

I think time complexity is O(NlogN).
Errr, my bad, as @ray050899 put below, worst case complexity should be O(N^2), when the tree is really unbalanced.

*/

public class Codec {
    private static final String SEP = ",";
    private static final String NULL = "null";
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        if (root == null) return NULL;
        //traverse it recursively if you want to, I am doing it iteratively here
        Stack<TreeNode> st = new Stack<>();
        st.push(root);
        //下面是非常经典的先序便利，注意先放Right，再放left,Stack的性质
        while (!st.empty()) {
            root = st.pop();
            sb.append(root.val).append(SEP);
            if (root.right != null) st.push(root.right);
            if (root.left != null) st.push(root.left);
        }
        return sb.toString();
    }

    // Decodes your encoded data to tree.
    // pre-order traversal
    public TreeNode deserialize(String data) {
        if (data.equals(NULL)) return null;
        String[] strs = data.split(SEP);
        Queue<Integer> q = new LinkedList<>();
        for (String e : strs) {
            q.offer(Integer.parseInt(e));
        }
        return getNode(q);
    }
    
    // some notes:
    //   5
    //  3 6
    // 2   7
    private TreeNode getNode(Queue<Integer> q) { //q: 5,3,2,6,7
        if (q.isEmpty()) return null;
        TreeNode root = new TreeNode(q.poll());//root (5)
        Queue<Integer> samllerQueue = new LinkedList<>();
        while (!q.isEmpty() && q.peek() < root.val) {
            samllerQueue.offer(q.poll()); //samllerQueue用来存放当前考察Root节点所有左子树节点
        }
        //smallerQueue : 3,2   storing elements smaller than 5 (root)
        root.left = getNode(samllerQueue);
        //进行到这里后，原来的q里面只剩比Root大的节点了，也就是右子树节点，所以直接继续递归就可以了。
        //q: 6,7   storing elements bigger than 5 (root)
        root.right = getNode(q);
        return root;
    }
}

