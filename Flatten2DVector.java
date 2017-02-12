
    /*
    思路：这道题其实就是如何应用两个内部的Iterator，因为为了遍历每个Vector需要一个Interator，同时对于每个Vector里面的元素遍历，也需要一个Interator。
*/

public class Vector2D {

    private Iterator<List<Integer>> i; //Vector之间的Iterator
    private Iterator<Integer> j; //当前正在考察的Vector内部的Iterator

    public Vector2D(List<List<Integer>> vec2d) {
        i = vec2d.iterator();
    }

    public int next() {
        hasNext(); //注意这里要有一行这个，主要考虑是如果next（）单独使用的话，也能确保不出问题。虽然大多数情况下我们都是先调用hasNext(),如果是True的话，我们再调用next().
        return j.next();
    }

    public boolean hasNext() {
        //如果j还是Null没有初始化，或者j不空，但是已经到达当前Vector的末尾了，同时当前Vector后面还有Vector，
        //这个时候就重新设置j,j的值就是下一个Vector的内部Interator。
        //这里用While的意思可能是对于特殊情况，比如下一个Vector是个只含有0个元素的空数组情况，这样，我们就直接跳过，继续考察下一个Vector
        while ((j == null || !j.hasNext()) && i.hasNext())
            j = i.next().iterator();
        return j != null && j.hasNext(); //如果看看j是不是空，同时不空的话，j.hasNext()也有元素，这样才返回True。
    }
}
