/**
 * 思路：把所有Interval按照他们开始时间的排序，然后时刻记录当前所有考察过的Interval里最大的End时间。然后遍历每个Interval,如果当前的Interval的开始时间比End小，说明有时间重复，那么直接退出返回False即可。否则，表示当前Interval的开始时间在End后面，所以没有重复发生，这个时候只需要更新当前End就行了（比如当前Interval的结束时间比现在End还晚的话，那么就更新End）。
 */

public class Solution {
    public boolean canAttendMeetings(Interval[] intervals) {
        if(intervals == null || intervals.length == 0) return true;
        Arrays.sort(intervals, new Comparator<Interval>(){
            public int compare(Interval i1, Interval i2){
                return i1.start - i2.start;
            }
        });
        int end = intervals[0].end;
        for(int i = 1; i < intervals.length; i++){
            if(intervals[i].start < end) {
                return false;
            }
            end = Math.max(end, intervals[i].end);
        }
        return true;
    }
}
