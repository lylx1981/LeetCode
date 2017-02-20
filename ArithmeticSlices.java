/* 思路： 这道题：Leetcode的解法非常巧，非常值得学习，可以看看下面的英文描述。就是每次按照滑动窗口只判断当前的三个值是不是满足等差数列。
从第1次开始，按照例子【1,2,3,4，5】，就是1，2，3满足等差数列，同时，特别重要的一点是用一个叫做curr的计数器来记录当前终止于当前index i的等差数列的个数是多少。到第一个后，curr=1，也就是终止于3的等差数列个数就是1个。
第二步，当检查到4时，最重要的，刚才所有终结到3的等差数列的个数（当前是1，也就是对应1，2，3这个等差数列）都现在可以延伸到4，所以，一共可以生成新的count个等差数列（也就是1，2，3，4），同时再加上现在2,3,4自己是一个长度为3的新的等差数列，所以现在将cur加1即可（也就是现在cur是2）。然后再把当前cur加到Sum（原来是1）上，所以现在sum一共是3。
第三步，当检查到5时，最重要的，刚才所有终结到4的等差数列的个数（当前是2，也就是对应1，2，3，4以及2，3，4这2个等差数列）都现在可以延伸到5，所以，一共可以生成新的2个等差数列（也就是1，2，3，4，5以及2，3，4，5），同时再加上现在3,4，5自己是一个长度为3的新的等差数列，所以现在将cur加1即可（也就是现在cur是3）。然后再把当前cur加到Sum（之前是3）上，所以现在sum一共是6。
如果某一次判断当前三个数不是等差数列的时候，这时就不能Extend了，所以清空cur,让其从0重新开始即可。

这个题的解法非常巧！！！！



If I understand correctly, the way it works is:
i) We need minimum 3 indices to make arithmetic progression,
ii) So start at index 2, see if we got two diffs same, so we get a current 1 arith sequence
iii) At any index i, if we see it forms arith seq with former two, that means running (curr) sequence gets extended upto this index, at the same time we get one more sequence (the three numbers ending at i), so curr++. Any time this happens, add the curr value to total sum.
iv) Any time we find ith index does not form arith seq, make currently running no of seqs to zero.

*/


public int numberOfArithmeticSlices(int[] A) {
    int curr = 0, sum = 0;
    for (int i=2; i<A.length; i++)
        if (A[i]-A[i-1] == A[i-1]-A[i-2]) {
            curr += 1;
            sum += curr;
        } else {
            curr = 0;
        }
    return sum;
}
