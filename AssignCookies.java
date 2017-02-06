/*
思路： 非常简单的题，贪心算法即可。把两个数组都由小到达排列，从小的Cookie开始，尽量让其服务每个Child。

Just assign the cookies starting from the child with less greediness to maximize the number of happy children .
*/
public class Solution {
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int i = 0;
        for(int j=0;i<g.length && j<s.length;j++) {
        	if(g[i]<=s[j]) i++;
        }
        return i;


    }
}
