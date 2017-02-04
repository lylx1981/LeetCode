/**
 * 思路：简单的DP,当前第i步房子什么颜色，取决于i-1房子的颜色。稍微和传统DP不太一样的是，因为我们只记录用掉的钱数，而没有变量记录具体每个房子选择的颜色，所以DP过程中记录三种所有的可能性。也就是说，对每个房子i,同时记录假设它砌成三个不同颜色的值（当然如果i房决定是红色，决定了i-1房只可能是蓝色或者绿色两者之一）。对这三个变量都进行计算即可。最终遍历完N个房子后，其三个变量的最小值即可。
 * 
 * 
 * 这道题最重要的一点对于DP的新的应用形式，在每一轮中同时计算三个变量。而且DP过程没有返回值。另外一种看到过的DP题型是DP有返回值，但是为了返回多个值，将返回值定义为一个ResultType的对象。
 * 
 * The basic idea is when we have painted the first i houses, and want to paint the i+1 th house, we have 3 choices: paint it either red, or green, or blue. If we choose to paint it red, we have the follow deduction:

    paintCurrentRed = min(paintPreviousGreen,paintPreviousBlue) + costs[i+1][0]
    Same for the green and blue situation. And the initialization is set to costs[0], so we get the code:


 */

public class Solution {
    public int minCost(int[][] costs) {
        if(costs.length==0) return 0;
        int lastR = costs[0][0];
        int lastG = costs[0][1];
        int lastB = costs[0][2];
        for(int i=1; i<costs.length; i++){
            int curR = Math.min(lastG,lastB)+costs[i][0];
            int curG = Math.min(lastR,lastB)+costs[i][1];
            int curB = Math.min(lastR,lastG)+costs[i][2];
            lastR = curR;
            lastG = curG;
            lastB = curB;
        }
        return Math.min(Math.min(lastR,lastG),lastB);
    }
    
    //能够看的更清楚的DP代码如下，不如上面的代码简洁
    
public class Solution {
    public int minCost(int[][] costs) {
    if(costs==null||costs.length==0){
        return 0;
    }
    for(int i=1; i<costs.length; i++){
        costs[i][0] += Math.min(costs[i-1][1],costs[i-1][2]);
        costs[i][1] += Math.min(costs[i-1][0],costs[i-1][2]);
        costs[i][2] += Math.min(costs[i-1][1],costs[i-1][0]);
    }
    int n = costs.length-1;
    return Math.min(Math.min(costs[n][0], costs[n][1]), costs[n][2]);
    }
}
