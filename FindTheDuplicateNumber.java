
/*这道题很重要，好好体会二分法的用途。二分法不一致是只能用来在有序数组中用的，这道题就是很好例子  

这道题给了我们n+1个数，所有的数都在[1, n]区域内，首先让我们证明必定会有一个重复数，这不禁让我想起了小学华罗庚奥数中的抽屉原理(又叫鸽巢原理), 即如果有十个苹果放到九个抽屉里，如果苹果全在抽屉里，则至少有一个抽屉里有两个苹果，这里就不证明了，直接来做题吧。题目要求我们不能改变原数组，即不能给原数组排序，又不能用多余空间，那么哈希表神马的也就不用考虑了，又说时间小于O(n2)，也就不能用brute force的方法，那我们也就只能考虑用二分搜索法了，我们在区别[1, n]中搜索，首先求出中点mid，然后遍历整个数组（也就是具体察看每个数组位置上存的值），统计所有小于等于mid的数的个数，如果个数大于mid，则说明重复值在[1, mid-1]之间，反之，重复值应在[mid+1, n] 之间，然后依次类推，直到搜索完成，此时的low就是我们要求的重复值，参见代码如下

实际上，我们可以根据抽屉原理简化刚才的暴力法。我们不一定要依次选择数，然后看是否有这个数的重复数，我们可以用二分法先选取n/2，按照抽屉原理，整个数组中如果小于等于n/2的数的数量大于n/2，说明1到n/2这个区间是肯定有重复数字的。比如6个抽屉，如果有7个袜子要放到抽屉里，那肯定有一个抽屉至少两个袜子。这里抽屉就是1到n/2的每一个数，而袜子就是整个数组中小于等于n/2的那些数。这样我们就能知道下次选择的数的范围，如果1到n/2区间内肯定有重复数字，则下次在1到n/2范围内找，否则在n/2到n范围内找。下次找的时候，还是找一半。

// 如果小于等于中间数的数量大于中间数，说明前半部分必有重复。注意这个是整个数组中小于等于mid的个数，如果这个数大于mid，则可能会重复的元素的具体值肯定是会比mid小（是和mid的值比较，不是和mid上面存的值比较！！ 这里的比较比较绕），所以继续在mid左侧排查（是排查索引，而不是索引上的值，所以才是这个二分法真正Apply的地方）。注意这里的二分法和之前的二分法不太一样，每次统计的Count个数是和mid本身的值比较，而不是和mid位置上的nums[mid]比较，这是最大的不同！！！ 也是这里体现了抽屉原理的应用。从而二分的目的不是说去考虑左侧或者右侧数组中存的元素的具体值，而是说去考察左侧或者右侧的index.

注意：
我们比较的mid而不是nums[mid]
因为mid是下标，所以判断式应为cnt > mid，最后返回min

*/

public class Solution {
    public int findDuplicate(int[] nums) {
        int min = 0, max = nums.length - 1;
        while(min <= max){
            // 找到中间那个数
            int mid = min + (max - min) / 2;
            int cnt = 0;
            // 计算总数组中有多少个数小于等于中间数
            for(int i = 0; i < nums.length; i++){
                if(nums[i] <= mid){
                    cnt++;
                }
            }
              // 如果小于等于中间数的数量大于中间数，说明前半部分必有重复。注意这个是整个数组中小于等于mid的个数，如果这个数大于mid，则可能会重复的元素的具体值肯定是会比mid小（这里的比较比较绕），所以继续在mid左侧排查。注意这里的二分法和之前的二分法不太一样，上面统计的Count个数是和mid本身的值比较，而不是和mid位置上的nums[mid]比较，这是最大的不同！！！ 也是这里体现了抽屉原理的应用。从而二分的目的不是说去考虑左侧或者右侧数组中存的元素的具体值，而是说去考察左侧或者右侧的index.

            if(cnt > mid){
                max = mid - 1;
            // 否则后半部分必有重复
            } else {
                min = mid + 1;
            }
        }
        return min;
    }
}

/*
这个算法更加巧妙，了解即可，比较绕。
经过热心网友waruzhi的留言提醒还有一种O(n)的解法，并给了参考帖子，发现真是一种不错的解法，其核心思想快慢指针在之前的题目Linked List Cycle II中就有应用，这里应用的更加巧妙一些，由于题目限定了区间[1,n]，所以可以巧妙的利用坐标和数值之间相互转换，而由于重复数字的存在，那么一定会形成环，我们用快慢指针可以找到环并确定环的起始位置，确实是太巧妙了！

映射找环法
复杂度
时间 O(N) 空间 O(1)

思路
假设数组中没有重复，那我们可以做到这么一点，就是将数组的下标和1到n每一个数一对一的映射起来。比如数组是213,则映射关系为0->2, 1->1, 2->3。假设这个一对一映射关系是一个函数f(n)，其中n是下标，f(n)是映射到的数。如果我们从下标为0出发，根据这个函数计算出一个值，以这个值为新的下标，再用这个函数计算，以此类推，直到下标超界。实际上可以产生一个类似链表一样的序列。比如在这个例子中有两个下标的序列，0->2->3。

但如果有重复的话，这中间就会产生多对一的映射，比如数组2131,则映射关系为0->2, {1，3}->1, 2->3。这样，我们推演的序列就一定会有环路了，这里下标的序列是0->2->3->1->1->1->1->...，而环的起点就是重复的数。

所以该题实际上就是找环路起点的题，和Linked List Cycle II一样。我们先用快慢两个下标都从0开始，快下标每轮映射两次，慢下标每轮映射一次，直到两个下标再次相同。这时候保持慢下标位置不变，再用一个新的下标从0开始，这两个下标都继续每轮映射一次，当这两个下标相遇时，就是环的起点，也就是重复的数。对这个找环起点算法不懂的，请参考Floyd's Algorithm。

注意
第一次找快慢指针相遇用do-while循环
*/

public class Solution {
    public int findDuplicate(int[] nums) {
        int slow = 0;
        int fast = 0;
        // 找到快慢指针相遇的地方
        do{
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while(slow != fast);
        int find = 0;
        // 用一个新指针从头开始，直到和慢指针相遇
        while(find != slow){
            slow = nums[slow];
            find = nums[find];
        }
        return find;
    }
}
