
    /*
    思路：比较简单的一道题，可以从图上分析，比如X轴的重合部分的长度计算方法如下：左边就是两个矩形左边端点X对应部分的最大值（max(A,E)），而右边就是两个矩形右边端点X对应部分的最小值(min(G,C))。，Y轴也是类似计算。
    
    最后，判断一下两个部分是不是重合即可。如果不重合，面积就是两个面积之和，否则就是两个矩形面积之和再减去重合部分面积。

     */
   
public int computeArea(int A, int B, int C, int D, int E, int F, int G, int H) {
        
        int areaOfSqrA = (C-A) * (D-B);
         int areaOfSqrB = (G-E) * (H-F);
        
        int left = Math.max(A, E);
        int right = Math.min(G, C);
        int bottom = Math.max(F, B);
        int top = Math.min(D, H);
        
        //If overlap
        int overlap = 0;
        //直接利用上面计算出的各个值来判断是否重合，很方便，比如如果不重合，right就会跑到left左边去了，自己画一下就明白了 
        if(right > left && top > bottom)
             overlap = (right - left) * (top - bottom);
        
        return areaOfSqrA + areaOfSqrB - overlap;
    }
