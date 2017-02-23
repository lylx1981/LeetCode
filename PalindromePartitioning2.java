 
 /*
思路1： 直接使用动态规划，用一个Cut数组存储前i个字符组成的字串最多需要多少Cut。然后对原字符串进行遍历，对于每个字符，考虑以其为中心的像两边扩展半径为j的所组成的串，同时保证其一定需要是回文串（半径j延伸到尽可能不能再延伸为止）。这个过程中，随时更新cut[i+j]的值，这个值其实基本由cut[i-j]再加上1构成，因为从0到i-j我们已经知道需要cut[i-j]这么多次刀了，那么对于【0，i+j】这个长度的串来说，我现在只需要在S【i-j】这里再多一刀就行了（也就是为什么要加1），因为现在我前提条件就是我已经知道s[i-j, i+j]本身就是回文串了，所以在S【i-j来一刀，左右都是回文。这样Cur会不断从前到后被更新，最后返回cut[n]即可。

另外，要注意分别区分以i为中心，长度为奇数以及长度为偶数的回文。奇数的话，i就在最中间。

Took me a while to understand. I'd like to help further explain it.

The definition of 'cut' array is the minimum number of cuts of a sub string. More specifically, cut[n] stores the cut number of string s[0, n-1].

Here is the basic idea of the solution:

Initialize the 'cut' array: For a string with n characters s[0, n-1], it needs at most n-1 cut. Therefore, the 'cut' array is initialized as cut[i] = i-1

Use two variables in two loops to represent a palindrome:
The external loop variable 'i' represents the center of the palindrome. The internal loop variable 'j' represents the 'radius' of the palindrome. Apparently, j <= i is a must.
This palindrome can then be represented as s[i-j, i+j]. If this string is indeed a palindrome, then one possible value of cut[i+j] is cut[i-j] + 1, where cut[i-j] corresponds to s[0, i-j-1] and 1 correspond to the palindrome s[i-j, i+j];

When the loops finish, you'll get the answer at cut[s.length]
*/




class Solution {
public:
    int minCut(string s) {
        int n = s.size();
        vector<int> cut(n+1, 0);  // number of cuts for the first k characters
        for (int i = 0; i <= n; i++) cut[i] = i-1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; i-j >= 0 && i+j < n && s[i-j]==s[i+j] ; j++) // odd length palindrome
                cut[i+j+1] = min(cut[i+j+1],1+cut[i-j]);

            for (int j = 1; i-j+1 >= 0 && i+j < n && s[i-j+1] == s[i+j]; j++) // even length palindrome
                cut[i+j+1] = min(cut[i+j+1],1+cut[i-j+1]);
        }
        return cut[n];
    }
};


//同样的JAVA Code如下

/**
     * Each cut at i+j is calculated by scanning (i-j)'s minimum cut + 1 if
     * s[i-j, i+j] is a palindrome. 
     */
    public int minCut(String s) {
        if(s == null || s.length() == 0) return 0;
        int len = s.length();
        int[] cuts = new int[len + 1]; // store results
        for (int i = 0; i <= len; i++) cuts[i] = i - 1; // max cuts
        for (int i = 0; i < len; i++) {
            // odd palin
            for (int j = 0; i - j >= 0 && i + j < len && s.charAt(i - j) == s.charAt(i + j); j++) cuts[i + j + 1] = Math.min(cuts[i + j + 1], 1 + cuts[i - j]);
            // even palin
            for (int j = 1; i - j + 1 >= 0 && i + j < len && s.charAt(i - j + 1) == s.charAt(i + j); j++) cuts[i + j + 1] = Math.min(cuts[i + j + 1], 1 + cuts[i - j + 1]);
        }
        return cuts[len];
    }




 
 
 /**  思路2：还是利用Backtracking，代码和131Palindrome Partitioning差不多一样，只是每次多串一个Count，作为当前已经切了几刀。另外，用一个palin来存储已经判断过是不是回文的substring以加快速度
     * Backtracking, generate all cuts
     */
    public static int minCut(String s) {
        Set<String> palin = new HashSet<String>();
        return minCut(s, 0, palin);
    }
    
    public static int minCut(String s, int count, Set<String> palin) {
        // System.out.println("s: " + s + " \tcount: " + count);
        if (s == null || s.length() == 0 || isPalindrome(s)) {
            palin.add(s);
            return count;
        }
            
        int min = Integer.MAX_VALUE;
        for (int i = s.length() - 1; i >= 0; i--) { //从后往前判断应该是会更快。
            if (isPalindrome(s.substring(0, i))) {
                palin.add(s.substring(0, i));
                // add DP here
                //substring(i)是指从i开始的后面的子串，所以这里是说如果后面剩余部分如果已经是回文，则现在的count就是所需要的刀数了，否则我就继续需要对后面的部分进行考察，也就是Result的刀数现在取决于递归调用的结果
                int result = palin.contains(s.substring(i)) ? count : minCut(s.substring(i), count + 1, palin);;
                min = Math.min(min, result);
            }
        }
        return min;
    }
    
    /**
     * judge whether a string is a Palindrome
     */
    private static boolean isPalindrome(String s) {
        if (s == null || s.length() == 0) return false;
        if (s.length() == 1) return true;
        
        int i = 0;
        int len = s.length();
        while (i < len / 2) {
            if (s.charAt(i) != s.charAt(len - i - 1)) return false;
            i++;
        }
        return true;
    }
}
