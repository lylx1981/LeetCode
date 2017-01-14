public class MaxProductSubArray {

    /**
     * DP. Bottom-up.
     * We need to track both maximum product and minimum product.
     * In case a minimum product multiply by a negative number and become maximum.
     * f(k): Largest product subarray, from index 0 up to k.
     * g(k): Smallest product subarray, from index 0 up to k.
     * Similar to max sum subarray, the max product candidates are:
     * 1. Build on previous subarray max product: f(k-1) * A[k]
     * 2. Build on previous subarray min product: g(k-1) * A[k]
     * 3. Start from right here: A[k]
     * Recurrence Relation:
     * f(k) = max( f(k-1) * A[k], A[k], g(k-1) * A[k] ) //Anyway最大值就是从这三个中选一个最大的就行，无论A[k]是正是负值以及f,g当前是正是负
     * g(k) = min( g(k-1) * A[k], A[k], f(k-1) * A[k] ) //Anyway最小值就是从这三个中选一个最小的就行，无论A[k]是正是负值以及f,g当前是正是负
     * Base:
     * Initialize max, min and final result as the first number.
     */
    public int maxProduct(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int max = nums[0], min = nums[0], res = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int preMax = max, preMin = min; // IMPORTANT: max and min need tp be stored.
            max = Math.max(Math.max(nums[i], preMax * nums[i]), preMin * nums[i]);
            min = Math.min(Math.min(nums[i], preMax * nums[i]), preMin * nums[i]);
            res = Math.max(max, res); //记得每次把找出的最大值再和Global的比较，然后存在global的变量里，这里也就使res
        }
        return res;
    }
}
