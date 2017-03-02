/*思路： 这道题还是非常难想到的，最小的Patch个数感觉像是一个全局的问题。但是看了答案后，发现就是用贪心算法，每次假设【1，miss） （不包括miss本身）之前的已经全部能组合出来的，这个时候如果miss这个数不在数组里（如果在的话，直接利用即可，同时更新可覆盖范围），就加一个miss进去，这样，可以覆盖的范围一下子就到了更远的地方，也就是到miss-1+miss。看了答案发现也没有理论的证明说这个就一定是最优的，可能因为原理是每次都只加一个数（也就是只加最小的个数），同时加的这个数让可覆盖的范围跳的最远，所以说最优的。 看看下面的这个例子

show the algorithm with an example, 注意一共会有几种不同情况

let nums=[1 2 5 6 20], n = 50.

Initial value: with 0 nums, we can only get 0 maximumly.

Then we need to get 1, since nums[0]=1, then we can get 1 using [1]. now the maximum number we can get is 1. (actually, we can get all number no greater than the maximum number)

number used [1], number added []
can achieve 1~1
Then we need to get 2 (maximum number +1). Since nums[1]=2, we can get 2 （注意这里是一个Case）. Now we can get all number between 1 ~ 3 (3=previous maximum value + the new number 2). and 3 is current maximum number we can get.

number used [1 2], number added []
can achieve 1~3
Then we need to get 4 (3+1). Since nums[2]=5>4; we need to add a new number to get 4 （注意这里是一个Case）. The optimal solution is to add 4 directly. In this case, we could achieve maximumly 7, using [1,2,4].

number used [1 2], number added [4]
can achieve 1~7
Then we need to get 8 (7+1). Since nums[2]=5<8 （注意这里是一个Case）, we can first try to use 5. Now the maximum number we can get is 7+5=12. Since 12>8, we successfully get 8.

number used [1 2 5], number added [4]
can achieve 1~12
Then we need to get 13 (12+1), Since nums[3]=6<13, we can first try to use 6. Now the maximum number we can get is 12+6=18. Since 18>13, we successfully get 13.

number used [1 2 5 6], number added [4]
can achieve 1~18
Then we need to get 19 (18+1), Since nums[4]=20>19, we need to add a new number to get 19. The optimal solution is to add 19 directly. In this case, we could achieve maximumly 37.

number used [1 2 5 6], number added [4 19]
can achieve 1~37
Then we need to get 38(37+1), Since nums[4]=20<38, we can first try to use 20. Now the maximum number we can get is 37+20=57. Since 57>38, we successfully get 38.

number used [1 2 5 6 20], number added [4 19]
can achieve 1~57


this is an iterative process. if you know [1,2,3] can cover [1...6], after patching 7, you know [1+7...6+7] --> [8...13] can be covered. therefore [1,2,3,7] can cover [1...6] union [7] union [8...13], which is [1...13].*/

// eg.[1,2,3,9], n=100
    // miss=1  -> 2, i=0
    // miss=2  -> 4, i=1
    // miss=4  -> 7, i=2
    // miss=7  -> 14, i=2 (Patch here!)
    // miss=14 -> 23, i=3
    // miss=23 -> 46, i=3 (Patch here!)
    // ...
    // Why is it possible to move 7 to 14 after patch? 
    // Since [1,6] is covered that all combinations with 7 cover [1,13]
    public int minPatches(int[] nums, int n) {
        int patch = 0, i = 0; // long for overflow, eg.nums=[1,2,31,33],n=2147483647
        for (long miss = 1; miss <= n; ) { /* invariant: [1,miss) is covered */
            if (i >= nums.length || miss < nums[i]) {
                miss += miss; // patch miss, now we reach miss-1 (already covered) + miss + 1 (next miss)=2*miss
                patch++;
            } else {
                miss += nums[i++]; // miss >= nums[i], [1,miss) + nums[i] covers miss and move forward
            }
        }
        return patch;
    }

