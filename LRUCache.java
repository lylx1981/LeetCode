
 /*
思路1：用一个Hashmap，里面每个键值对的K就是用户给的Key，而Value里面存的是其对应的内部双向链表的节点（而节点中的Value存的才是之前用户给的Value）。这里提到另外使用的是一个双向链表，作用是来随时调整节点的顺序，每次新插入的一个(K,V)键值对如果在当前Hashmap里面还没有的话，就将生成一个新的对应这个键值对的双向链表Node，同时这个Node会插在链表最前面（直接在Head指针后面）。同时，如果是对某个已有的键值对进行Get操作，每次被访问后的其对应的Node节点也都调整到链表最前面。也就是说，双向链表最末尾就是LRU的最近最少使用的了，如果需要替换的话（比如以达到Cache最大Capacity），就替换链表末尾的就行了。注意双向链表的好处有很多，另外，收尾都用两个Dummy Node（head 和Tail）这样就不用为Null操心。所以每次Get(key)的操作要分两步，先是取到该Key对应的Node，然后再取Node里面的值，另外，因为这个Node刚被访问过来，还要把它调整到第一个去，代表这是最近刚使用过的。

这道题需要好好看看，非常简单，但是很多设计细节，特别重要！！！！面试的时候很可能会遇到

另外，自己想到的另外一个方案是用Queue来记录动作，具体是每个get结束后，就让元素进Queue，同时如果Queue最前面的第一个元素是和刚get的元素的一样的话，那就让Queue一直弹出直到不是刚Get的元素为止。这样，如果一旦Cache Capacity满了，要删除的话，就删Queue当前Pop出来的元素即可。另外，Put元素的话，就直接让元素进Queue。粗想觉得这是一个好方法，但是其实是不行的，因为元素有删除后又加入的情况，这样的话，Queue里面存的都可能是很多过期的记录，所以Queue这个方法不行。又仔细想来一下发现,Queue方法完全是错的，不能用。
*/

import java.util.HashMap;
import java.util.Map;

/** 也可以看看下面的英文描述，也很清楚。
 * The problem can be solved with a hashtable that keeps track of the keys and its values in the double linked list. One interesting property about double linked list is that the node can remove itself without other reference. In addition, it takes constant time to add and remove nodes from the head or tail.

One particularity about the double linked list that I implemented is that I create a pseudo head and tail to mark the boundary, so that we don't need to check the NULL node during the update. This makes the code more concise and clean, and also it is good for the performance as well.

 * <p>
 * Use 2 data structures to implement an LRU Cache:
 * 1. A queue which is implemented by a doubly linked list. The max size of the queue will be equal to cache size. Put
 * least recently used at the tail.
 * 2. A hash map with Node's value as key and the Node as value.
 * 3. A dummy head and a dummy tail of the queue. So that we can access both head and tail fairly quick.
 */
class LRUCache {

    private final int capacity;
    private final Map<Integer, Node> cache;
    private final Node head;
    private final Node tail;

    /**
     * Remember capacity.
     * Create cache map and double linked list.
     */
    public LRUCache(int capacity) {
        this.capacity = capacity;
        cache = new HashMap<>();
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.prev = head;
    }

    /**
     * Check key in cache.
     * If not in cache, return -1.
     * If in cache, get the node, move it to head, return its value.
     */
    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1;
        }
        Node node = cache.get(key); //依赖Key先拿到Node，
        moveToHead(node);
        return node.val;//Value在Node里面存着呢
    }

    /**
     * If key already in cache:
     * | Get the node, update its value, move to head.
     * If key is not in cache:
     * | Create a new node.
     * | Add it to list and cache.
     * | If cache size exceeds capacity:
     * |   Get the last node.
     * |   Remove it from list and cache.
     */
    public void set(int key, int value) {
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            node.val = value;
            moveToHead(node); //因为刚访问过node,现在要把它挪到最前面
        } else {
            Node newNode = new Node(key, value);
            addNode(newNode);
            cache.put(key, newNode);
            if (cache.size() > capacity) { //当容量超过的时候，要剔除掉LRU那个，现在也就是把链表上最后一个Node删除。
                Node last = tail.prev;
                removeNode(last);
                cache.remove(last.key);
            }
        }
    }

    /**
     * Remove node from list and add it to head.
     */
    private void moveToHead(Node node) {
        removeNode(node);
        addNode(node);
    }

    /**
     * Remove a node from double linked list.
     */
    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    /**
     * Add a node after head.
     */
    private void addNode(Node node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    /**
     * Double linked list node.
     * With previous node, next node, key, and value.
     */
    class Node {

        Node prev;
        Node next;
        int key;
        int val;

        public Node() {
        }

        public Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }
}
