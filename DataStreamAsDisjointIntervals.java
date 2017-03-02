
/* 用TreeMap，比较有意思，注意熟悉一下Treemap，已经它的几个非常重要的函数。另外，在处理的时候，比较有细节的注意。

From JAVA Doc - TreeMap： A Red-Black tree based NavigableMap implementation. The map is sorted according to the natural ordering of its keys, or by a Comparator provided at map creation time, depending on which constructor is used.

higherKey(K key) -- Returns the least key “strictly” greater than the given key, or null if there is no such key.
lowerKey(K key)-- Returns the greatest key “strictly” less than the given key, or null if there is no such key.

ceilingKey(K key)-- Returns the least key greater than or equal to the given key, or null if there is no such key.
floorKey(K key) -- Returns the greatest key less than or equal to the given key, or null if there is no such key.

Use TreeMap to easily find the lower and higher keys, the key is the start of the interval.
Merge the lower and higher intervals when necessary. The time complexity for adding is O(logN) since lowerKey(), higherKey(), put() and remove() are all O(logN). It would be O(N) if you use an ArrayList and remove an interval from it.

*/

/**
 * Definition for an interval. 注意Interval的数据结构
 * public class Interval {
 *     int start;
 *     int end;
 *     Interval() { start = 0; end = 0; }
 *     Interval(int s, int e) { start = s; end = e; }
 * }
 */


public class SummaryRanges {
    TreeMap<Integer, Interval> tree; //注意Tree里面Value存的是Interval类型的变量，Key存的是那个Interval的Start起点的值

    public SummaryRanges() {
        tree = new TreeMap<>();
    }

    public void addNum(int val) {
        if(tree.containsKey(val)) return;
        Integer l = tree.lowerKey(val); //也就是比当前Value小的最大的那个Key，这个Key也就代表是某个Interval的起点
        Integer h = tree.higherKey(val); //也就是当前Value大的最小的那个Key，这个Key也就代表是某个Interval的起点
        if(l != null && h != null && tree.get(l).end + 1 == val && h == val + 1) { //下面那个Interval的End+1就是当前Value，并且上面那个Interval的Start起点就是Val+1，现在合并l和h对应的两个Interval
            tree.get(l).end = tree.get(h).end;
            tree.remove(h); //合并后把h对应的键值对去掉，因为那个Interval已经不存在了。
        } else if(l != null && tree.get(l).end + 1 >= val) { //说明下面那个Interval的跨度+1大于Val（这里面有2中情况，也许是end本身就比val大了，那么这个时候val已经就被下面这个interval包含了，或者end+1正好等于val），不管什么情况，我就都对下面这个Interval的End取当前两者最大即可，有必要的话就更新，没必要的话这个操作也没有影响。同时这里我不用删除当前这个Interval，因为我们只是更新了它的end, 而TreeMap里表达一个Interval的key是用Start起点当作Key的，所以这里不影响
            tree.get(l).end = Math.max(tree.get(l).end, val);
        } else if(h != null && h == val + 1) { //这个情况说明val+1触摸到了上面那个/interval的开始位置了，这个时候，更新上面那个Interval，让其从val开始即可。同时删除老的Interval（因为这个Interval的起点Start变了，所以老的键值对不能再用了）
            tree.put(val, new Interval(val, tree.get(h).end));
            tree.remove(h);
        } else {
            tree.put(val, new Interval(val, val)); //将自己存入，自己Value作为Key，（val,val）作为Value
        }
    }

    public List<Interval> getIntervals() {
        return new ArrayList<>(tree.values());//注意这个写法，非常简洁，就把一个TreeMap直接转换为ArrayList的格式了。
    }
}
