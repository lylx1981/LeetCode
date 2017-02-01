/**
 * 思路：还是用Stack，但是这个方法太巧了。而且现在不是往Stack里面放括号，而是放括号相对应的Index。
 * 主要的Idea仍然是一致往Stack里面放（，遇到）的时候，如果当前Stack顶元素是（，就Pop出一个，完成一个配对即可。
 * 否则的话把）对应的Index也放入Stack中，这样一轮结尾后，Stack里面其实就是放的不能配对的括号，也就是相应能配对的括号序列中间的切割线（也就是说切割线中间的都是合法的括号序列），那么接下来，我们要做的就是判断每两个相邻切割线的间隔，最长的那个就是最长的合法括号序列的长度，非常巧！！！
 * 
 * 
 * The workflow of the solution is as below.

Scan the string from beginning to end.
If current character is '(',
push its index to the stack. If current character is ')' and the
character at the index of the top of stack is '(', we just find a
matching pair so pop from the stack. Otherwise, we push the index of
')' to the stack.
After the scan is done, the stack will only
contain the indices of characters which cannot be matched. Then
let's use the opposite side - substring between adjacent indices
should be valid parentheses.
If the stack is empty, the whole input
string is valid. Otherwise, we can scan the stack to get longest
valid substring as described in step 3.
 */


class Solution {
public:
    int longestValidParentheses(string s) {
        int n = s.length(), longest = 0;
        stack<int> st;
        for (int i = 0; i < n; i++) {
            if (s[i] == '(') st.push(i);
            else {
                if (!st.empty()) {
                    if (s[st.top()] == '(') st.pop();
                    else st.push(i);
                }
                else st.push(i);
            }
        }
        if (st.empty()) longest = n;
        else {
            int a = n, b = 0;
            while (!st.empty()) {
                b = st.top(); st.pop();
                longest = max(longest, a-b-1);
                a = b;
            }
            longest = max(longest, a);
        }
        return longest;
    }
};

/*方法二：DP，自己也想到了DP的方法，但是分析比较绕。可以看下面的这个分析：

My solution uses DP. The main idea is as follows: I construct a array longest[], for any longest[i], it stores the longest length of valid parentheses which is end at i.

And the DP idea is :

If s[i] is '(', set longest[i] to 0,because any string end with '(' cannot be a valid one. 这一步简单

Else if s[i] is ')'

     If s[i-1] is '(', longest[i] = longest[i-2] + 2 这一步简单

     Else if s[i-1] is ')' and s[i-longest[i-1]-1] == '(', longest[i] = longest[i-1] + 2 + longest[i-longest[i-1]-2]
     
     对于上一步s[i-longest[i-1]-1]其实可以看成是s[i-1-longest[i-1]]，这一位其实就是以i-1为结尾的最大的合法自序列开始处的前一个位置，比如叫这一位为X。想法是我看看这个位置是不是（，是的话，那么它正好可以和当前i位置的）配对起来，而且一旦如此配对起来了，整个序列的长度就会大增，其中包含三部分：1. 当前的）和位置X配上的对的新的一个括号对，长度贡献2，longest[i-1]还是原来以i-1结束的最长合法序列长度，以及以i-longest[i-1]-2也就是i-1-longest[i-1]-1这一位结束的合法自序列的长度，这一位其实就是X的前面一位。
     
     最后注意在代码的过程中，要判断相应位置是不是存在，不要数组越界即可。下面的代码是C++的，自己可以写一个JAVA的

For example, input "()(())", at i = 5, longest array is [0,2,0,0,2,0], longest[5] = longest[4] + 2 + longest[1] = 6.
*/

   int longestValidParentheses(string s) {
            if(s.length() <= 1) return 0;
            int curMax = 0;
            vector<int> longest(s.size(),0);
            for(int i=1; i < s.length(); i++){
                if(s[i] == ')'){
                    if(s[i-1] == '('){
                        longest[i] = (i-2) >= 0 ? (longest[i-2] + 2) : 2;
                        curMax = max(longest[i],curMax);
                    }
                    else{ // if s[i-1] == ')', combine the previous length.
                        if(i-longest[i-1]-1 >= 0 && s[i-longest[i-1]-1] == '('){
                            longest[i] = longest[i-1] + 2 + ((i-longest[i-1]-2 >= 0)?longest[i-longest[i-1]-2]:0);
                            curMax = max(longest[i],curMax);
                        }
                    }
                }
                //else if s[i] == '(', skip it, because longest[i] must be 0
            }
            return curMax;
        }
