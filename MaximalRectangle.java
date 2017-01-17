public class MaximalRectangle {

    /**
     * 此题是之前那道的 Largest Rectangle in Histogram 直方图中最大的矩形 的扩展，这道题的二维矩阵每一层向上都可以看做一个直方图，输入矩阵有多少行，就可以形成多少个直方图，对每个直方图都调用 Largest Rectangle in Histogram 直方图中最大的矩形 中的方法，就可以得到最大的矩形面积。那么这道题唯一要做的就是将每一层构成直方图，由于题目限定了输入矩阵的字符只有 '0' 和 '1' 两种，所以处理起来也相对简单。方法是，对于每一个点，如果是‘0’，则赋0，如果是 ‘1’，就赋 之前的height值加上1。具体参见代码如下
     * 
     * 对于二维数组中的每一行i，其上面前i行都是一个字符1的连续高度直方图，这样我们就可以转化为求row个直方图中最大矩形面积了。

        举个例子，对于二维数组
        
        [[‘1’, ‘0’, ‘0’, ‘1’],
        
        [‘0’, ‘1’, ‘1’, ‘1’],
        
        [‘1’, ‘1’, ‘1’, ‘0’]]
        
        第1行对应的字符’1’的高度直方图为[1, 0, 0, 1]， 第2行对应的字符’1’的高度直方图为[0, 1, 1, 2]， 第3行对应的字符’1’的高度直方图为[1, 2, 2, 0]。

       以下是C++代码，但是没问题，大体思想基本上是一致的！！
       
     */
     int maximalRectangle(vector<vector<char> > &matrix) {
        int res = 0;
        vector<int> height;
        for (int i = 0; i < matrix.size(); ++i) { //一行一行来判断
            height.resize(matrix[i].size());
            for (int j = 0; j < matrix[i].size(); ++j) {
            //因为是从第0行，也就是对一个柱子从上到下来计算柱子高度的，所以一旦遇到一个0，就把高度清零，否则高度递增1
            //而且height可以一直使用，也就是说计算完第i行对应的height数组后，该数组还继续可以用来计算i+1行对应的height数组，很巧！·！！
                height[j] = matrix[i][j] == '0' ? 0 : (1 + height[j]); 
            }
            //调用largestRectangleArea对height数组进行求解就行了，得到了当前对于第i行转化为Historim图的结果，然后和全局结果进行比较
            res = max(res, largestRectangleArea(height)); 
        }
        return res;
    }
}
