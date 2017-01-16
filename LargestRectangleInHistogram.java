public class InsertInterval {

    /**
     * 主要思想应用Stack。
     * 具体是针对每一个位置x，计算”包含这个位置的且以这个位置的bar为最小Bar的长方形的面积。
     * 如果对每个位置都计算了上面的值，同时用一个全局变量一直记录全局最大值，那么程序结束后就得到了要求的解。
     * 
     * 如果要计算”包含这个位置的且以这个位置的bar为最小Bar的长方形的面积。”，那么就需要用到Stack，同时从前到后遍历数组，只要当前位置j的值比Stack的最顶端的值大，就直接把这个值继续塞入Stack，也就是Stack里面一直存放的是Bar高度逐渐递增的位置的Index。直到遇到一个位置j，Stack最顶端的值大于等于j位置的值(也就是高度发生了递减) ，这时候开始回溯计算。具体的思想是从Stack中依次pop出所有比当前位置j大的元素（直到stack中最顶端的元素小于j位置上的元素就不再pop了）. 对于每个pop出来的元素（比如位置为k的这个位置），计算”包含这个位置的且以这个位置的bar为最小Bar的长方形的面积”。具体做法是，这个长方形的左边界可以由下面方法求得：寻找“位置k前面第一个出现的Bar的值比位置为k的Bar的值小的那个index(因为刚才Stack加入是以依次递增顺序加入的，所以其实就是加入位置k之前的被加入到Stack里面的那个元素)， 而这个长方形的右边界也就是寻找位置k后面第一个出现的Bar的值比位置k的Bar的值小的index（其实就是外层大循环当前遍历到的位置j. 然后计算相应长方形面积，然后和全局最大值比较，就行了。
     * 
     * 以下这个帖子很好讲述了具体的原理如何求得”包含这个位置的且以这个位置的bar为最小Bar的长方形的面积。
     * http://www.geeksforgeeks.org/largest-rectangle-under-histogram/
     * In this post, O(n) time solution is discussed. Like the previous post, width of all bars is assumed to be 1 for simplicity. For every bar ‘x’, we calculate the area with ‘x’ as the smallest bar in the rectangle. If we calculate such area for every bar ‘x’ and find the maximum of all areas, our task is done. How to calculate area with ‘x’ as smallest bar? We need to know index of the first smaller (smaller than ‘x’) bar on left of ‘x’ and index of first smaller bar on right of ‘x’. Let us call these indexes as ‘left index’ and ‘right index’ respectively.
            We traverse all bars from left to right, maintain a stack of bars. Every bar is pushed to stack once. A bar is popped from stack when a bar of smaller height is seen. When a bar is popped, we calculate the area with the popped bar as smallest bar. How do we get left and right indexes of the popped bar – the current index tells us the ‘right index’ and index of previous item in stack is the ‘left index’. Following is the complete algorithm.

        1) Create an empty stack.
        
        2) Start from first bar, and do following for every bar ‘hist[i]’ where ‘i’ varies from 0 to n-1.
        ……a) If stack is empty or hist[i] is higher than the bar at top of stack, then push ‘i’ to stack.
        ……b) If this bar is smaller than the top of stack, then keep removing the top of stack while top of the stack is greater. Let the removed bar be hist[tp]. Calculate area of rectangle with hist[tp] as smallest bar. For hist[tp], the ‘left index’ is previous (previous to tp) item in stack and ‘right index’ is ‘i’ (current index).
     */
    ppublic int largestRectangleArea(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        
        Stack<Integer> stack = new Stack<Integer>();
        int max = 0;
        for (int i = 0; i <= height.length; i++) {
            int curt = (i == height.length) ? -1 : height[i]; //i == height.length也就是到达了原数组最后一个值的后面一个位置，这样为了保证计算能够完整进行，只需要把就把cur的值设为-1，那么肯定会比Stack里面任何一个值都小，这样就可以激发运算了。
            while (!stack.isEmpty() && curt <= height[stack.peek()]) {
                int h = height[stack.pop()];
                int w = stack.isEmpty() ? i : i - stack.peek() - 1; //当stack为空的时候，也就是说长方形可以一直跨越到最左端
                max = Math.max(max, h * w);
            }
            stack.push(i);
        }
        
        return max;
    }
}
      
