public class TwoSum2 {

    /**
     * Two pointers. //夹逼法
     * 
     * Use two indices from both ends of the array and compare sum with target.
     * If target is larger, move the end index.
     * If target is smaller, move the start index.
     * Stop till we find the target.
     */
    public int[] twoSum(int[] numbers, int target) {
        int start = 0;
        int end = numbers.length - 1;
        while (start < end) {
            long sum = numbers[start] + numbers[end];
            if (sum > target) {
                end--;
            } else if (sum < target) {
                start++;
            } else {
                break;
            }
        }
        return new int[]{start + 1, end + 1};
    }
}
