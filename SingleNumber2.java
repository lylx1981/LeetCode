/*
这道题主要有两种思路，下面的这个最最最牛B，容易理解，而且都可以推广到k的情况。
Idea是：整数因为有32位，所以就对32位的每个位进行检查即可。针对每一个位，对数组中的元素进行遍历，对于每个元素，都只对当前考察的位进行检查，对其求和，看出现了多少个1。特别地，通过%操作，每过3次，就让Sum归0 （这个3可以推广到k次的情况）。这样的话，如果一个数在数组中出现了三次，那么它的二进制表示中为1的各个位上，这个数会贡献三个1，因为三个1会被%操作归零，所以如果Sum结果是1的话，这个1一定是那个只出现1次的那个数贡献的。所以只要对某位检查完后Sum还是1，那么就把个记录下来即可。当32位都检查完，凡是Sum结果是1的位，就都是只出现1次那个数贡献的，组合起来这个32位的数就是那个只出现一次的数，非常巧！！！

The usual bit manipulation code is bit hard to get and replicate. I like to think about the number in 32 bits and just count how many 1s are there in each bit, and sum %= 3 will clear it once it reaches 3. After running for all the numbers for each bit, if we have a 1, then that 1 belongs to the single number, we can simply move it back to its spot by doing ans |= sum << i;

This has complexity of O(32n), which is essentially O(n) and very easy to think and implement. Plus, you get a general solution for any times of occurrence. Say all the numbers have 5 times, just do sum %= 5.*/

public int singleNumber(int[] nums) {
    int ans = 0;
    for(int i = 0; i < 32; i++) {
        int sum = 0;
        for(int j = 0; j < nums.length; j++) {
            if(((nums[j] >> i) & 1) == 1) {
                sum++;
                sum %= 3;
            }
        }
        if(sum != 0) {
            ans |= sum << i; //sum << i 就是把这个结果现在放回到第i位上，|=其实就是与当前Ans进行或操作，这样当前位的结果也就加入到Ans里了，那么最后ans就是那个只出现一次的数了！
        }
    }
    return ans;
}


/*第二种解法，Idea是基于信息编码的方法，比较绕，也可以参考（这个解法也是可以推广到k的）：

Beautiful. Let me describe it to see if I'm understanding it right:

First time number appear -> save it in "ones"

Second time -> clear "ones" but save it in "twos" for later check

Third time -> try to save in "ones" but value saved in "twos" clear it.*/

/**
     * Use ones to store those nums only appeared once
     * twos to store those nums appeared twice
     */
    public static int singleNum(int[] A) {
        int ones = 0, twos = 0;
        for (int i = 0; i < A.length; i++) {
            ones = (ones ^ A[i]) & ~twos; // in ones not in twos
            twos = (twos ^ A[i]) & ~ones; // in twos not in ones
        }
        return ones; // only appeared once
    }
