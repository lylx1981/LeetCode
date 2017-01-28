/*
    思路： 非常简单的一道题，主要是判断何时记一个数
    
    Time complexity:  O(n)
Space complexity: O(1)
 */

public int countSegments(String s) {
    int res=0;
    for(int i=0; i<s.length(); i++)
        if(s.charAt(i)!=' ' && (i==0 || s.charAt(i-1)==' ')) //当当前位不为空格，且之前一位是空格的时候，说明新碰上了一个Segement,Res加1即可。i==0的时候比较特殊，因为它没有前面一位，所以如果i==0的位不是一个空格，则直接是一个Segement
            res++;        
    return res;
}
