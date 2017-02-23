//思路： 用Hashmap分别对A,B,C,D做两两求和，并用Hashmap记录。特别地，先对C,D进行求和，以Sum为Kay，Value是出现过这个Sum的次数。然后再对AB求和，然后看看A，B求和的每个结果在之前的Hashmap里面有没有对应的负值，有的话，就把那个负值为Key的对应的Value取出来加在Rest即可（这个就是本次贡献的可以造成4个值和为0的次数）。最后返回Res。  自己看了4Sum的解，自己还想的是用类似数组指针的方法，但是这道题因为ABCD各有各的数组（而4Sum是在同一个数组中判断有没有4个数和为0），所以不应该硬套数组的方法已经two pointer这样的思想了。要学会变通。

/*Time complexity:  O(n^2)
Space complexity: O(n^2)*/


public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
    Map<Integer, Integer> map = new HashMap<>();
    
    for(int i=0; i<C.length; i++) {
        for(int j=0; j<D.length; j++) {
            int sum = C[i] + D[j];
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
    }
    
    int res=0;
    for(int i=0; i<A.length; i++) {
        for(int j=0; j<B.length; j++) {
            res += map.getOrDefault(-1 * (A[i]+B[j]), 0);
        }
    }
    
    return res;
}
