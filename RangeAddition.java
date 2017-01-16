/*原理是：对于每个要做的动作，只要先把需要更新的第一个元素更新即可（比如操作是+2，就将该元素加2）。然后把需要更新的最后一个元素（say j）的后面一个元素(i.e., j+1)做反向的更新（比如，刚才操作是+2，现在操作就是减2）。
然后第二次遍历，从头开始，整体求和放到Sum里初值为0，且对第i个元素求和完毕后将把sum当前值赋给i,这样的原理是就是把每个operation需要更新的动作逐步扩展第一个元素后的所有后续元素，因为用了Sum和的方式，所以会一直向后传播更新。同时，因为在每个opearition需要更新的元素的最后一个元素(j)的下一个元素(j+1)处刚才已经执行了反向操作，所以sum在更新到这个位置时，会被减去相应opeartoin对应需要的更新量，从而相关在j处截止的opeartion对应的影响从现在（j+1处）开始就没有了。

原理非常巧妙！

Just store every start index for each value and at end index plus one minus it

for example it will look like:

[1 , 3 , 2] , [2, 3, 3] (length = 5)

res[ 0, 2, ,0, 0 -2 ]

res[ 0 ,2, 3, 0, -5]

sum 0, 2, 5, 5, 0

res[0, 2, 5, 5, 0]
*/

 public int[] getModifiedArray(int length, int[][] updates) {

    int[] res = new int[length];
     for(int[] update : updates) {
        int value = update[2];
        int start = update[0];
        int end = update[1];
        
        res[start] += value;
        
        if(end < length - 1)
            res[end + 1] -= value;
        
    }
    
    int sum = 0;
    for(int i = 0; i < length; i++) {
        sum += res[i];
        res[i] = sum;
    }
    
    return res;
}
