public class NimGame {

    /** 思路，这道题比较巧，只要预先记录一边从0到i的和就行了，然后i,j的和的值就是其各自到0的和的值的差值。很简单的题，自己想的复杂了，去用Hashmap去记录任何调用这个函数得到的结果值以备后续使用。
     * 
     * 以后遇到求和的这种题，多想想求差值的这个思路
     */

    
    
    public class RangeSumQueryImmutable {

    class NumArray {

        private int[] nums;

        public NumArray(int[] nums) {
            for (int i = 1; i < nums.length; i++) {
                nums[i] += nums[i - 1];
            }
            this.nums = nums;
        }

        public int sumRange(int i, int j) {
            return i == 0 ? nums[j] : nums[j] - nums[i - 1];
        }

    }

}
