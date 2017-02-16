思路： 用到了前缀和，但是还用到了Hashmap，比较巧的一道题，很有技巧。需要好好琢磨琢磨，可以看看下面的分析。

上来一看感觉和209.Minimum Size Subarray Sum有点像，细思之后发现两个根本不是一回事，209里的数全是正数，这里不是，没什么规律。
前缀和可以解决，需要O(N^2)的时间。需要优化，于是可以把前缀和存在hashmap里，就只需要O(N)的时间了。
hashmap的key是前缀和，value是到第几个数。注意key(表示前缀和)有可能重复(因为有负数)。
注意这道题不要先生成hashmap再遍历map找和，因为key有可能重复，就会把之前的相同key覆盖，越靠前的key产生的subarray越长，就会漏掉答案。
正确的作法是，一边扫描数组生成hashmap一边找和，这样能确保找的都是前面的；同时如果遇到key重复了，先记录下当前sum是否可以找到和为k，可以的话记录max,然后我们丢掉这个key,保留最早的那个，因为最早的可以生成的size更大，当前这个我们也已经examine过了。



public int maxSubArrayLen(int[] nums, int k) {
    int sum = 0, max = 0;
    HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
    for (int i = 0; i < nums.length; i++) {
        sum = sum + nums[i];
        if (sum == k) max = i + 1;//如果当前sum增加到k了，那么初始化长度max就是i+1，因为现在Sum才增长到k,所以他前面还不可能出现两个前缀和之差达到k的Case
        else if (map.containsKey(sum - k)) max = Math.max(max, i - map.get(sum - k));//如果当前Map里面已经有了对应于sum-k的前缀和的话，那么就计算i和那个节点的距离，同时与当前max比较，如果比max大，就更新。map.get(sum - k)就是用来去能够达到sum-k前缀和的那个位置。
        if (!map.containsKey(sum)) map.put(sum, i); //这里保证了如果有重复的Sum的话，我只会存那个最先出现的，后面出现的我都不管了，因为最先出现的才会带给我最长的序列，非常重要的一步！！！！！
    }
    return max;
}

