/**
 * Given an array of integers and an integer k, find out whether there are two distinct indices i and j in the array
 * such that nums[i] = nums[j] and the difference between i and j is at most k.
 * <p>
 * Tags: Array, Hash Table
 * Similar Problems: (E) Contains Duplicate, (M) Contains Duplicate III
 */


public class ContainsDuplicate2 {
    // 方法一： use HashMap 需要记录一下上一个重复元素最近的坐标
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i]) && i - map.get(nums[i]) <= k) {
                return true;
            } else {
                map.put(nums[i], i);
            }
        }
        return false;
    }

    /** 方法二：
     * Don't need to save number and index in a map.
     * Just loop through the array with a sliding window(set of size k).
     */
    public boolean containsNearbyDuplicate2(int[] nums, int k) {
        Set<Integer> set = new HashSet<Integer>();
        for (int i = 0; i < nums.length; i++) {
            if (i > k) {
                set.remove(nums[i - k - 1]); //删除掉滑动窗口最前面那个
            }
            if (!set.add(nums[i])) {
                return true;
            }
        }
        return false;
    }
}
