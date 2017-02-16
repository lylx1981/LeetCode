/*思路： DP+位操作。DP[i]的1的个数有两部分组成，一部分是末位，另外一部分是其余部分，而其余部分就是i/2，那么该数的1已经在DP[i/2]已经计算过了。
其实仔细看明白后觉得是挺简单的。

Explaination.
Take number X for example, 10011001.
Divide it in 2 parts:
<1>the last digit ( 1 or 0, which is " i&1 ", equivalent to " i%2 " )
<2>the other digits ( the number of 1, which is " f[i >> 1] ", equivalent to " f[i/2] " )*/


public int[] countBits(int num) {
    int[] f = new int[num + 1];
    for (int i=1; i<=num; i++) f[i] = f[i >> 1] + (i & 1);
    return f;
}
