class Solution {
    /*
     * 
        
        
        思路： 数学相关的题，想明白了就很简单，想不明白就很难。主要是从本质上0的出现时有2乘以5造成的，所以因为是计算阶乘（其中每个数都可以把它们分解），我们只看看分解以后有多少2和有多少5就行了，一对2，5贡献一个0，所以看看能成多少对2，5.因为2总是比5多多了，所以看看究竟N的阶乘里一共有多少个5就行了，这就是问题的答案。 细节方面就是注意有的含5的数只贡献1个0，比如5，15， 35， 有的却会贡献2个5， 比如25， 有的可以贡献三个5， 比如125， 等等。 比较重要的一道数学题。
        
        The ZERO comes from 10.
        The 10 comes from 2 x 5
        And we need to account for all the products of 5 and 2. likes 4×5 = 20 ...
        The number of zeros is the minimum of the number of 2 and the number of 5.
        
        Since multiple of 2 is more than multiple of 5, the number of zeros is dominant by the number of 5.
        
        Now let's see what numbers will contribute a 5. Well, simply the multiples of 5（multiple of 5 也就是5的倍数）, like 5, 10, 15, 20, 25, 35, .... So is the result simply n / 5? Well, not that easy. Notice that some numbers may contribute more than one 5, like 25 = 5 * 5. Well, what numbers will contribute more than one 5? Ok, you may notice that only multiples of the power of 5 will contribute more than one 5. For example, multiples of 25 will contribute at least two 5's.
        
        Well, how to count them all? If you try some examples, you may finally get the result, which is n / 5 + n / 25 + n / 125 + .... The idea behind this expression is: all the multiples of 5 will contribute one 5, the multiples of 25 will contribute one more 5 and the multiples of 125 will contribute another one more 5... and so on. Now, we can write down the following code, which is pretty short.
        
        
        
        Example One
        
        How many multiples of 5 are between 1 and 23? There is 5, 10, 15, and 20, for four multiples of 5. Paired with 2's from the even factors, this makes for four factors of 10, so: 23! has 4 zeros.
        
        Example Two
        
        How many multiples of 5 are there in the numbers from 1 to 100?
        
        because 100 ÷ 5 = 20, so, there are twenty multiples of 5 between 1 and 100.
        
        but wait, actually 25 is 5×5, so each multiple of 25 has an extra factor of 5, e.g. 25 × 4 = 100，which introduces extra of zero.
        
        So, we need know how many multiples of 25 are between 1 and 100? Since 100 ÷ 25 = 4, there are four multiples of 25 between 1 and 100.
        
        Finally, we get 20 + 4 = 24 trailing zeroes in 100!
        
        The above example tell us, we need care about 5, 5×5, 5×5×5, 5×5×5×5 ....
        
        Example Three
        
        By given number 4617.
        
        5^1 : 4617 ÷ 5 = 923.4, so we get 923 factors of 5
        
        5^2 : 4617 ÷ 25 = 184.68, so we get 184 additional factors of 5
        
        5^3 : 4617 ÷ 125 = 36.936, so we get 36 additional factors of 5
        
        5^4 : 4617 ÷ 625 = 7.3872, so we get 7 additional factors of 5
        
        5^5 : 4617 ÷ 3125 = 1.47744, so we get 1 more factor of 5
        
        5^6 : 4617 ÷ 15625 = 0.295488, which is less than 1, so stop here.
        
        Then 4617! has 923 + 184 + 36 + 7 + 1 = 1151 trailing zeroes.
        
     */


    /**
     * O(log5-n)
     */
    public static int trailingZeroes(int n) {
        int r = 0;
        while (n > 0) {
            n /= 5;
            r += n; // add # of 5 in n
        }
        return r;
    }
