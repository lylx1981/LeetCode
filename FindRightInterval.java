/*思路： 自己差不多也想到了，用Treemap，可以和220题用TreeSet比较。
用来Treemap后，先遍历一遍数组，把每个节点以他们的Start为Kay放入Treemap中。
然后再次遍历数组，寻找当前interval.end为输入参数的 ceiling Entry即可。Ceiling就是说在当前Map里的比当前interval.end大的那个元素中最小的那个。


*/

public class Solution {
    public int[] findRightInterval(Interval[] intervals) {
        int[] result = new int[intervals.length];
        java.util.NavigableMap<Integer, Integer> intervalMap = new TreeMap<>();
        
        for (int i = 0; i < intervals.length; ++i) {
            intervalMap.put(intervals[i].start, i);    
        }
        
        for (int i = 0; i < intervals.length; ++i) {
            Map.Entry<Integer, Integer> entry = intervalMap.ceilingEntry(intervals[i].end);
            result[i] = (entry != null) ? entry.getValue() : -1;
        }
        
        return result;
    }
}
