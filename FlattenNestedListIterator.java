/*思路： 比较直观的能想到用Stack，有各种不同的版本，下面这个版本是Leetcode上评论最好的，特点是其直接从后向前来向stack里加东西，不仅加List，单个数字也加，然后每次从Stack里Pop就行了。Hasnext调用过程中，如果Pop出来的是一个List，就继续将这个List中的元素从后往前继续往Stack里面加就行了，然后继续While循环，直到继续拿出Stack的pop出来的是一个单个元素为止。如果直接说一个数，那就直接返回True，


A question before this is the Nested List Weight Sum, and it requires recursion to solve. As it carries to this problem that we will need recursion to solve it. But since we need to access each NestedInteger at a time, we will use a stack to help.

In the constructor, we push all the nestedList into the stack from back to front, so when we pop the stack, it returns the very first element. Second, in the hasNext() function, we peek the first element in stack currently, and if it is an Integer, we will return true and pop the element. If it is a list, we will further flatten it. This is iterative version of flatting the nested list. Again, we need to iterate from the back to front of the list.


*/

public class NestedIterator implements Iterator<Integer> {
    Stack<NestedInteger> stack = new Stack<>();
    public NestedIterator(List<NestedInteger> nestedList) {
        for(int i = nestedList.size() - 1; i >= 0; i--) {
            stack.push(nestedList.get(i));
        }
    }

    @Override
    public Integer next() {
        return stack.pop().getInteger(); //由于每次都会在next()调用之前调用hasNext()，所以总是能保证现在Stack里面pop出来的一定是一个单元素
    }

    @Override
    public boolean hasNext() {
        while(!stack.isEmpty()) {
            NestedInteger curr = stack.peek();
            if(curr.isInteger()) {
                return true;
            }
            stack.pop();
            for(int i = curr.getList().size() - 1; i >= 0; i--) {
                stack.push(curr.getList().get(i));
            }
        }
        return false;
    }
}

/* 另外一个方法就是直接对List首先进行打平操作，递归调用flatten，然后放在一个一维List里，之后就全部太简单了。
但是貌似这个方法不太推荐
First flatten the list to a list of Integer by using DFS, then just call the plain next() and hasNext()
*/
public class NestedIterator implements Iterator<Integer> {

private List<Integer> flattenedList;
private Iterator<Integer> it;

public NestedIterator(List<NestedInteger> nestedList) {
    flattenedList = new LinkedList<Integer>();
    flatten(nestedList);
    it = flattenedList.iterator();
}

private void flatten(List<NestedInteger> nestedList) {
    for (NestedInteger i : nestedList) {
        if (i.isInteger()) {
            flattenedList.add(i.getInteger());
        } else {
            flatten(i.getList());
        }
    }
}

@Override
public Integer next() {
    return it.next();
}

@Override
public boolean hasNext() {
    return it.hasNext();
}

