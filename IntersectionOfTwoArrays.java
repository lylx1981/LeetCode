 /** 思路：用两个HashSet即可，注意Hashset里的一个重要特性是其可以自动去除，所以不用担心加入重复元素。
     * 自己刚开始还想用Hashmap去解决去重问题，但是实际上不必要，用HashSet即可！！


     */

    
public class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> set = new HashSet<>();
        Set<Integer> intersect = new HashSet<>();
        for (int i = 0; i < nums1.length; i++) {
            set.add(nums1[i]); //把nums1的元素都加到set里
        }
        for (int i = 0; i < nums2.length; i++) {
            if (set.contains(nums2[i])) { //如果当前元素在Set里，那么它应该就是交集中的一个
                intersect.add(nums2[i]); //因为set不会加入重复元素，所以这里不用担心，让Set自己去重就行了。
            }
        }
        int[] result = new int[intersect.size()];
        int i = 0;
        for (Integer num : intersect) {
            result[i++] = num;
        }
        return result;
    }
}
