
/*方法一： 用桶排序，比较巧，核心是想到一个人如果有N个paper,其H-Index最多是N，所以就设定N个桶就行了，把每个文章放到其Citation对应的那个桶里，凡是Citation大于N的都放到N桶里即可。
最后从最大桶开始算Paper的总和（i从N开始递减），如果Paper总和大于了遍历所有i,那个i就是H-index。*/
public class HIndex {

    /**
     * Bucket sort.
     * Suppose n is the number of papers.
     * H can be at most n when a person has n papers and all of them have more than n citations.
     * To find a number h that h of his n papers have >= h citations, put papers in buckets.
     * All papers have >= n citations put into bucket n.
     * Papers have i citations put into bucket i.
     * Then count backwards.
     * The first number i that has total papers >= i is the answer.
     */
    public int hIndex(int[] citations) {
        int n = citations.length;
        int[] buckets = new int[n + 1];
        for (int c : citations) {
            if (c >= n) {
                buckets[n]++;
            } else {
                buckets[c]++;
            }
        }
        int papers = 0;
        for (int i = n; i >= 0; i--) {
            papers += buckets[i];
            if (papers >= i) {
                return i;
            }
        }
        return 0;
    }
}

/*思路二：

下面的这个思路也是我自己想到的，基本上完全一样：

这道题的切入点应该是这样  h这个值是和几篇文章有关  而只有数组的索引是和文章的数量有关 所以关注点应该在索引上
首先把数组按倒序排列 Then, we look for the last position in which f is greater than or equal to the position (we call h this position).
For example, if we have a researcher with 5 publications A, B, C, D, and E with 10, 8, 5, 4, and 3 citations, respectively, the h index is equal to 4 because the 4th publication has 4 citations and the 5th has only 3. On the contrary if the same publications have 25, 8, 5, 3, and 3, then the index is 3 because the fourth paper has only 3 citations.
f(A)=10, f(B)=8, f(C)=5, f(D)=4, f(E)=3　→ h-index=4
f(A)=25, f(B)=8, f(C)=5, f(D)=3, f(E)=3　→ h-index=3
O(N)的算法类似 先过一遍数组 数组的索引是文章的引用次数 如果引用次数大于n了 就放在n索引里 然后值就是这个引用次数出现了几次  所以第n索引的是说出现了多少个大于n的引用 
然后也是倒序遍历 找到第一个累加起来超过当前索引的值
*/


    /* 思路三：也可以用排序做，先把Citation排序，然后从前往后判断，每一步主要取i所在位置的数字（Citation数）和N-i（比该大的Citation具有的papers数），按照H-index定义，应该取两者之间的最小值，取完后的最小值作为一个H-Index 的 Candidate与当前Max比较，如需要更新则更新。最后返回那个Max，就是最大可能达到的H-index。

*/
// Key is: h could either be paper number or citations.
    // eg.   [0, 1, 3, 5, 6]
    // cites  0  1  3  5  6
    // papers 5  4  3  2  1
    // hindex 0  1  3  2  1
    // max = 3
    public int hIndex(int[] citations) {
        Arrays.sort(citations);
        int max = 0;
        for (int i = 0; i < citations.length; i++) {
            int cites = citations[i];
            int papers = citations.length - i; // include itself since "at least h citations"
            int hindex = Math.min(cites, papers);
            max = Math.max(max, hindex);
        }
        return max;
    }
