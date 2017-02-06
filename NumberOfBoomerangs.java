/*

思路，数学问题，不用三重循环去测每个组合。对i遍历即可，每轮遍历过程中，计算所有其他点与i的距离d，并且以距离d为Key，以相应与i距离为d的点的个数为Value放到一个Hashmap里。
在i的这一层循环最后，对所有点检查完毕后，针对一个d, 一个value的键值对, 从这个value里面任取2个组成的排列（注意这里不是组合，是排列），就能组成一个解（从n个数中取2个组成的排列，化简之间就是n*(n-1)）,所以对于每个键值对，每个d, 其可构造出value * (value-1) 个解 ，把他们相加即是最后结果，非常巧！！！！ 记得没考察完一个i， 要把当前Hashmap全部清空重新开始。 



For every i, we capture the number of points equidistant from i. Now for this i, we have to calculate all possible permutations of (j,k) from these equidistant points.

Total number of permutations of size 2 from n different points is nP2 = n!/(n-2)! = n * (n-1). hope this helps.



*/
public int numberOfBoomerangs(int[][] points) {
    int res = 0;

    Map<Integer, Integer> map = new HashMap<>();
    for(int i=0; i<points.length; i++) {
        for(int j=0; j<points.length; j++) {
            if(i == j)
                continue;
            
            int d = getDistance(points[i], points[j]);                
            map.put(d, map.getOrDefault(d, 0) + 1);
        }
        
        for(int val : map.values()) {
            res += val * (val-1);
        }            
        map.clear();
    }
    
    return res;
}

private int getDistance(int[] a, int[] b) {
    int dx = a[0] - b[0];
    int dy = a[1] - b[1];
    
    return dx*dx + dy*dy;
}
