class Solution {
    /*
     * 比较简单的DP，DP[i]表示直到第i家为止，最多能偷多少钱，其取决于下面两者的最大值，也就是DP[i-1]（一旦偷了这一家，第i家就不能再偷了），DP[i-2]+num[i] (一旦偷了第i-2家，我还可以偷第i家，所以是他们的钱数之和)，在其两者之间选最大的那个就是DP[i]的值。
     */
   public class HouseRobber {

    private HouseRobber hr;

    /**
     * DP. Space Optimized.
     * Max amount of house n is either rob the previous house or rob this house.
     * Recurrence relation:
     * max[n] = max(max[n - 2] + nums[n], max[n - 1])
     * Optimization:
     * Use constant variables instead of an array.
     */
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        int preMax = nums[0];
        int max = Math.max(nums[0], nums[1]);
        for (int i = 2; i < nums.length; i++) {
            int temp = preMax;
            preMax = max;
            max = Math.max(temp + nums[i], preMax);
        }
        return max;
    }

    /**
     * DP. More compact code.
     */
    public int robB(int[] nums) {
        int preMax = 0;
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            int curMax = Math.max(preMax + nums[i], max);
            preMax = max;
            max = curMax;
        }
        return max;
    }
}
