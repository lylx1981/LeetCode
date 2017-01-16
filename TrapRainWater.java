//Version 0: Two pointer
public class Solution {
    /**
     * 思路：双指针法 two pointer，向两边收缩
     * 左右两边同时再分别记录一个当前的最大高度(leftheight rightheight)
     * 每次进入一个新的循环，分别以目前左右两边目前的最大高度来决定具体考察哪边，因为哪边低，哪边决定了具体能灌进多少水
     * 比如当前左边的最大高度小于右边的最大高度，那么具体考察左边，也就是左边指针新到达的这个位置i，看从这个位置能灌进多少水: 
     * 进一步对i这个位置进行判断：
     *  1) 如果位置i的值比当前左边所保留的最大高度小，那么水直接可以灌进去，和左边当前保留的最大高度差既是灌进去的量。
     *  2) 如果位置i的值比当前左边所保留的最大高度大，则当前位置i是无法灌进去水的，但是要将当前左侧最大高度更新为i处的值
     * 
     * 右侧也是一样的道理。
     * 
     * 边界情况不用特殊考虑，程序自己已经能Cover了。
     * 
     * @param heights: an array of integers
     * @return: a integer
     * 
     */
    public int trapRainWater(int[] heights) {
        // write your code here
        int left = 0, right = heights.length - 1; 
        int res = 0;
        if(left >= right)
            return res;
        int leftheight = heights[left];
        int rightheight = heights[right];
        while(left < right) {
            if(leftheight < rightheight) {
                left ++;
                if(leftheight > heights[left]) {
                    res += (leftheight - heights[left]);
                } else {
                    leftheight = heights[left];
                }
            } else {
                right --;
                if(rightheight > heights[right]) {
                    res += (rightheight - heights[right]);
                } else {
                    rightheight = heights[right];
                }
            }
        }
        return res;
    }
}      
