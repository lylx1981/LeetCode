/* 思路： 感觉这道题还是比较绕的，用来一些数序的知识，并且关于如何把元素放入桶中的设计非常巧，使得我们可以不管同一个桶中的元素之间的比较。
利用桶排序求解。首先，遍历数组nums[]求得min和max。假设数组共有N个数，则所求的解必然大于等于gap=(max-min)/(N-1)(向上取整，且在N个数均匀分布时取得)。接着再遍历一次数组，将(nums[i]-min)/gap作为下标，放入相应的桶中（也就是以Gap为步长建立一系列N-1个桶，每个桶只接受满足那个步长范围的元素，也就是k-th bucket contains all numbers in [min + (k-1)gap, min + k*gap)）。其中，每个桶只需要维护该桶内的最大值和最小值即可（这点很重要，省了很多空间）。因为，每个桶里面所有的数最大不会相差len，所以桶内部是不存在解的（因为刚才已经证明了所求解必然大于Gap，），因此解只可能在相邻的桶中获得，即后一个桶的最小值减去前一个桶的最大值是可能的解。最后再从这些差值中取得一个最大值即可。


Suppose there are N elements in the array, the min value is min and the max value is max. Then the maximum gap will be no smaller than ceiling[(max - min ) / (N - 1)].

Let gap = ceiling[(max - min ) / (N - 1)]. We divide all numbers in the array into n-1 buckets, where k-th bucket contains all numbers in [min + (k-1)gap, min + k*gap). Since there are n-2 numbers that are not equal min or max and there are n-1 buckets, at least one of the buckets are empty. We only need to store the largest number and the smallest number in each bucket.

After we put all the numbers into the buckets. We can scan the buckets sequentially and get the max gap.
my blog for this problem*/

public class Solution {
    public int maximumGap(int[] num) {
        if (num == null || num.length < 2)
            return 0;
        // get the max and min value of the array
        int min = num[0];
        int max = num[0];
        for (int i:num) {
            min = Math.min(min, i);
            max = Math.max(max, i);
        }
        // the minimum possibale gap, ceiling of the integer division
        int gap = (int)Math.ceil((double)(max - min)/(num.length - 1));
        int[] bucketsMIN = new int[num.length - 1]; // store the min value in that bucket
        int[] bucketsMAX = new int[num.length - 1]; // store the max value in that bucket
        Arrays.fill(bucketsMIN, Integer.MAX_VALUE);
        Arrays.fill(bucketsMAX, Integer.MIN_VALUE);
        // put numbers into buckets
        for (int i:num) {
            if (i == min || i == max)
                continue; //注意这道题把min,max没有放在桶中，所以要单独考虑。也可以不这么做
            int idx = (i - min) / gap; // index of the right position in the buckets
            bucketsMIN[idx] = Math.min(i, bucketsMIN[idx]);
            bucketsMAX[idx] = Math.max(i, bucketsMAX[idx]);
        }
        // scan the buckets for the max gap
        int maxGap = Integer.MIN_VALUE;
        int previous = min;//previous首先设置为min
        for (int i = 0; i < num.length - 1; i++) {
            if (bucketsMIN[i] == Integer.MAX_VALUE && bucketsMAX[i] == Integer.MIN_VALUE)
                // empty bucket
                continue;
            // min value minus the previous value is the current gap //当前的Min减去前面的Max就是当前求的的Gap，Previous一直存的是前一个的Max
            maxGap = Math.max(maxGap, bucketsMIN[i] - previous);
            // update previous bucket value
            previous = bucketsMAX[i];
        }
        maxGap = Math.max(maxGap, max - previous); // updata the final max value gap 注意这道题把min,max没有放在桶中，所以要单独考虑。最后再让Max与Previous减一下，看看结果
        return maxGap;
    }
}
