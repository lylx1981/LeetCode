class Solution {
    /*
     * 思路：思路： 这道题主要难点是怎么在stack的加入或者删除元素操作的同时，能够动态维护最小元素。因为Stack加入元素，删除元素都是有序的，也就是先进后出的，所以最小值也可以以有序的方式存储。这道题用另一个minimum Stack存取不同时期第一个Stack里面的动态最小值，同时每次第一个stack里面对元素有加入或者删除操作，最小Stack都有相应的更新。这道题非常非常巧的思想！！！ 要好好体会！！
        
        
     */
class MinStack {

    private Stack<Integer> s = new Stack<>();
    /**
     * Standard solution, two stacks.
     * A minStack to store minimums.
     */
    private Stack<Integer> minStack = new Stack<>();//用来存储当前的最小值

    /**
     * Push x to stack.
     * After that, check min stack.
     * Push to min stack if min stack is empty or x is smaller than the top of min stack.
     */
    public void push(int x) {
        s.push(x);
        if (minStack.isEmpty() || x <= minStack.peek()) { // even smaller 如果最小Stack是空的，直接加入。或者，如果当前要进Stack的值，比最小Stack里面的Top元素还小，则需要更新当前的最小值
            minStack.push(x);//把X放到最小Stack里，说明x现在是全局最小值了。
        }
    }

    /**
     * Pop from s.
     * Check if the value is current min.
     * If it is, pop from min stack as well.
     */
    public void pop() {
        if (s.pop().equals(minStack.peek())) minStack.pop();//注意这里是两个操作，pop已经把S里面的元素取出了，然后继续比较如果这个元素同时是最小Stack里面的Top元素，因为他已经被删除了，所以这个元素也要从最小Stack里面删除，删除后，最小Stack的Top元素就是没有加入当前删除元素之前的那个最小值，太巧了！！！！
    }

    public int top() {
        return s.peek();
    }
    
    /**
     * Peek min stack to get current minimum.
     */
    public int getMin() {
        return minStack.peek();
    }
}
