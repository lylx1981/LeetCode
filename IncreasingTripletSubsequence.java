/*思路： 这道题看似简单但是一点都不简单，还是类似于300题求LIS(longest increasing sequence)的方法。用两个变量即可，然后从头遍历数组，c1存放当前可以达到的最小值，c2存取的比c1大的某一个值。对于一个新的要考察的值，如果它比最小值还小，则更新它为最小值。
否则如果这个值比最小值大，但是比当前c2存的值小，则更新c2为这个值，把C2弄的更小使得我更有机会找到第三个数。最后一种情况，如果一个数比c1,c2都大，那么直接就是找到了。 注意这里几点：
1. c1有可能更新后比c2还在位置靠后的地方，所以他们的具体意思并不是让c1,c2组成所需要的Triplet的头2位，而是c1代表组成长度为1的递增子串的结尾位置最好的Candiate。C2是长度为2的递增字串结尾位置的最好Candidate。看下面的分析

before coding I also thought about this solution and I did’t think that will work, as it appears to be very naïve and greedy: find first smallest, then find second smallest, then find the third and bingo. I argued myself it cannot pass the case like [1,2,0,3] since c1 is changed.

But when I take a closer look, it does [1,2,0,3] very well. And I realize that c1 and c2 are indeed having the meaning of:

C1 = so far best candidate of end element of a one-cell subsequence to form a triplet subsequence

C2 = so far best candidate of end element of a two-cell subsequence to form a triplet subsequence

So c1 and c2 are the perfect summary of history. 注意上面当碰到0时，c1更新为0，也就是当前0变成了长度为1的字串的结尾位置最好的Candiate，而当前c2还是2，意思是它现在是长度为2的递增字串结尾位置的最好Candidate，因为它对应的串是【1，2】.所以就巧在这里，长度为2的串的计算要首先通过长度为1的串的if判断。也就是说只要c2存在，那么一定在前面有一个长度为2的递增子串。*/


public boolean increasingTriplet(int[] nums) {
        // start with two largest values, as soon as we find a number bigger than both, while both have been updated, return true.
        int small = Integer.MAX_VALUE, big = Integer.MAX_VALUE;
        for (int n : nums) {
            if (n <= small) { small = n; } // update small if n is smaller than both
            else if (n <= big) { big = n; } // update big only if greater than small but smaller than big
            else return true; // return if you find a number bigger than both
        }
        return false;
}
