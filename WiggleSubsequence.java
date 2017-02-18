/*思路： 非常好的一道题，可以有2种不同的解法，每个方法都 有其各自的思想，这道题应该好好看看，其中贪心算法应该是最好的，自己想到的是DP。

*/


// dp solution
//ends_large[i]存放以nums[i]结尾的并且以升的趋势结尾的最长Wiggle子序列的长度
////ends_small[i]存放以nums[i]结尾的并且以降的趋势结尾的最长Wiggle子序列的长度
//注意自己在想的时候，想通以就定义一个单一DP[i]而不是分为上面两种ends_large和ends_small来记录以nums[i]为结尾的最长Wiggle子序列，所以这个是需要学习的地方。如果只有一个单一的DP[i]的话，可能就需要另一个表示符号位的数组来记录DP[i]的最后是升还是降的趋势，所以还不如把它们划分开。

public class Solution {
    public int wiggleMaxLength(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int len = nums.length;
        int[] ends_large = new int[len];
        int[] ends_small = new int[len];
        Arrays.fill(ends_large, 1);
        Arrays.fill(ends_small, 1);
        for (int i = 1; i < len; i++) {
            //对每个i前面的j进行判断
            for (int j = i-1; j >= 0; j--) {
                //如果当前nums[i] > nums[j]，nums[i]加上ends_small[j]对应的 Wiggle Subsequence最长子序列是一个新的长度增加1的满足条件的 Wiggle Subsequence
                //因为要对所有j都进行测试，所以这个是一个保留最大值的过程，也就是ends_large[i]要保留能够达到的最大值
                if (nums[i] > nums[j]) ends_large[i] = Math.max(ends_large[i], ends_small[j] + 1);
                else if (nums[i] < nums[j]) ends_small[i] = Math.max(ends_small[i], ends_large[j]+1); //同理如上
            }
        }
        return Math.max(ends_small[len-1], ends_large[len-1]); //返回两种Wiggle Subsequence最长的一个即可。
    }
}



/*
方法二：还是定义两种类型的数组记录以nums[i]结尾的最长Wiggle子序列的长度，但是观察上面的DP会发现其实没有必要对所有i前面的j都进行比较，只要当前位和前一位做比较即可。如果需要更新，则更新，如果不需要更新，就保留之前的值就行。

For every position in the array, there are only three possible statuses for it.

up position, it means nums[i] > nums[i-1]
down position, it means nums[i] < nums[i-1]
equals to position, nums[i] == nums[i-1]
So we can use two arrays up[] and down[] to record the max wiggle sequence length so far at index i.
If nums[i] > nums[i-1], that means it wiggles up. the element before it must be a down position. so up[i] = down[i-1] + 1; down[i] keeps the same with before.
If nums[i] < nums[i-1], that means it wiggles down. the element before it must be a up position. so down[i] = up[i-1] + 1; up[i] keeps the same with before.
If nums[i] == nums[i-1], that means it will not change anything becasue it didn't wiggle at all. so both down[i] and up[i] keep the same.

In fact, we can reduce the space complexity to O(1), but current way is more easy to understanding.

*/

public class Solution {
    public int wiggleMaxLength(int[] nums) {
        
        if( nums.length == 0 ) return 0;
        
        int[] up = new int[nums.length]; //以nums[i]结尾的并且以升的趋势结尾的最长Wiggle子序列的长度
        int[] down = new int[nums.length]; //存放以nums[i]结尾的并且以降的趋势结尾的最长Wiggle子序列的长度
        
        up[0] = 1;
        down[0] = 1;
        
        for(int i = 1 ; i < nums.length; i++){
            if( nums[i] > nums[i-1] ){ //如果i比i-1大
                up[i] = down[i-1]+1; //up[i]长度其实就是down[i-1]对应的长度增1
                down[i] = down[i-1]; //而down[i]保持不变即可
            }else if( nums[i] < nums[i-1]){
                down[i] = up[i-1]+1;
                up[i] = up[i-1];
            }else{
                down[i] = down[i-1]; //保持不变即可
                up[i] = up[i-1];//保持不变即可
            }
        }
        
        return Math.max(down[nums.length-1],up[nums.length-1]);
    }
}

/*
把上面的程序进一步进化，就会变成下面的超级简洁的代码
Very nice and easy to understand! It's really hard to understand if jump to the final version at the very first. Now I can do it myself like this :) DP is magic!!!

*/

    public int wiggleMaxLength(int[] nums) {
        if (nums.length == 0) return 0;
        int up = 1, down = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < nums[i - 1]) down = up + 1;
            else if (nums[i] > nums[i - 1]) up = down + 1;
        }
        return Math.max(up, down);
    }
    
