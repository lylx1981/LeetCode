/*思路： 贪心算法，预期找最少删多少个，其实这个题也就是可以等效转化为求最多可以有多少个不重叠的Internval，那么为了找最多有多少个，可以用贪心策略，把所有Interval按照end排序，然后每次挑一个其开始时间比上一个挑中的Interval的end晚的Interval（每挑一个计数器加1），这是最优的策略，因为这样能够给后面留最多的可能插入更多的Interval。等得到了最多有多少个不重叠的Interval后，用总个数减去这个数，就是最少删去几个可以使得剩余的Interval不重叠了。

另外，自己想的是可不可以用图论算法，每个Interval是一个节点，凡是每个Interval重叠，就在两个节点间构建一条边。算法就每次删除那个边最多的节点，直到图中只剩下节点而没有边。看了Leetcode评论，发现有人和我有一样的思路，但是不幸的是，这个方法不是最优的，有Counter Example,但是没人给出来。总之，这个方法是不对的。  
    
 ---------------------------------   
It's easy to think that:

If we count the overlapping times with each interval.
Then, for each time we remove the interval with most overlapping interval(let's call it A)
And we update the other intervals' overlapping time which is overlapped with A.
When all interval has zero overlapping times, we get the result.
I know how to do it in greedy in O(nlgn) time, but I wonder why this cant work...
This idea failed in the last test case(that with 10k inputs).I can't figure out why... Can someone help me?please...?
Thanks your time for seeing these. Thank you~



*/

public int eraseOverlapIntervals(Interval[] intervals) {
        if (intervals.length == 0)  return 0;

        Arrays.sort(intervals, new myComparator());
        int end = intervals[0].end;
        int count = 1;        

        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i].start >= end) {
                end = intervals[i].end;
                count++;
            }
        }
        return intervals.length - count;
    }
    
    class myComparator implements Comparator<Interval> {
        public int compare(Interval a, Interval b) {
            return a.end - b.end;
        }
}
