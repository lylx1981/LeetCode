/*思路： 其实就是判断线段是否有交错，刚开始想的比较简单，但是仔细想想比较复杂的。具体的解法是Work Out所有的可能交错的情况只有下面三类可能的情况，见下面的图示，就对这三类情况进行定义，然后判断就行了，自己其实只想到了Case 1. 
*/
/*               i-2
    case 1 : i-1┌─┐
                └─┼─>i
                 i-3
                 
                    i-2
    case 2 : i-1 ┌────┐
                 └─══>┘i-3
                 i  i-4      (i overlapped i-4)

    case 3 :    i-4
               ┌──┐ 
               │i<┼─┐
            i-3│ i-5│i-1
               └────┘
                i-2

*/

public boolean isSelfCrossing(int[] x) {
    for(int i=3, l=x.length; i<l; i++) {
//底下三个If可以参看上面的图例，会比较清晰，其实就是在定义如何满足上面三种交错出现的条件
        if(x[i]>=x[i-2] && x[i-1]<=x[i-3]) return true;  // Case 1: current line crosses the line 3 steps ahead of it
        else if(i>=4 && x[i-1]==x[i-3] && x[i]+x[i-4]>=x[i-2]) return true; // Case 2: current line crosses the line 4 steps ahead of it
        else if(i>=5 && x[i-2]>=x[i-4] && x[i]+x[i-4]>=x[i-2] && x[i-1]<=x[i-3] && x[i-1]+x[i-5]>=x[i-3]) return true;  // Case 3: current line crosses the line 6 steps ahead of it
    }
    return false;
}
