/*思路： 差不多想出来了。将所有楼的左右边界点与其楼高组成一个二元组后，进入到一个List里，然后对整个List整体排序后，从左到右扫描每个点即可。对于每个二元组（V1,V2），V1就是当前坐标，V2就是对应的楼的高度。为了标记V1对应的这个点是楼的开始点，还是终点，用正负符号来标记V2也就是楼的高度，如果V2<0,说明当前V1对应的点是一个楼的起点，否则是个终点。对于是起点的情况，将当前楼的高度加入最大堆里。如果是终点的情况，从最大堆Pop出最大元素出来（这时候堆里就又会新的最大元素了，这就是为什么用Heap的原因，可以随时维护全局最大或最小值）,对于这个新的最大高度，可能需要在Res新加一个坐标点来标记从这个新的最大高度开始的Line（但是如果当前新的最大高度和当前Res里面当前最后一个高度一样，则不用进行任何操作，因为新的线还在同样的高度上，只不过是不同的楼造成的）。


Firstly, please notice what we need to achieve:

  1. visit all start points and all end points in order;
  2. when visiting a point, we need to know whether it is a start point or a
      end point, based on which we can add a height or delete a height from
      our data structure;
To achieve this, his implementation:

  1. use a int[][] to collect all [start point, - height] and [end point, height]
      for every building;
  2. sort it, firstly based on the first value, then use the second to break
      ties;
Thus,

  1. we can visit all points in order;
  2. when points have the same value, higher height will shadow the lower one;
  3. we know whether current point is a start point or a end point based on the
      sign of its height;


。*/

public List<int[]> getSkyline(int[][] buildings) {
    List<int[]> result = new ArrayList<>();
    List<int[]> height = new ArrayList<>();
    for(int[] b:buildings) {
        // start point has negative height value
        height.add(new int[]{b[0], -b[2]});
        // end point has normal height value
        height.add(new int[]{b[1], b[2]}); 
    }

    // sort $height, based on the first value, if necessary, use the second to
    // break ties
    Collections.sort(height, (a, b) -> {
            if(a[0] != b[0]) 
                return a[0] - b[0];
            return a[1] - b[1];
    });
    
    //上面是JAVA8的写法，如果不熟悉的话，可以还按照下面的经典写法定义Comparator即可，下面的另外一个代码中定义Comparator的例子，供参考
    
 /*   Collections.sort(points, new Comparator<Point>() {
        public int compare(Point a, Point b) {
            if (a.val != b.val) return a.val - b.val;
            if (a.flag != b.flag) return a.flag - b.flag;  // Starting point first.
            if (a.flag == 0 && b.flag == 0) return b.height - a.height; // Both starting points, with larger height first.
            return a.height - b.height;     // Both ending points, with smaller height first.
        }
    });*/
    
    

    // Use a maxHeap to store possible heights
    Queue<Integer> pq = new PriorityQueue<>((a, b) -> (b - a)); //注意这里是建立最大堆，JAVA8的写法
    //PriorityQueue(SortedSet<? extends E> c) Creates a PriorityQueue containing the elements in the specified sorted set. This priority queue will be ordered according to the same ordering as the given sorted set.

    // Provide a initial value to make it more consistent
    pq.offer(0);

    // Before starting, the previous max height is 0;
    int prev = 0;

    // visit all points in order
    for(int[] h:height) {
        if(h[1] < 0) { // a start point, add height
            pq.offer(-h[1]);
        } else {  // a end point, remove height
            pq.remove(h[1]);
        }
        int cur = pq.peek(); // current max height;
  
        // compare current max height with previous max height, update result and 
        // previous max height if necessary
        if(prev != cur) { //只有在prev != cur才操作，否则是一种特殊情况，也就是之前的点，和现在的点高度都一样，在这种情况下，当前点就不应该再加一个坐标到Res里了，一直就用之前的那个点就行。也就是为了解决下面说的这个情况：There must be no consecutive horizontal lines of equal height in the output skyline. For instance, [...[2 3], [4 5], [7 5], [11 5], [12 7]...] is not acceptable; the three lines of height 5 should be merged into one in the final output as such: [...[2 3], [4 5], [12 7], ...]
            result.add(new int[]{h[0], cur});
            prev = cur;
        }
    }
    return result;
}

/* 这里讨论了优先级队列删除操作可能开销比较高，所以下面的这个代码使用TreeMap来做。
pq.poll() which remove the top of priority queue is O(log(n)), while pq.remove which remove any element is O(n) as it needs to search the particular element in all of the elements in the priority queue.
Thanks for the good solutions. However, there is a small thing that can be improved. pq.remove() is O(n) hence make it slower. I have modified it a little bit to use TreeMap instead of PriorityQueue and the run time is 2.5X faster.
TreeSet can't handle duplicate height. Priority Queue allows two elements to be the same, but TreeSet doesn't allow that, so need to use TreeMap.
*/


public class Solution {
    public List<int[]> getSkyline(int[][] buildings) {
        List<int[]> heights = new ArrayList<>();
        for (int[] b: buildings) {
            heights.add(new int[]{b[0], - b[2]});
            heights.add(new int[]{b[1], b[2]});
        }
        Collections.sort(heights, (a, b) -> (a[0] == b[0]) ? a[1] - b[1] : a[0] - b[0]);
        TreeMap<Integer, Integer> heightMap = new TreeMap<>(Collections.reverseOrder());//所以这里建立的是最大堆的效果
        heightMap.put(0,1);
        int prevHeight = 0;
        List<int[]> skyLine = new LinkedList<>();
        for (int[] h: heights) {
            if (h[1] < 0) {
                Integer cnt = heightMap.get(-h[1]);
                cnt = ( cnt == null ) ? 1 : cnt + 1;
                heightMap.put(-h[1], cnt);
            } else {
                Integer cnt = heightMap.get(h[1]);
                if (cnt == 1) {
                    heightMap.remove(h[1]);
                } else {
                    heightMap.put(h[1], cnt - 1);
                }
            }
            int currHeight = heightMap.firstKey(); //这个是TreeMap取当前最大元素的函数
            if (prevHeight != currHeight) {
                skyLine.add(new int[]{h[0], currHeight});
                prevHeight = currHeight;
            }
        }
        return skyLine;
    }
}
