
/*
思路： 这道题比较有意思，自己想的差不多，但是注意Design的题目没有完全的对与错，就看分析的是不是有道理。最简单的方法就是使用Queue，没来一个HiT放进Queue即可，并且暂时不用做其他动作。只有在GetHit被调用的时候，这时候根据当前时间，开始从Queue中Pop出元素以删除已经在300秒以外的元素，然后因为每次一个Hit在Queue里占据了一个位置，所以Queue的长度直接就是Sum，所以更新完Queue后，直接返回Sum就行。

关于这个方法自己的另外的考虑： 但是这样的话，不太能Scale，如果一秒钟有大量的数据来的话，每个Hit都占据一个位置，一个改进的方法是把每秒中来的所有Hit求和加起来再进入Queue。，这样的话，每次调用GetHit的时候，就要对Queue中所有元素求和才可以，这就要导致需要把Queue中所有元素拿出来才行，明显这不是一个好的办法。那么另外的一种方法就是再写一个Manitaince的函数，这个函数每一秒都对Sum进行更新，比如当前这一秒新的Sum的值应该是老的Sum，减去Queue中Pop出来那个元素的值（ 其也就是在这一秒过期的那个Hit的个数），然后如果这一秒还有新Hit的话，再加上这一秒新的Hit，那么就得到了当前最新的Sum。这样的话每次GetHit直接就返回Sum就行（而且达到了O(1)的操作），因为Sum一直都是时新的。但是可能题目要求不能有这样的Maintainace的函数存在，所以可能不符合题意，但是这是一种潜在的设计机制。

另外Leetcode评论最多的Solution，是用桶，也比较巧，建议看看体会体会。

In this problem, I use a queue to record the information of all the hits. Each time we call the function getHits( ), we have to delete the elements which hits beyond 5 mins (300). The result would be the length of the queue : )
*/

public class HitCounter {
        Queue<Integer> q = null;
        /** Initialize your data structure here. */
        public HitCounter() {
            q = new LinkedList<Integer>();
        }
        
        /** Record a hit.
            @param timestamp - The current timestamp (in seconds granularity). */
        public void hit(int timestamp) {
            q.offer(timestamp);
        }
        
        /** Return the number of hits in the past 5 minutes.
            @param timestamp - The current timestamp (in seconds granularity). */
        public int getHits(int timestamp) {
            while(!q.isEmpty() && timestamp - q.peek() >= 300) {
                q.poll();
            }
            return q.size();
        }
}


/*
思路2：Leetcode评论最多的Solution，是用桶。基本思想是滑动窗口的思路，每来一个Hit，就扔进其对应的桶里，同时如果桶对应的时间戳已经过期了，那么更新其为当前Hit的时间戳 （这个非常巧）。 而对于GetHit操作，比较费时间，需要把所有300秒之内的桶都遍历一遍求和，然后得到Sum。

In this problem, I use a queue to record the information of all the hits. Each time we call the function getHits( ), we have to delete the elements which hits beyond 5 mins (300). The result would be the length of the queue : )

O(s) s is total seconds in given time interval, in this case 300.
basic ideal is using buckets. 1 bucket for every second because we only need to keep the recent hits info for 300 seconds. hit[] array is wrapped around by mod operation. Each hit bucket is associated with times[] bucket which record current time. If it is not current time, it means it is 300s or 600s... ago and need to reset to 1.

O(1) hit() O(s) getHits() 
*/

public class HitCounter {
    private int[] times;
    private int[] hits;
    /** Initialize your data structure here. */
    public HitCounter() {
        times = new int[300];
        hits = new int[300];
    }
    
    /** Record a hit.
        @param timestamp - The current timestamp (in seconds granularity). */
    public void hit(int timestamp) {
        int index = timestamp % 300;
        if (times[index] != timestamp) { //说明现在桶的时间戳已经过期了，更新它
            times[index] = timestamp;
            hits[index] = 1;//同时因为该桶已经过期了，所以桶中的Hit数量应该重新开始记录，设置其为1，也就是记录现在加入的这个新的Hit
        } else {
            hits[index]++;
        }
    }
    
    /** Return the number of hits in the past 5 minutes.
        @param timestamp - The current timestamp (in seconds granularity). */
    public int getHits(int timestamp) {
        int total = 0;
        for (int i = 0; i < 300; i++) {
            if (timestamp - times[i] < 300) { //只有桶的时间戳在300秒之内的桶才计算到Total里。有些桶还会存在这里，并且时间按戳已经过期的原因是最近300秒内一直没有落入该桶的Hit
            //这个地方的的确非常巧，值得学习哦！
                total += hits[i];
            }
        }
        return total;
    }
}
