/*
思路：数学题，感觉也没有什么好的办法。主要想法就是每次Shuffle的时候，先生成一个对原数组的Clone，然后在Clone上进行操作，从头开始遍历，然后对于每一个考察元素j,将其与一个随机的j前面的元素交换。

*/


import java.util.Random;

public class Solution {
    private int[] nums;
    private Random random;

    public Solution(int[] nums) {
        this.nums = nums;
        random = new Random();
    }
    
    /** Resets the array to its original configuration and return it. */
    public int[] reset() {
        return nums;
    }
    
    /** Returns a random shuffling of the array. */
    public int[] shuffle() {
        if(nums == null) return null;
        int[] a = nums.clone();
        for(int j = 1; j < a.length; j++) {
            int i = random.nextInt(j + 1); //nextInt(j + 1) returns a random num between [0, j]. By nextInt(j), you never get a chance to return the original order array. 也就是说设置成j+1的情况可以使得有机会出现原数组，也就是j仍然替换了j自己。
            swap(a, i, j);
        }
        return a;
    }
    
    private void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
