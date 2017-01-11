public class MoveZeroes {

    /**
     * Two Pointers. One-pass.
     * Track the end of non-zero elements with a pointer.
     * Swap non-zero elements to the front.
     * For each number n in the array:
     * | If n != 0:
     * |  Swap n nums[cur]
     * |  cur -> cur+1
     * If current number is not zero, swap it to the front.
     * Where we track the swap position with another pointer.
     */
    public void moveZeroes(int[] nums) {
        int cur = 0;
        for (int i = 0; i < nums.length; ++i) {
            if (nums[i] != 0) {
                int temp = nums[cur];
                nums[cur++] = nums[i];
                nums[i] = temp;
            }
        }
    }
}
