/*  思路： 比较简单的一道题，就在这个类里面再定义一个变量叫next,总是把List里面的下一个变量提前放到这个next里面就行了。也就是说，每次调用这个类的Wrapper next()的时候，就直接把当前next变量的值返回，并且再用原生List的next()再从List中取出一个元素放到next变量里。同样的，hasNext只需要检查next变量是不是空就行了。
     */
public class PeekingIterator implements Iterator<Integer> {

    private Integer next;
    private Iterator<Integer> iter;

    /**
     * Peeking iterator is based on normal iterator.
     * Just a wrapper class.
     * Use a variable to cache the next element.
     */
    public PeekingIterator(Iterator<Integer> iterator) {
        // initialize any member here.
        this.iter = iterator;
        if (iterator.hasNext()) {
            next = iterator.next();
        }
    }

    /**
     * Just return the cached element.
     */
    // Returns the next element in the iteration without advancing the iterator.
    public Integer peek() {
        return next;
    }

    /**
     * Return next value.
     * If iterator still has more elements, update next with next.
     * If iterator don't have any more, set next to null.
     */
    // hasNext() and next() should behave the same as in the Iterator interface.
    // Override them if needed.
    @Override
    public Integer next() {
        int res = next;
        next = iter.hasNext() ? iter.next() : null;
        return res;
    }

    /**
     * Just check next.
     */
    @Override
    public boolean hasNext() {
        return next != null;
    }

}
