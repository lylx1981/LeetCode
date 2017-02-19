/*
思路：具有简单的推导关系，然后按照推导关系计算一下即可。


F(k) = 0 * Bk[0] + 1 * Bk[1] + ... + (n-1) * Bk[n-1]
F(k-1) = 0 * Bk-1[0] + 1 * Bk-1[1] + ... + (n-1) * Bk-1[n-1]
       = 0 * Bk[1] + 1 * Bk[2] + ... + (n-2) * Bk[n-1] + (n-1) * Bk[0]
Then,

F(k) - F(k-1) = Bk[1] + Bk[2] + ... + Bk[n-1] + (1-n)Bk[0]
              = (Bk[0] + ... + Bk[n-1]) - nBk[0]
              = sum - nBk[0]
Thus,

F(k) = F(k-1) + sum - nBk[0]
What is Bk[0]?

k = 0; B[0] = A[0];
k = 1; B[0] = A[len-1];
k = 2; B[0] = A[len-2];
...

*/


public class Solution {
    public int maxRotateFunction(int[] A) {
        int allSum = 0;
        int len = A.length;
        int F = 0; //初始值是计算出F(0)
        for (int i = 0; i < len; i++) {
            F += i * A[i];
            allSum += A[i];
        }
        int max = F;
        for (int i = len - 1; i >= 1; i--) {
            F = F + allSum - len * A[i]; //从F(K-1)计算F(K) ,从len-1开始是因为看上面k = 0; B[0] = A[0];k = 1; B[0] = A[len-1]; 也就是Bk[0]的排列顺序是从和F（K）不一样的，比如F（1）对应的B1[0]的值其实是A[len-1]
k = 2; B[0] = A[len-2];
            max = Math.max(F, max);
        }
        return max; 
    }
}  
