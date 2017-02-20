
/*
思想： Leetcode上的方法，非常巧，自己也想到了一个，但是明显没有这个好。就是从最高的一组人开始，依次对每一组元素插入到最终结果集合的k位置上即可，k就是一个人对象的第二部分的那个值。

Pick out tallest group of people and sort them in a subarray (S). Since there's no other groups of people taller than them, therefore each guy's index will be just as same as his k value.
For 2nd tallest group (and the rest), insert each one of them into (S) by k value. So on and so forth.
E.g.
input: [[7,0], [4,4], [7,1], [5,0], [6,1], [5,2]]
subarray after step 1: [[7,0], [7,1]]
subarray after step 2: [[7,0], [6,1], [7,1]]
...

It's not the most concise code, but I think it well explained the concept.
*/


public class Solution {
    public int[][] reconstructQueue(int[][] people) {
        //pick up the tallest guy first
        //when insert the next tall guy, just need to insert him into kth position
        //repeat until all people are inserted into list
        Arrays.sort(people,new Comparator<int[]>(){
           @Override
           public int compare(int[] o1, int[] o2){
               return o1[0]!=o2[0]?-o1[0]+o2[0]:o1[1]-o2[1];//这里新定义一个比较符，对于两个人，如果高度不同，则高的在前面，如果两个人高度相同，则k值小的在前面。
           }
        }); //这个数组会按照上面的比较符进行排列。
        List<int[]> res = new LinkedList<>();//res先设定为Linkedlist类型以便于插入操作，这里是一个细节的点，要注意。
        for(int[] cur : people){
            res.add(cur[1],cur);//把当前cur加入到res的cur[1]的位置上去

        //add(int index, E element) Inserts the specified element at the specified position in this list.      
        }
        return res.toArray(new int[people.length][]);//然后再把res转换为一个int型的数组返回
    }
}
