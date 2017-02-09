/*
思路一：1。如果总油量比耗油量多，则一定有解。2.如果当前Start开始点不行，我就接着试验下一个点。
1，2一起保证最后如果有解的话，Start对应的就一定是可以跑完全程的点


The reason why I think this works:
1, if sum of gas is more than sum of cost, then there must be a solution. And the question guaranteed that the solution is unique(The first one I found is the right one).
2, The tank should never be negative, so restart whenever there is a negative number.

*/

public int canCompleteCircuit(int[] gas, int[] cost) {
    int sumGas = 0;
    int sumCost = 0;
    int start = 0;
    int tank = 0;
    for (int i = 0; i < gas.length; i++) {
        sumGas += gas[i];
        sumCost += cost[i];
        tank += gas[i] - cost[i];
        if (tank < 0) {
            start = i + 1; //.如果当前Start开始点不行，我就接着试验下一个点。直接将Tank清零即可。
            tank = 0;
        }
    }
    if (sumGas < sumCost) {
        return -1; //如果总油量比耗油量多，则一定有解
    } else {
        return start;
    }
}

/*
思路二：夹逼法，我假设从最后一个点设为开始点，第一个点设为终点，向中间夹逼
如果当前油量大于0，我让终点++，也就是再往前开一个点
如果当前油量小于0，我让起点--，也就是油不够了，再从前面一个点再试试看
当start和end碰到一起的时候推出循环即可


最后如果油量推出循环后还是大于0的话，就代表有解
*/

class Solution {
public:
    int canCompleteCircuit(vector<int> &gas, vector<int> &cost) {

       int start = gas.size()-1;
       int end = 0;
       int sum = gas[start] - cost[start];
       while (start > end) {
          if (sum >= 0) {
             sum += gas[end] - cost[end];
             ++end;
          }
          else {
             --start;
             sum += gas[start] - cost[start];
          }
       }
       return sum >= 0 ? start : -1;
    }
};

