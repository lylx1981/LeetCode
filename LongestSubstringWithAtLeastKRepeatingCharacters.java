/*
思路：用到递归以及Divce&Couque，思想比较绕，就是每次拿一个字符串作为candidate（初始就是整个字符串），然后对其进行字母出现的次数计数，如果有字符出现次数小于K个（代码中就是从第一个这样不满足条件的字符开始），把Cadidate分成2半，就继续在其左右寻找进入递归。特别地，当前这个出现次数小于K个的字符肯定不会出现在结果里，因为它在当前已经判定出来了其出现次数小于K次了，那么它在继续递归的过程中也不可能满足条件，也就是说该字符在当前已经被永久排除了。

这个题是第一次见到这样的想法，之前从没有这样想过，要好好体会，非常巧！！ 特别重要的一道题！！！



in the first pass I record counts of every character in a hashmap
in the second pass I locate the first character that appear less than k times in the string. this character is definitely not included in the result, and that separates the string into two parts.
keep doing this recursively and the maximum of the left/right part is the answer.

*/

public int longestSubstring(String s, int k) {
    char[] str = s.toCharArray();
    return helper(str,0,s.length(),k);
}
private int helper(char[] str, int start, int end,  int k){
    if(end-start<k) return 0;//substring length shorter than k.
    int[] count = new int[26];
    for(int i = start;i<end;i++){
        int idx = str[i]-'a';
        count[idx]++;
    }
    for(int i = 0;i<26;i++){
        if(count[i]<k&&count[i]>0){ //count[i]=0 => i+'a' does not exist in the string, skip it.
            for(int j = start;j<end;j++){
                if(str[j]==i+'a'){
                    int left = helper(str,start,j,k);
                    int right = helper(str,j+1,end,k);
                    return Math.max(left,right);
                }
            }
        }
    }
    return end-start;
}
