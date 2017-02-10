public class Solution {
/*
思路： 非常好的一道题，自己在纸上画了很多，觉得有一些思路，考虑了各种情况，但是还是不清楚不知道怎么入手。看来以后明白其实就是要定义一种字符串的比较符，也就是给定任意两个字符串，定义其谁应该按照题意排在我们所需要的结果。具体方式就是看看让两个字符串按照2个不同排列顺序组成两个数进行比较，从而确定他们的大小关系。定义了这个比较操作符之后，用这个比较操作符对原数组进行排序，然后依次添加到Res里，就行了，非常巧！！！
*/

    /**
     * Create a comparator for sorting
     * Convert num to String and compare the concatenated result of them
     * Note {0, 0} is a special case
     */
    public String largestNumber(int[] num) {
        StringBuilder res = new StringBuilder();
        if (num == null || num.length == 0) return res.toString();
        String[] str = new String[num.length];
        for (int i = 0; i < num.length; i++) str[i] = num[i] + ""; //都变成字符串
        Comparator<String> comp = new Comparator<String>() { //定义两个字符串的比较操作
            @Override
            public int compare(String s1, String s2) {
                return Long.compare(Long.valueOf(s2 + s1), Long.valueOf(s1 + s2)); //这里注意要反着写，使得本来应该是大于的反而是小于
            }
        };
        Arrays.sort(str, comp); //因为sort函数是按从大到小排列的，因为刚才操作符是反着写的，所以现在排列的就是正好从大到小排列的了，非常巧！！
        if (str[0].equals("0")) return "0"; // deal with 0，// An extreme edge case by lc, say you have only a bunch of 0 in your int array
        for (String s : str) res.append(s);
        return res.toString();
    }
}
