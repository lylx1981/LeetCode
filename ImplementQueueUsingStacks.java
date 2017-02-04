class Solution {
    /*
     * 用栈实现队列，这个比用队列实现栈要麻烦一些，这里用到两个栈，两种思路，第一种就是在进栈的时候，把栈逆序放在另外一个栈，出的时候直接出另外一个栈就可以了。或者把栈逆序先放在另外一个栈里，再加入新加的元素，然后再把它倒回到第一个栈里，这样的话，所有后续的操作都是在第一个栈里操作。不管这第一种方法如何实现，会发现，总会在加入一个元素的时候，就要把所有元素倒腾一遍，开销比较大。这样的速度会很慢，只击败了13%的玩家...
     
     
     第二种思路，进栈的时候不做任何处理（当要进行Pop的时候再按需进行处理 ），出栈的时候把栈逆序放在另外一个栈，出另外一个栈。下面就是两种的代码实现。
     
     维护两个栈inStack与outStack，其中inStack接收push操作新增的元素，outStack为pop/peek操作提供服务

由于栈具有后进先出（Last In First Out）的性质，栈A中的元素依次弹出并压入空栈B之后，栈A中元素的顺序会被逆转

当执行pop或者peek操作时，如果outStack中元素为空，则将inStack中的所有元素弹出并压入outStack，然后对outStack执行相应操作

----------由于元素至多只会从inStack向outStack移动一次(这一点非常重要！！！！！！)，因此peek/pop操作的平摊开销为O(1)

注意： 把这个题和“Implement Stack using Queue”比较发现，那道题不存在这道题的优化问题，那道题也不需要两个Queue来模拟Stack（虽然也可以），只需要一个queue就行了，而且每次在Push里面直接将Queue中的元素重新调整顺序的操作无论如何也避免不了（比如如果按照按需操作，刚开始Queue里依次加入了1，2，3，4，当要Pop元素的时候，要想把Queue里的元素顺序全都变为4，3，2，1的话，其所需要的挪动开销和每加入一个元素就调整一下顺序的方法是一样的，而且更麻烦，要把1，2，3都拿出来，再按照3，2，1的顺序插入到原来的Queue中）。不像这道题，当以按需的方式进行工作的时候，的确很多在Push操作中就力图完成调整顺序而带来的来回挪动开销都可以省掉了，所以这道题有这样的优化方法存在。

     */
public class MyQueue {

        Stack<Integer> input = new Stack<>();
        Stack<Integer> output = new Stack<>();


        // Push element x to the back of queue.
        public void push(int x) {
            input.push(x);
        }

        // Removes the element from in front of queue.
        public int pop() {
            peek();
            return output.pop();
        }

        // Get the front element.
        public int peek() {
            if (output.isEmpty()) {
                while (!input.isEmpty())
                    output.push(input.pop());
            }
            return output.peek();
        }

        // Return whether the queue is empty.
        public boolean empty() {
            return input.isEmpty() && output.isEmpty();
        }
}
