/*
思路： 是对之前Painting House的扩展，但是这里的方法很巧，虽然还是用DP(dp[i][j] represents the min paint cost from house 0 to house i when house i use color jdp[i][j] represents the min paint cost from house 0 to house i when house i use color j)，再对第i步进行判断取什么颜色的时候，记录之i-1前一步的最小值和对应的颜色即可。但是因为要避免之前一步和当前步颜色不一样，那么再保存一下前一步的最二小值即可。这样的话，dp[i][j]的计算就取决于颜色J是不是和i-1步取最小值的时候的颜色是否一样，如果一样，就取第二最小值，否则就取最小值即可。

非常巧的方法，值得体会！！

Explanation: dp[i][j] represents the min paint cost from house 0 to house i when house i use color j; The formula will be dp[i][j] = Math.min(any k!= j| dp[i-1][k]) + costs[i][j].

Take a closer look at the formula, we don't need an array to represent dp[i][j], we only need to know the min cost to the previous house of any color and if the color j is used on previous house to get prev min cost, use the second min cost that are not using color j on the previous house. So I have three variable to record: prevMin, prevMinColor, prevSecondMin. and the above formula will be translated into: dp[currentHouse][currentColor] = (currentColor == prevMinColor? prevSecondMin: prevMin) + costs[currentHouse][currentColor].

        prevMin 之前最小值  min 当前一步最小值;
        prevMinInd 之前最小值对应的颜色ID  minInd 当前一步最小值对应的颜色ID;
        prevSecMin 之前一步第二小值  secMin 当前一步第二小值
    */    
        

public class Solution {
    public int minCostII(int[][] costs) {
        if(costs == null || costs.length == 0 || costs[0].length == 0) return 0;
        
        int n = costs.length, k = costs[0].length;
        if(k == 1) return (n==1? costs[0][0] : -1);
        
        int prevMin = 0, prevMinInd = -1, prevSecMin = 0;//prevSecMin always >= prevMin
        for(int i = 0; i<n; i++) {
            int min = Integer.MAX_VALUE, minInd = -1, secMin = Integer.MAX_VALUE;
            for(int j = 0; j<k;  j++) {
                int val = costs[i][j] + (j == prevMinInd? prevSecMin : prevMin); //如果j和prevMinInd一样，那么只能取之前第二小值prevSecMin，否则的话取之前最小值即可prevMin
                if(minInd< 0) {min = val; minInd = j;}//when min isn't initialized
                else if(val < min) {//when val < min, 说明Val现在比当前最小值还小，那么就要更新当前最小值min,以及当前第二最小值
                    secMin = min; //当前最小值变成了当前第二最小值
                    min = val;//val变为当前最小值
                    minInd = j; //当前最小值对应颜色ID变为j
                } else if(val < secMin) { //when min<=val< secMin 说明Val没有当前最小值小，但是比当前第二最小值小，这个时候更新第二最小值即可。
                    secMin = val;
                }
            }
            //为了进入下一步，进行下面的操作。
            prevMin = min;
            prevMinInd = minInd;
            prevSecMin = secMin;
        }
        return prevMin;
    }
}
