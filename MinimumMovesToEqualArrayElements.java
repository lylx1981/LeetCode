
/*
思路： 数序问题，自己想的差不多，已经退出来了，这里更好的一个数学分析如下：

let's define sum as the sum of all the numbers, before any moves; minNum as the min number int the list; n is the length of the list;

After, say m moves, we get all the numbers as x , and we will get the following equation

 sum + m * (n - 1) = x * n
and actually,

  x = minNum + m
and finally, we will get

  sum - minNum * n = m


另外一个方法是用反向思维，更巧, 把n-1个数都加1，反响等于1个数减1，这样的话，只需要继续大家都要多少步可以减到最小的那个元素即可！！！

Adding 1 to n - 1 elements is the same as subtracting 1 from one element, w.r.t goal of making the elements in the array equal.
So, best way to do this is make all the elements in the array equal to the min element.
sum(array) - n * minimum


*/





public int minMoves(int[] nums) {
    
    int result = 0;
    int min = nums[0];
    
    for (int i = 1; i < nums.length; i++)
    {
        if (nums[i] >= min)
            result += nums[i] - min;
        else
        {
            result += (min - nums[i]) * i;
            min = nums[i];
        }
    }
    return result;
}
