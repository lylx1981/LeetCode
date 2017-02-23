public class InsertInterval {

    /**
     * Array. In-place solution.
     * 1.从头到尾遍历数组，从头开始先skip所有在新加interal之前完全不重叠的的Interval
     * 2. 对于一个当前判断的intveral， 如果它的end不再小于新加interval的Start，说明存在”潜在重叠“，现在开始深入判断，有几种情况： 
     *进入新的while循环：
     * a 如果当前interval的start小于等于newInterval的end,则说明，必存在overlap （需要合并2个Interval）,那么把当前这个interval从list删除（因为要支持in-place操作），
     * 同时更新newInterval的值以体现合并的效果。然后继续考察下一元素，其中可能导致更多的合并操作，直到while循环退出
     * while循环后的作用是：1. 将新的newInterval加入到restult里。2。还有一种情况是之前根本就没有进入While循环，这是当newInterval完全不和任何一个现有的interval重合的情况，则直接把它加入即可，程序不会进入While。
     * 
     * 注意，这个方法使用了in-place的操作。
     * 
     * Skip the non-overlapping intervals whose end time is before new interval's start.
     * For overlapped intervals that start before new interval end.
     * | Remove this overlapped interval from list.
     * | Merge it with the new interval by: 1. update start to min start times; 2. update end to max end times.
     * Add new interval in the position i.
     */
    public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        int i = 0;
        // Skip the non-overlapping intervals.
        while (i < intervals.size() && intervals.get(i).end < newInterval.start) {
            i++;
        }
        while (i < intervals.size() && intervals.get(i).start <= newInterval.end) { // Overlap.
            // Remove the overlapped interval.
            Interval inter = intervals.remove(i);
            // Merge with new interval.
            newInterval.start = Math.min(inter.start, newInterval.start);
            newInterval.end = Math.min(inter.end, newInterval.end);
        }
        intervals.add(i, newInterval); // Insert new interval.
        return intervals;
    }
      
    
  //上面这个解法Leetcode出现错误，下面这个是Leetcode上推荐的解法，第二次看的时候要重新看看。


public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
    List<Interval> result = new LinkedList<>();
    int i = 0;
    // add all the intervals ending before newInterval starts
    while (i < intervals.size() && intervals.get(i).end < newInterval.start)
        result.add(intervals.get(i++));
    // merge all overlapping intervals to one considering newInterval
    while (i < intervals.size() && intervals.get(i).start <= newInterval.end) {
        newInterval = new Interval( // we could mutate newInterval here also
                Math.min(newInterval.start, intervals.get(i).start),
                Math.max(newInterval.end, intervals.get(i).end));
        i++;
    }
    result.add(newInterval); // add the union of intervals we got
    // add all the rest
    while (i < intervals.size()) result.add(intervals.get(i++)); 
    return result;
}
