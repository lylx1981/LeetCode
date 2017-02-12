
    /*
    思路：基本想法是还按所有会议的开始顺序排序后，依次检查每一个，然后用一个集合来存放当前已经使用过的Meeting Room。对于一个新的会议，如果所有现在最早结束的那个Room仍然不能为这个新的会议所用，那么无论如何我都要为这个新会议再申请一个Room了，否则的话，如果现在最早结束的那个Room已经空闲了，我就直接让这个新的会议用这个Room就可以了，需要做的只是把当前这个已经最早结束的这个Room的再次结束时间更新为新的会议的结束时间。另外，由于每次我都要检查的是那个最早结束的Room，所以用一个Heap的结构来存储Meeting Rooms，其实是最小Heap。这就是这道题要用Heap的原因。
*/

public int minMeetingRooms(Interval[] intervals) {
    if (intervals == null || intervals.length == 0)
        return 0;
        
    // Sort the intervals by start time
    Arrays.sort(intervals, new Comparator<Interval>() {
        public int compare(Interval a, Interval b) { return a.start - b.start; } //这里定义了两个Internal大小主要以其开始时间为准
    });
    
    // Use a min heap to track the minimum end time of merged intervals
    //而在Heap里面，Meeting Room的大小是以其结束时间为准的，结束的越早，值越小。通过这个Comprator，Heap的Root元素总是最早结束的那个Meeting Room
    PriorityQueue<Interval> heap = new PriorityQueue<Interval>(intervals.length, new Comparator<Interval>() {
        public int compare(Interval a, Interval b) { return a.end - b.end; }
    });
    
    // start with the first meeting, put it to a meeting room
    heap.offer(intervals[0]);
    
    for (int i = 1; i < intervals.length; i++) {
        // get the meeting room that finishes earliest
        Interval interval = heap.poll(); //拿出一个最早结束的Meeting Room判断其能不能用。
        
        if (intervals[i].start >= interval.end) {
            // if the current meeting starts right after 
            // there's no need for a new room, merge the interval
            interval.end = intervals[i].end; //能用的话，直接更新其结束时间
        } else {
            // otherwise, this meeting needs a new room
            heap.offer(intervals[i]); //不能用的话，就把当前这个Internal加入到Heap，相当于新申请了一个Room
        }
        
        // don't forget to put the meeting room back
        heap.offer(interval); //不管刚刚从Heap拿出来的那个Interval能不能用，最后都要把它放回到Heap里，因为Anyway，你已经申请了这个Room,其是应该总是存在于Heap里的
    }
    
    return heap.size(); //最后把Heap的大小就是这个过程中你要用到的Meeting Room的个数返回即可。
}
