


/*
思路： 还是Simple Reservoir Sampling方法的简单应用，从头开始遍历数组，只是不同的是，如果当前的数不是target的话，直接跳过即可。

一些关于Reservoir Sampling的基础知识：

Example: Sample size 1[edit]
Suppose we see a sequence of items, one at a time. We want to keep a single item in memory, and we want it to be selected at random from the sequence. If we know the total number of items (n), then the solution is easy: select an index i between 1 and n with equal probability, and keep the i-th element. The problem is that we do not always know n in advance. A possible solution is the following:

Keep the first item in memory.
When the i-th item arrives (for {\displaystyle i>1} i>1):
with probability {\displaystyle 1/i} 1/i, keep the new item (discard the old one)
with probability {\displaystyle 1-1/i} {\displaystyle 1-1/i}, keep the old item (ignore the new one)
So:

when there is only one item, it is kept with probability 1;
when there are 2 items, each of them is kept with probability 1/2;
when there are 3 items, the third item is kept with probability 1/3, and each of the previous 2 items is also kept with probability (1/2)(1-1/3) = (1/2)(2/3) = 1/3;
by induction, it is easy to prove that when there are n items, each item is kept with probability 1/n.


*/

public class Solution {

    int[] nums;
    Random rnd;

    public Solution(int[] nums) {
        this.nums = nums;
        this.rnd = new Random();
    }
    
    public int pick(int target) {
        int result = -1;
        int count = 0;//主要用来记录现在见到几个Traget了
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != target)
                continue;
            //如果通过了上面这一步，说明当前遇到的正是一个target  
            //The method call returns a pseudorandom, uniformly distributed int value between 0 (inclusive) and n (exclusive).这个函数要求if (n <= 0) throw new IllegalArgumentException("n must be positive");
            if (rnd.nextInt(++count) == 0) //如果这个数==0（因为使用的是Random函数，所以只有1/count的概率等于0）count要++ 一下是确保第一个副本肯定会被选上，并将第一个副本的index存在Result里 。random.nextInt(++counter) this will guarantee that at least the first one will be picked up。this is because we need to save current element with probability 1 / (total # of encountered candidates). So it's 100% for first target element, 50% for the second one, and so on...
                result = i; //则将当前result替换为i的坐标
        }
        
        return result;
    }
}
