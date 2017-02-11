class Solution {
        /*
         * 
         
    思路： 代码是最简单的方法，依赖排序。但是其实这道题还有各种优化，最优的方法是用类似快速排序Partition的方法+随机化排序数组的方法，来做，可以看看Leetcode上的详细归纳
    
    https://leetcode.com/problems/kth-largest-element-in-an-array/ 
         */
   

   public class Solution {
    public int findKthLargest(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length-k];
    }
}
