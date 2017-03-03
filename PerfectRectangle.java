/* 思路：这道题如果看到Tricky，一下子就简单了，主要是如果是Perfect长方形，只要满足下面两个条件：

1。所组成的Perfece长方形的四个顶点的位置上只可能有且仅有唯一1个基长方形的顶点在那里出现
2。对于其他任何基长方形顶点出现的位置，在这些位置上一定有偶数个基长方形的顶点在那里出现而造成重合。
上面2条基本看看题目中的图形例子就明白了。

3。基长方形的面积和一定等于Perfect长方形的面积


The right answer must satisfy two conditions:

the large rectangle area should be equal to the sum of small rectangles
count of all the points should be even, and that of all the four corner points should be one
*/

public boolean isRectangleCover(int[][] rectangles) {

        if (rectangles.length == 0 || rectangles[0].length == 0) return false;
        //这四个值记录Perfect长方形的四个坐标
        int x1 = Integer.MAX_VALUE;
        int x2 = Integer.MIN_VALUE;
        int y1 = Integer.MAX_VALUE;
        int y2 = Integer.MIN_VALUE;
        
        HashSet<String> set = new HashSet<String>();
        int area = 0;
        
        for (int[] rect : rectangles) {
            //对每个基长方形，检查最各个坐标，然后如果需要更新x1,x2,y,1,y2的话就更新，记得这四个值是最后Perfect 长方形的四个角坐标
            x1 = Math.min(rect[0], x1);
            y1 = Math.min(rect[1], y1);
            x2 = Math.max(rect[2], x2);
            y2 = Math.max(rect[3], y2);
            
            area += (rect[2] - rect[0]) * (rect[3] - rect[1]); //同时记录每个基长方形的大小
            
            String s1 = rect[0] + " " + rect[1];
            String s2 = rect[0] + " " + rect[3];
            String s3 = rect[2] + " " + rect[3];
            String s4 = rect[2] + " " + rect[1];
            //有了就加，没了就减，这里是一种判断某个位置上是不是有Even偶个点重合的方法            
            if (!set.add(s1)) set.remove(s1);
            if (!set.add(s2)) set.remove(s2);
            if (!set.add(s3)) set.remove(s3);
            if (!set.add(s4)) set.remove(s4);
        }
        //如果Perface长方形四个点不包含在Set里，说明不对，因为这些位置上没有点重合的现象，所以Set里肯定有他们的记录
        //同时set.size() 不等于4也不对，因为四个点肯定要有四个记录在里面。同时这一条也判断了，其他位置上发生重合的次数必须是Even才可以，这样Set里面经过Even次有了就加，没了就减的操作后，正好没有它们的纪录。
        if (!set.contains(x1 + " " + y1) || !set.contains(x1 + " " + y2) || !set.contains(x2 + " " + y1) || !set.contains(x2 + " " + y2) || set.size() != 4) return false;
        
        return area == (x2-x1) * (y2-y1); //最后判断基长方形的面积是不是和Perfect长方形一样，如果是，那真的就是True了。
}
