/*
思路： 比较简单的一道题，和自己想的差不多。就是用一个List来存储所有Vector自己的Interator，每next()一次，就List头取一个Interator出来，用其取出一个元素后，如果它还没有空，就再把它加回到List末尾去。

Uses a linkedlist to store the iterators in different vectors. Every time we call next(), we pop an element from the list, and re-add it to the end to cycle through the lists.

*/

public class ZigzagIterator {
    LinkedList<Iterator> list;
    public ZigzagIterator(List<Integer> v1, List<Integer> v2) {
        list = new LinkedList<Iterator>();
        if(!v1.isEmpty()) list.add(v1.iterator());
        if(!v2.isEmpty()) list.add(v2.iterator());
    }

    public int next() {
        Iterator poll = list.remove(); //注意JAVA中这个函数是吧Head取出来
        int result = (Integer)poll.next();
        if(poll.hasNext()) list.add(poll); //如果还有元素的话，再把这个Interator加到list末尾
        return result;
    }

    public boolean hasNext() {
        return !list.isEmpty(); //只要List不为空，整个hasNext就是True。
    }
}
