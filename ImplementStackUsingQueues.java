

/** 思路：只要在Push新元素的时候，在Push这个操作里，直接把所有元素按照应该从Stack中Pop中的顺序排好（由于是Queue，那么让他们以先进先出的顺序排好即可），这样，接下来Stack的各种操作就和Queue的各种操作一致了。
 * Use queue
 * It would too easy to use Deque
 */
public class MyStack {

        Queue<Integer> queue = new LinkedList<>();

        // Push element x onto stack.
        public void push(int x) {
            queue.offer(x);
            for (int i = 0; i < queue.size() - 1; i++) {
                queue.offer(queue.poll()); //最核心的代码就是这里，非常简单，就是加入新元素后，把整个Queue里的在新加入的x元素之前的元素一个个拿出来再放进去一遍（也就是i只会到queue.size() - 2， 不会动x这个新加的元素，纸上稍微一画就可清楚了，非常巧），这样，最新加入的那个元素就会排到第一位了
            }
        }

        // Removes the element on top of the stack.
        public int pop() {
            return queue.poll();
        }

        // Get the top element.
        public int top() {
            return queue.peek();
        }

        // Return whether the stack is empty.
        public boolean empty() {
            return queue.isEmpty();
        }
}
