public class WiggleSort {

    /**
     * The final sorted nums needs to satisfy two conditions:
     * <p>只需要满足以下条件就行了：
     * If i is odd, then nums[i] >= nums[i - 1]; //奇数为比前面一个大
     * If i is even, then nums[i] <= nums[i - 1].//偶数为比前面一个小
     * <p> 下面的证明说明了即使交换两个当前元素，也不会影响产生对于前面元素的连锁反应
     * Proof: suppose if nums[0...i-1] is already wiggle.
     * If i is odd, then nums[i - 2] >= nums[i - 1], nums[i - 1] should <= nums[i]
     * So if nums[i - 1] > nums[i], swap them. And because nums[i - 2] >= nums[i - 1], nums[i - 2] > nums[i].
     * 上面这一步也就说明了，即使交换，交换过去的是也就是nums[i]是比刚才的值也就是刚才的nums[i-1]更加小的值，所以不会产生任何连锁反应而影响到
     * nums[i-2].
     * if i is even, 同理是可以证明的。
     * Then nums[i - 2] > nums[i] < nums[i - 1]
     */
    public void wiggleSort(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            //注意这里的如果求一个位是奇数位，还是偶数位的方法，i & 1 即可，因为1的二进制只有末尾是1，其他位都是0，而i的二进制数，只有奇数
           // 的时候，末位是1，偶数的时候，末位是0，而高位都应该与1的&运算都变为0了。所以计算结果>0就是代表是奇数位。
           //           // 求余运算符应该也可以，但是看过前面一道题说，求余运算代价比较高。
            if ((i & 1) > 0) {
                if (nums[i - 1] > nums[i]) {
                    swap(nums, i);
                }
            } else if (i != 0 && nums[i - 1] < nums[i]) {
                swap(nums, i);
            }
        }
    }

    private void swap(int[] nums, int i) {
        int temp = nums[i];
        nums[i] = nums[i - 1];
        nums[i - 1] = temp;
    }
 }
