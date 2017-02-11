class Solution {
        /*
         * 
方法一： 利用JAVA里面的TreeSet这种数据结构，这种数据结构内部查找其实是按BST的，尤其这道题应用了TreeSet里面两个重要的函数，并且一定要理解这两个函数的意思：

floor(E e)： 也就是找对于E来说，在Set里面对于当前E的最高的那个地板，
Returns the greatest element in this set less than or equal to the given element, or null if there is no such element.


ceiling(E e) 也就是找对于E来说，在Set里面对于当前E的最低的那个天花板，
Returns the least element in this set greater than or equal to the given element, or null if there is no such element.

原理是
1） 如果当前在Set里面对于当前当前考察值的最高的那个地板存在，且其由于是大于当前考察值的，那么如果其大于的量在t之内，那么就说明它就是一个满足条件的值，返回即可。

2） 如果当前在Set里面对于当前当前考察值的最高的那个天花板存在，且其由于是小于当前考察值的，那么如果其小于的量在t之内，那么就说明它就是一个满足条件的值，返回即可。

每次对一个考察值检查完毕后，如果当前TreeSet所跨越的窗口已经达到了k,要从当前TreeSet中删除滑动窗口最前面的那个,同时加入现在刚刚检查完毕的考察值
         */
   

public class Solution {
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        int p = 0; 
        if(nums.length==0) return false; 
        if(k==0) return false; 

        TreeSet<Long> set = new TreeSet<>(); 
        for(int i = 0; i<nums.length; i++){
            Long tmp1 = set.ceiling((long)nums[i]); // Changing Integer to Long can pass all test cases.
            Long tmp2 = set.floor((long)nums[i]); // Changing Integer to Long can pass all test cases.
            if(tmp1!=null && tmp1 - nums[i]<=t) return true; //见上面说的1） 
            if(tmp2!=null && nums[i] - tmp2<=t) return true; //见上面说的2） 
            
            if(set.size()==k) set.remove((long)nums[p++]); //如果
            set.add((long)nums[i]); 
        }
        return false; 
    }   
}

/* /* 思路二： 基本想法是桶排序，以t+1的大小作为桶，所以如果两个数如果差在t范围内时，这两个数必定要么在一个桶中，要么在相邻桶中。


The idea is like the bucket sort algorithm. Suppose we have consecutive buckets covering the range of nums with each bucket a width of (t+1). If there are two item with difference <= t, one of the two will happen:

(1) the two in the same bucket
(2) the two in neighbor buckets

比较特别巧的一点是：代码中有个原则是在一个桶中只用存放任意一个应该属于该桶的代表性元素就行了。因为如果是一个桶多余两个元素的话，这已经自动满足<t的条件了，已经返回True了。

# We don't need to store multiple values in a bucket.
        # Because if that is a case, we should return True above.
        # We always update the bucket with the latest (right most) value.
*/
private long getID(long i, long w) {
    return i < 0 ? (i + 1) / w - 1 : i / w;
}

public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
    if (t < 0) return false;
    Map<Long, Long> d = new HashMap<>();
    long w = (long)t + 1;
    for (int i = 0; i < nums.length; ++i) {
        long m = getID(nums[i], w);
        if (d.containsKey(m))
            return true;
        if (d.containsKey(m - 1) && Math.abs(nums[i] - d.get(m - 1)) < w)
            return true;
        if (d.containsKey(m + 1) && Math.abs(nums[i] - d.get(m + 1)) < w)
            return true;
        d.put(m, (long)nums[i]);
        if (i >= k) d.remove(getID(nums[i - k], w));
    }
    return false;
}

