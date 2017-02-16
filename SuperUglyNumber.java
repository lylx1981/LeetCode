public class Solution {

/*

思路：和Ugly Number II比较类似，但是每次每个质数的倍数必须本身也是一个之前出现过的Super Ugly数才可以的。例如对于2来说，2×5=10现在这个10就不是super ugly了，因为5并不在primes里。
所以，基本思路还是一样，但是每次乘的倍数都应该是当前已经在ugly里面的一个已经生成的Super ugly数了。
特别的，对于每个primes j,我们用一个index数组记录，当前这个j已经与现有哪些super ugly数结合一起生成过新的super ugly数了，那么下一次我如果还用这个j对应的primes来生成一个备选Super ugly 数的时候，我就从ulgy数组里的 index数组j位置上标记的位置所对应的ugly数来与当前prime结合。

比如对于 primes = [2, 7, 13, 19] 假设当前ugly list如下：[1, 2, 4, 7, 8, 13, 14, 16, 19, 26, 28, 32] ，那么可能对于2来说，其已经与1,2,4,7,8结合生成过super ugly数了，那么下一次如果再要用2生成一个super ugly candidate的话，就直接该用13了，也就是2×13=26了，所以为了记录2究竟已经与ugly list里面哪些数结合使用过了，我们就用一个index[]数组来存储。比如当前index[0]也就是为了表示2这个数应该结合的下一个数是谁，因为应该是13，所以index[0]=5， 那么ugly[idx[0]]=ugly[5]=13. 比较绕，要好好理解。



*/

public int nthSuperUglyNumber(int n, int[] primes) {
    int[] ugly = new int[n];
    int[] idx = new int[primes.length];

    ugly[0] = 1;
    for (int i = 1; i < n; i++) {
        //find next
        ugly[i] = Integer.MAX_VALUE;
        for (int j = 0; j < primes.length; j++)
            ugly[i] = Math.min(ugly[i], primes[j] * ugly[idx[j]]);
        
        //slip duplicate
        for (int j = 0; j < primes.length; j++) {
            while (primes[j] * ugly[idx[j]] <= ugly[i]) idx[j]++; //对于每个prime, 检查其乘以他们的下一个该结合的数，如果该数还比现在刚生成的ugly[i]小，则让这个数对应的index持续增加（从而下一个要结合的数就会是ubly数组的继续往后的数），这也是为了去重复
        }
    }

    return ugly[n - 1];
}
}
