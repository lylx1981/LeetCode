class Solution {
    /*
     * 思路：数学题，主要难点在于判断循环何时归为1，或者一直无限Loop。用一个例子试试看就发现，比如16
     一个个算一下发现经过了几轮之后，数字又变回了16，所以Loop就会无限循环下去，这样就想到，没算出一个数，就把它放到一个HashSet里，一旦发现Hashset里有这个数了，那么就说明出现了循环，直接退出循环即可。最后再判断，如果退出后，最终的计算值是1，那么就是一个高兴数，否则就不是高兴数。另外这道题也可以用那个快慢指针追赶的方法来判断Loop。
     
     
     */
    /**
     * loop detection using Set, use more space
     */
    public boolean isHappy(int n) {
        if (n < 1) return false;

        int num = n;
        Set<Integer> results = new HashSet<>();
        while (!results.contains(num)) { //如果当前num不在Set里，那么就继续往Set里面加，说明还没出现循环
            results.add(num);
            num = digitSquareSum(num); //每次对当前num计算后的数，继续赋值给num,
        }
        
        return num == 1; //如果最后num是1，它就是高兴数
    }

    public int digitSquareSum(int n) {
        int res = 0;
        int digit;
        for (; n > 0; n /= 10) {
            digit = n % 10;
            res += digit * digit;
        }
        return res;
    }
}
    
/**     另外这道题也可以用那个快慢指针追赶的方法来判断Loop。
     * loop detection like linked list
     */
    public boolean isHappy(int n) {
        int slow, fast;
        slow = fast = n;
        do {
            slow = digitSquareSum(slow);
            fast = digitSquareSum(digitSquareSum(fast)); //每次计算两边，这是个很通用的技巧，相当于指针的题，每次走两步！！！！
        } while (slow != fast);
        return slow == 1;
    } 
