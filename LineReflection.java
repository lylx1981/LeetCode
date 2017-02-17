思路：这道题给了我们一堆点，问我们存不存在一条平行于y轴的直线，使得所有的点关于该直线对称。题目中的提示给的相当充分，我们只要按照提示的步骤来做就可以解题了。首先我们找到所有点的横坐标的最大值和最小值，那么二者的平均值就是中间直线的横坐标，然后我们遍历每个点，如果都能找到直线对称的另一个点，则返回true，反之返回false

自己第一次审题不清楚。

public boolean isReflected(int[][] points) {
    int max = Integer.MIN_VALUE;
    int min = Integer.MAX_VALUE;
    HashSet<String> set = new HashSet<>();
    for(int[] p:points){
        max = Math.max(max,p[0]);
        min = Math.min(min,p[0]);
        String str = p[0] + "a" + p[1];
        set.add(str);
    }
    int sum = max+min;//该值除以2的值就是中间分割线
    for(int[] p:points){
        //int[] arr = {sum-p[0],p[1]};//sum/2-p[0]是距离中间分割线的距离，然后还要再加上中间分割线的坐标也就是sum/2, 才是对称点的对标，所以两个sum/2和起来就是一个sum
        String str = (sum-p[0]) + "a" + p[1];
        if( !set.contains(str))
            return false;
        
    }
    return true;
}
