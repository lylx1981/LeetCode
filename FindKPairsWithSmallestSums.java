/*思路： 用优先级队列，自己差不多想出来了，但是Leetcode的方法更巧妙。

思路是对第一个数组从头开始判断，而且所有会导致变化的转折点在第一个数组的任意一个元素和第二个数组的第一个元素配起来的这些Sum，也就是{nums1[i],nums[0]} ,因为有可能出现{nums1[i+1],nums[0]} < {nums1[i],nums[k]} 这样的话，下一个最小元素是{nums1[i+1],nums[0]}，而不是{nums1[i],nums[k]}，同时这些{nums1[i],nums[k]}的元素过后还会有可能继续加进来的，所以需要一直Track他们 。

数据结构就采用Queue，先把这些转折点的元素都加入一个最小堆里，然后一直While循环K次，每次从最小堆里拿出一个元素加入结果集，同时对每次拿出来的对象，判断：1）如果这个对象的第二个组成部分cur[1]已经是第二个数组的最后一个元素了，那么直接跳过，否则把当前考察对象的来自第一个数组的元素cur[0]与第二个数组的元素的后面一个cur[2]+1 （cur[1]存储的是来自第二个数组的元素的具体值，cur[2]对应的是其坐标Index）元素组合起来成为一个新的对象加入最小堆，这样的话第二个数组中cur[2]+1后面的元素也不会被忘记（而且由于现在加入的这个新的对象一定比他们大，所以只要现在加入的这个对象不被拿出最小堆，那么一直就轮不到这些对象），他们会在当前加入最小堆的这个对象拿出最小堆时，重新被考察到，非常巧！！。


*/

public class Solution {
    public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        PriorityQueue<int[]> que = new PriorityQueue<>((a,b)->a[0]+a[1]-b[0]-b[1]);
        List<int[]> res = new ArrayList<>();
        if(nums1.length==0 || nums2.length==0 || k==0) return res;
        //注意加入最小堆的对象是由三个部分组成的，分别是来自第一个数组的元素，来自第二个数组的元素，以及第二个元素在第二个数组中的位置
        for(int i=0; i<nums1.length && i<k; i++) que.offer(new int[]{nums1[i], nums2[0], 0}); //把所有转折点先加进最小堆
        while(k-- > 0 && !que.isEmpty()){ 
            int[] cur = que.poll(); //从当前堆中拿出一个最小的元素加入结果集
            res.add(new int[]{cur[0], cur[1]});
            if(cur[2] == nums2.length-1) continue;
            que.offer(new int[]{cur[0],nums2[cur[2]+1], cur[2]+1});//组成一个新的对象并加入最小堆
        }
        return res;
    }
}
