/* 

思路：数学计算公式即可。

Approach: Mathematics

The problem is basically asking the maximum length of consecutive number that has the running sum lesser or equal to `n`. In other word, find `x` that satisfy the following condition:

`1 + 2 + 3 + 4 + 5 + 6 + 7 + ... + x <= n`
`sum_{i=1}^x i <= n`
Running sum can be simplified,

`(x * ( x + 1)) / 2 <= n`
Using quadratic formula, `x` is evaluated to be,

`x = 1 / 2 * (-sqrt(8 * n + 1)-1)` (Inapplicable) or `x = 1 / 2 * (sqrt(8 * n + 1)-1)`
Negative root is ignored and positive root is used instead. Note that 8.0 * n is very important because it will cause Java to implicitly autoboxed the intermediate result into double data type. The code will not work if it is simply 8 * n. Alternatively, an explicit casting can be done 8 * (long) n).

*/


public class Solution {
    public int arrangeCoins(int n) {
        return (int) ((Math.sqrt(1 + 8.0 * n) - 1) / 2);
    }
}
