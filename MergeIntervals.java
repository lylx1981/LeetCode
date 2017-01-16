
public class MergeIntervals {
    
        /* 把interval按“开始点 starting point "的先后顺序排序，一个个判断。比如如果新考察的interval与当前正在合并的interval (start end来标记) 有重叠，则合并这两个interval（只需把当前inteval的end部分更新就好了）然后继续考察下一个interval(可能会有进一步的合并) 。start end代表当前正在合并区间的范围。
          如果没有重叠，则找到了一个完整的合并完成的interval, 那么把它加进result里，同时下来从一个新的interval开始，那么start,end都要重新设置。
          
        The idea is to sort the intervals by their starting points. Then, we take the first interval and compare its end with the next intervals starts. As long as they overlap, we update the end to be the max end of the overlapping intervals. Once we find a non overlapping interval, we can add the previous "extended" interval and start over.
    
    Sorting takes O(n log(n)) and merging the intervals takes O(n). So, the resulting algorithm takes O(n log(n)).
    
    I used an anonymous comparator and a for-each loop to try to keep the code clean and simple.
    */

    public List<Interval> merge(List<Interval> intervals) {
        if (intervals.size() <= 1)
            return intervals;
        
        // Sort by ascending starting point using an anonymous Comparator
        Collections.sort(intervals, new Comparator<Interval>() {
            @Override
            public int compare(Interval i1, Interval i2) {
                return Integer.compare(i1.start, i2.start);
            }
        });
        
        List<Interval> result = new LinkedList<Interval>();
        int start = intervals.get(0).start;
        int end = intervals.get(0).end;
        
        for (Interval interval : intervals) {
            if (interval.start <= end) // Overlapping intervals, move the end if needed
                end = Math.max(end, interval.end);
            else {                     // Disjoint intervals, add the previous one and reset bounds
                result.add(new Interval(start, end));
                start = interval.start;
                end = interval.end;
            }
        }
        
        // Add the last interval
        result.add(new Interval(start, end));
        return result;
    }

}      
