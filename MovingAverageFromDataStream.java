
    /** 思路：基本思想是用个Queue的结构，并且记录上次的计算和。一致保留计算和，而不是平均值，平均值只是在最后返回时做一下除法即可。 底下的代码用的LinkedList实现的Queue，可以尝试其他方法看看。
     * 
     * 
   Essentially, we just need to keep track of the sum of the current window as we go. This prevents an O(n) traversal over the Queue as we add new numbers to get the new average. If we need to evict then we just subtract that number off of our sum and divide by the size.
   
   可以回头再看看Leetcode有没有更好的方法，现在Leetcode上还有一种方法是不用Queue，就用一个数组
     */

    
public class MovingAverage {
    private double previousSum = 0.0;
    private int maxSize;
    private Queue<Integer> currentWindow;
    
    public MovingAverage(int size) {
        currentWindow = new LinkedList<Integer>();
        maxSize = size;
    }
    
    public double next(int val) {
        if (currentWindow.size() == maxSize)
        {
            previousSum -= currentWindow.remove();
        }
        
        previousSum += val;
        currentWindow.add(val);
        return previousSum / currentWindow.size();
    }
    
}
