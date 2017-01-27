/*
思路：我自己想到的，和下面代码基本类似，就是用BFS，非递归的方法。序列化时按层一层一层加入，空孩子用“ ”表示即可。
反序列化的时候，所有节点在一个序列化好的数组里。还是用Queue，同时用一个记录器i来获取当前考察元素的孩子。
每取出Queue里面的一个元素进行考察，i都要挪两步，因为其有两个左右孩子。

*/

public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if (root==null) return "";
        Queue<TreeNode> qu=new LinkedList<>();
        StringBuilder sb=new StringBuilder();
        qu.offer(root);
        sb.append(String.valueOf(root.val));
        sb.append(' ');
        while (!qu.isEmpty()) {
            TreeNode x=qu.poll(); //对当前x进行考察，同时这一轮会将x的左右孩子继续加到Queue里
            if (x.left==null) sb.append("null ");
            else {
                qu.offer(x.left);
                sb.append(String.valueOf(x.left.val));
                sb.append(' ');
            }
            if (x.right==null) sb.append("null ");
            else {
                qu.offer(x.right);
                sb.append(String.valueOf(x.right.val));
                sb.append(' ');
            }
        }
        return sb.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data.length()==0) return null;
        String[] node=data.split(" ");
        Queue<TreeNode> qu=new LinkedList<>();
        TreeNode root=new TreeNode(Integer.valueOf(node[0]));
        qu.offer(root);
        int i=1; //i 从1开始，因为node[0]是Root，所以直接从Root的左孩子开始
        while (!qu.isEmpty()) {
            Queue<TreeNode> nextQu=new LinkedList<>();
            while (!qu.isEmpty()) {
                TreeNode x=qu.poll();
                if (node[i].equals("null")) x.left=null;
                else {
                    x.left=new TreeNode(Integer.valueOf(node[i]));
                    nextQu.offer(x.left);
                }
                i++;
                if (node[i].equals("null")) x.right=null;
                else {
                    x.right=new TreeNode(Integer.valueOf(node[i]));
                    nextQu.offer(x.right);
                }
                i++; //每轮里面i都要向前挪两步
            }
            qu=nextQu;
        }
        return root;
    }
}

/* 方法二：递归方法，用先序便利，注意这里再次应用了如何在递归的遍历中添加技术细节的技巧

For example, you may serialize the following tree
 * <p>
 * |   1
 * |  / \
 * | 2   3
 * |    / \
 * |   4   5
 
 
 

*/

public class SerializeAndDeserializeBinaryTree {

    private static final String DELIMITER = ",";
    private static final String NULL_NODE = "#";

    /**
     * Recursive.
     * Pre-order traversal with root and a string builder.
     */
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        buildString(root, sb);
        return sb.toString();
    }

    /**
     * Recursive. Pre-order traversal.
     * Append current node's val and a delimiter.
     * Then recurse down to left and right subtrees.
     * Base case:
     * If node is null, append a null node and a delimiter.
     * => 1,2,#,#,3,4,#,#,5,#,#,
     */
    private void buildString(TreeNode node, StringBuilder sb) {
        if (node == null) {
            sb.append(NULL_NODE).append(DELIMITER);
            return;
        }
        sb.append(node.val).append(DELIMITER);
        //在对Node的左右孩子处理前，进行上述这些操作，从而实现了先序便利的效果！！！！！
        
        buildString(node.left, sb);
        buildString(node.right, sb);
    }

    /**
     * Recursive.
     * Same as pre-order traversal.
     * Split data and create a queue of string values first.
     * Poll a value string from the queue.
     * If null node, return null.
     * Create a tree node with value.
     * Then build left and right subtrees recursively.
     */
    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        Deque<String> nodes = new ArrayDeque<>();
        nodes.addAll(Arrays.asList(data.split(DELIMITER))); //nodes是一个Quene，一个个取出判断即可
        return buildTree(nodes);
    }

    private TreeNode buildTree(Deque<String> nodes) {
        // Get a value from queue and build node.
        String val = nodes.poll(); //取出当前判断node
        if (NULL_NODE.equals(val)) {
            return null;
        }
        TreeNode node = new TreeNode(Integer.valueOf(val)); //
        
        
        
         //在对Node的左右孩子处理前，进行上述这些操作，从而实现了先序便利的效果！！！！！
        
        node.left = buildTree(nodes); // Build left subtree. 因为现在Nodes已经取出了当前第一个元素，也就是node了，所以再次递归调用(nodes此时已经不包含node)，就是得到的以node左孩子为根的树
        node.right = buildTree(nodes); // Build right subtree. //同理，当前nodes已经把node.左孩子在上面的调用中去除了，现在得到的是以node右孩子为根的树
        return node;
    }
