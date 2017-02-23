//个人更倾向于这个Solution。思路：First turn the input into a set of numbers. That takes O(n) and then we can ask in O(1) whether we have a certain number.

//Then go through the numbers. If the number x is the start of a streak (i.e., x-1 is not in the set), then test y = x+1, x+2, x+3, ... and stop at the first number y not in the set. The length of the streak is then simply y-x and we update our global best with that. Since we check each streak only once, this is overall O(n). This ran in 44 ms on the OJ, one of the fastest Python submissions.

 public int longestConsecutive(int[] nums) {
            Set<Integer> set = new HashSet<>();
            for(int n : nums) {
                set.add(n);
            }
            int best = 0;
            for(int n : set) {
                if(!set.contains(n - 1)) {  // only check for one direction
                    int m = n + 1;
                    while(set.contains(m)) {
                        m++;
                    }
                    best = Math.max(best, m - n);
                }
            }
            return best;
        }


/*
思路： 非常巧的Solution，用Hashmap，和自己想的不一样。自己想的是不是可以用计数法来统计小于某个数的次数，但是Leetcode方法更巧。 其利用Hashmap，如果一个点是一个consevtive sequence的边界点，则它的Value就是对应的该sequence的长度。但是如果一个节点不是一个consevtive sequence的边界点，其也有对应的K以及V键值对在Hashmap里，但是这时候主要是用其K来避免判断重复元素，其V没有实际意义。对于任意一个新新加的元素N，如果已经在Hashmap里过了，表示出现了重复，直接跳过，否则N第一次出现的话，看看其左右大于或者小于1的数在不在Hashmap里，如果在的话，就要把N和左右两边的对应的序列里连起来（得到一个新的长度），同时将新的长度传播更新到新的序列左右两边的端点上。


We will use HashMap. The key thing is to keep track of the sequence length and store that in the boundary points of the sequence. For example, as a result, for sequence {1, 2, 3, 4, 5}, map.get(1) and map.get(5) should both return 5.

Whenever a new element n is inserted into the map, do two things:

See if n - 1 and n + 1 exist in the map, and if so, it means there is an existing sequence next to n. Variables left and right will be the length of those two sequences, while 0 means there is no sequence and n will be the boundary point later. Store (left + right + 1) as the associated value to key n into the map.
Use left and right to locate the other end of the sequences to the left and right of n respectively, and replace the value with the new length.
Everything inside the for loop is O(1) so the total time is O(n). Please comment if you see something wrong. Thanks.
*/


public int longestConsecutive(int[] num) {
    int res = 0;
    HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
    for (int n : num) {
        if (!map.containsKey(n)) {
            int left = (map.containsKey(n - 1)) ? map.get(n - 1) : 0;
            int right = (map.containsKey(n + 1)) ? map.get(n + 1) : 0;
            // sum: length of the sequence n is in
            int sum = left + right + 1;
            map.put(n, sum); //如果没有邻居的话，Sum就是1，也就是当前n就是一个长度为1的连续序列。特殊的是如果左右都有序列的话，这个时候合并完N之后，N就在中间了，那么现在N在Hashmap里的V值（也就是当前Sum）值就是个没有用的废值而已，不用管它，这个键值对存在的目的只是如果下面还有duplicate N出现，我们直接可以用Hashmap去重即可。
            
            // keep track of the max length 
            res = Math.max(res, sum); //更新Max
            
            // extend the length to the boundary(s)
            // of the sequence
            // will do nothing if n has no neighbors，如果其没有邻居的话，则什么都不会做
            map.put(n - left, sum);//把sum的长度传递到左边那个序列的原来边界点上
            map.put(n + right, sum);//把sum的长度传递到右边那个序列的原来边界点上
        }
        else {
            // duplicates
            continue;
        }
    }
    return res;
}
