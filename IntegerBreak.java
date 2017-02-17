/*思路： 数学题。把一个数拆分成2个的话，要想得到最大乘积，那么最好就是一半一半，当然是奇数的话，也是差不多一半一半的样子。
另外发现，对于一个数N,如果这个数>=4的话，拆分成一半一半总是比原来的N大。所以我们对一个数进行拆分的时候，只要剩下的数还比4大，就继续拆，这样导致的结果是最后只剩下2和3的乘积（1自动不管了扔掉）。但3×3比2×2×2来的好，所以尽量选3不选2.

所以代码就是不停地让原来的数每次减3（然后当前乘积×3），一直减到4以下，最后把剩余的数再乘到product上，就是最后结果了。


The first thing we should consider is : What is the max product if we break a number N into two factors?

I use a function to express this product: f=x(N-x)

When x=N/2, we get the maximum of this function.

However, factors should be integers. Thus the maximum is (N/2)*(N/2) when N is even or (N-1)/2 *(N+1)/2 when N is odd.

When the maximum of f is larger than N, we should do the break.

(N/2)*(N/2)>=N, then N>=4

(N-1)/2 *(N+1)/2>=N, then N>=5

These two expressions mean that factors should be less than 4, otherwise we can do the break and get a better product. The factors in last result should be 1, 2 or 3. Obviously, 1 should be abandoned. Thus, the factors of the perfect product should be 2 or 3.

The reason why we should use 3 as many as possible is

For 6, 3 * 3>2 * 2 * 2. Thus, the optimal product should contain no more than three 2.

Below is my accepted, O(N) solution.


*/



public class Solution {
    public int integerBreak(int n) {
        if(n==2) return 1;
        if(n==3) return 2;
        int product = 1;
        while(n>4){
            product*=3;
            n-=3;
        }
        product*=n;
        
        return product;
    }
}
