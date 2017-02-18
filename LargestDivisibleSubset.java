/*思路： 这个题自己想的是用Hashset之类的把所有能够互相整除的数都放到一个Set里，每当来一个新的元素就判断每个Set，看看这个新元素是不是能放到当前Set里，如果能放，都把它放进去，所以一个元素有可能被放入了多个Set。最后看哪个Set大就行了。

看了Leetcode上的解法，感觉好优化很多，大体思想是一致的，但是利用了有序这个条件，这样使得不用每次都与同属于一个Set的每个元素都比较。另外，解法其实是一种DP的解法。自己没有想到这道题可以用DP的思想来做。这两点都是非常重要的。

大体的思想是先把数组排序，然后从前往后考察，对于每个元素，利用DP：

count[n] = the length of the largest divisible subset whose largest number is nums[i] 也就是以当前nums[i]为最大元素的最大可互相整除集合的大小

count[i+1] = max{ 1 + count[i] if nums[n+1] mod nums[i] == 0 else 1 }


*/


//Use DP to track max Set and pre index.

public class Solution {
    public List<Integer> largestDivisibleSubset(int[] nums) {
        int n = nums.length;
        int[] count = new int[n]; //
        int[] pre = new int[n]; //对于以nums[i]为最大元素的最大可互相整除集合，pre[i]表示在这个集合中谁是在nums[i]前面的那个元素
        Arrays.sort(nums); //首先把nums排序
        int max = 0, index = -1;
        for (int i = 0; i < n; i++) {
            count[i] = 1;//初始值 count[i]就是1，因为就是自己，也就是自己是一个Size是1的最大可整除集合
            pre[i] = -1; //因为初始值是自己，所以没有前辈元素。
            for (int j = i - 1; j >= 0; j--) { //从i-1开始，对i的每个之前的元素进行考察
                if (nums[i] % nums[j] == 0) { //如果当前考察元素可以整除i，那么他们是可能存在于同一个最大可整除集合的
                    //如果1 + count[j] > count[i]，以nums[j]为最大元素的最大可互相整除集合加上现在的nums[i]后，比count[i]大，说明值得把nums[i]放入到以nums[j]为最大元素的最大可互相整除集合
                    if (1 + count[j] > count[i]) {
                        count[i] = count[j] + 1;
                        pre[i] = j;
                    } //因为对每个i前面的可以整除i的j都会进行比较已经考察，所以上面的部分其实是在取最大值，并且把i放到可以使count[i]达到最大值的那个j对应的以其为最大元素的最大可互相整除集合
                    //注意上面这个地方不用担心把i的前辈永久设定其前辈为某一个j，原因是：1） 如果最终结果i并不在结果集中，则没有任何问题，因为Anyway,最终结果集不包含i.
                    //2) 如果最终结果集合包含i,不可能出现该结果集包含i但不包含j的情况，因为数组是有序排列的，所以从i之后考察的元素都会大于i,同时如果最终集合包含i, 说明后面加入的元素都可以整除i, 而i又是能整除j的，所以包含i的一定包含j（而包含j的话，自然而然也一定包含以nums[j]为最大元素的最大可互相整除集合）。所以这里在判断count[i]的时候，是可以经过一轮判断，把永久确定下来nums[i]的前辈是谁而不论今后会发生什么.所以这里要用DP思想来理解，即count[n] = the length of the largest divisible subset whose largest number is nums[i]。
                }
            }
            //因为以nums[i]为最大元素的最大可互相整除集合不一定是全局最大的，用max,index保留全局最大值，所以这里和全局的相比，如果比全局的还大，那么就更新。
            if (count[i] > max) {
                max = count[i];
                index = i;
            }
        }
        List<Integer> res = new ArrayList<>();
        while (index != -1) { //index现在就是全局最大的可互相整除集合的最大元素的Index,顺着它的pre找（相当于指向其前辈的指针），就能把所有属于这个集合的元素都找出来了。
            res.add(nums[index]);
            index = pre[index];
        }
        return res;
    }
}

