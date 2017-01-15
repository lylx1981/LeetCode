
public class Solution {
    /** //九章算法，维持一个Window，不停向右挪Window，同时更新当前最小值
     * @param nums: an array of integers
     * @param s: an integer
     * @return: an integer representing the minimum size of subarray
     */
    public int minimumSize(int[] nums, int s) {
        // write your code here
        int j = 0, i = 0;
        int sum =0;
        int ans = Integer.MAX_VALUE;
        for(i = 0; i < nums.length; i++) {
            while(j < nums.length && sum < s) {
                sum += nums[j];
                j ++;
            }
            if(sum >=s) {
                ans = Math.min(ans, j - i);
            }
            sum -= nums[i];
        }
        if(ans == Integer.MAX_VALUE)
            ans = -1;
        return ans;
    }
    
     /** 和九章的算法，一个思想。
     * Two Pointers. O(n) Time.
     * Use start and end index to represent a window.
     * Store the window sum and min length.
     * Move the end index and update window sum.
     * If window sum >= s, means we are able to remove some value from ahead and get smaller window.
     * Move the start index and update sum.
     * Update min length.
     */
    public int minSubArrayLen(int s, int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int sum = 0;
        int min = Integer.MAX_VALUE;
        for (int start = 0, end = 0; end < nums.length; end++) {
            sum += nums[end];
            while (sum >= s) {
                min = Math.min(min, end - start + 1);
                sum -= nums[start++];
            }
        }
        return min == Integer.MAX_VALUE ? 0 : min; // Min length can be MAX, then no window is found.
    }
}
