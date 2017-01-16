public class JumpGame2 {
    
    /** 贪心算法，维持一个全局的可以跳的最远的距离（用last表示），且意思是经过jump中的跳数，可以到达的最远距离是哪里。
     * 在每次遍历i的时候，记录如果我再跳一次，最远可以跳到哪里。用cur表示
     * 当遍历到一个位置i是全局可以跳的最远距离所不能达到的（也就是last+1的位置），我要last的值更新为cur, 以表示一个新的可以全局到达的最远距离，因为更新了last,也预示着我是需要多跳一次才能达到这个更新的last,所以 jump次数要加1。
     * 如果当前i步仍然在全局可以跳的最远距离里面，那么只用更新cur就行了，也就是一直计算如果从当前这些位置（注意当前这些位置是已经可以通过jump目前的跳数就可以达到的）如果再多跳一步，最远可以跳到哪-- 注意这里每一步在计算cur的时候用到max操作) 
     * 
     * 注意题目有假设“ou can assume that you can always reach the last index”
     * 
     * Use last to store how far we already can reach
     * Compare i with last
     * If we run out of it, update and add 1 more step to result
     * Return if last is already bigger than or equal to the length
     * Use cur to store how far we can reach for the next step
     */
    public int jump(int[] A) {
        int step = 0;
        int last = 0; // how far we already can reach
        int cur = 0; // how far can we reach for next step
        
        for (int i = 0; i < A.length; i++) {
            if (i > last) { // run out of we can reach, need one more step
                last = cur;
                step++;
                if (last >= A.length) return step;
            }
            cur = Math.max(cur, i + A[i]);
        }
        return step;
    }

    // Dynamic Programming
    // 这个方法，复杂度是 O(n^2)，会超时，但是依然需要掌握。
    // step[i]是到i这个位置的最小跳动次数，那么它的值是由假设从0到i-1这些位置的最小跳动次数+1，然后再取min得到的。
    //这就是一个i和i之前步的递推关系

    public int jump(int[] A) {
        // state
        int[] steps = new int[A.length];

        // initialize
        steps[0] = 0;
        for (int i = 1; i < A.length; i++) {
            steps[i] = Integer.MAX_VALUE;
        }

        // function
        for (int i = 1; i < A.length; i++) {
            for (int j = 0; j < i; j++) {
                if (steps[j] != Integer.MAX_VALUE && j + A[j] >= i) {
                    steps[i] = Math.min(steps[i], steps[j] + 1);
                }
            }
        }
        
        // answer
        return steps[A.length - 1];
    }

}      
