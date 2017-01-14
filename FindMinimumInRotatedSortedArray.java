public class Solution {
    /**
     * @param nums: a rotated sorted array
     * @return: the minimum number in the array
     * 把当前数组最后一个元素当作target,其实就是找第一个比Target小的元素，那个元素就是最小值
     * 还是利用九章算法的模板
     */
    public int findMin(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        
        int start = 0, end = nums.length - 1;
        int target = nums[nums.length - 1];
        
        // find the first element <= target
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] <= target) { //mid都至少还没有target大，说明在下方那条线上，则第一个比target小的在左侧
                end = mid;
            } else {
                start = mid; //mid都比target大了，说明现在在上方的那条线上，则第一个比target小的值在右侧
            }
        }
        if (nums[start] <= target) {
            return nums[start];
        } else {
            return nums[end];
        }
    }
}
