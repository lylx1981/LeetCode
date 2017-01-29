/**
 * 这道题用递归Backtracking的方法最简单，但是要仔细想起楚过程。重点的难点是在如何想清楚不会生成重复的样式。
 * 问题的最重要的核心就是只要让左括号在不同的位置，那么一定是不同的样式，所以对于一个位置i,只要左括号的Budget没有用完，那么我就可以把一个左括号放在这里。
 * 同样对于一个位置i，是不是能在这个位置放一个右括号，取决于在位置i前面已经加入了多少个左括号，如果在位置i前面左括号数量加的至少不如右括号多的话，那么在当前位置再加一个右括号的话，那么该右括号就会面临没有相应的左括号配对，那么这样的情况下，这个位置就不能加一个右括号。
 * 对于每个位置i,都试图加一个左括号，一个右括号，但是继续到下一位置，直到最后完全加入了n个左括号，n个右括号时，表示一个完全合法的样式生成了，假如Result即可。
 * 
 */

public class Solution {
    public List<String> generateParenthesis(int n) {
        ArrayList<String> result = new ArrayList<String>();
        if (n <= 0) {
            return result;
        }
        helper(result, "", n, n);
        return result;
    }
    
	public void helper(ArrayList<String> result,
	                   String paren, // current paren
	                   int left,     // how many left paren we need to add
	                   int right) {  // how many right paren we need to add
		if (left == 0 && right == 0) {
			result.add(paren);
			return;
		}

        if (left > 0) {
		    helper(result, paren + "(", left - 1, right);  //如果不让该位是左括号的话，只需依赖下一行的If操作。
        }
        
        if (right > 0 && left < right) {//left < right 保证了左括号要比右括号加的多现在才可以加一个右括号。注意这两个变量存储的是buget,left < right说明左括号Budget更少，更少说明左括号加的更多。
		    helper(result, paren + ")", left, right - 1);  //让Left回溯到原先值
        }
	}
}

/**
 * 另外一个是非递归的方法，从1，。。。N-1 步，怎么计算出第N步，核心思想是把第N步的样式定义为由1。。。。N-1 步的结果经过 加工后组合起来，见如下分析：
         * My method is DP. First consider how to get the result f(n) from previous result f(0)...f(n-1).
        Actually, the result f(n) will be put an extra () pair to f(n-1). Let the "(" always at the first position, to produce a valid result, we can only put ")" in a way that there will be i pairs () inside the extra () and n - 1 - i pairs () outside the extra pair.
        
        Let us consider an example to get clear view:
        
        f(0): ""
        
        f(1): "("f(0)")"
        
        f(2): "("f(0)")"f(1), "("f(1)")"
        
        f(3): "("f(0)")"f(2), "("f(1)")"f(1), "("f(2)")"
        
        So f(n) = "("f(0)")"f(n-1) , "("f(1)")"f(n-2) "("f(2)")"f(n-3) ... "("f(i)")"f(n-1-i) ... "(f(n-1)")"
        可以看到上面的核心思想就是在第一部分的外面加上一个括号，所以这样是不会生成重复的。
        
        
        另外，可以看看这个帖子，说这个题其实是跟Catalan number相关，Catalan number其实就是如果求C_N+1,其就是对之前结果的C_0*C_N + C_1*C_N-1 + ....C_N*+C_0 求和。如果这个题只是计算个数的话，直接用这个公式就行了。下面的代码其实就是对 Catalan number公式里每个部分的*号前面那部分加一对括号即可。不会导致重复的生成。比如C_0*C_N负责了()()()....这种类型，而C_N*+C_0负责了(((((....)))))这种类型
        
        http://blog.csdn.net/xkzju2010/article/details/51284853
 * 
 */
 
 
 public class Solution
{
    public List<String> generateParenthesis(int n)
    {
        List<List<String>> lists = new ArrayList<>();
        lists.add(Collections.singletonList(""));
        
        for (int i = 1; i <= n; ++i)
        {
            final List<String> list = new ArrayList<>();
            
            for (int j = 0; j < i; ++j)
            {
                for (final String first : lists.get(j))
                {
                    for (final String second : lists.get(i - 1 - j))
                    {
                        list.add("(" + first + ")" + second);
                    }
                }
            }
            
            lists.add(list);
        }
        
        return lists.get(lists.size() - 1);
    }
}

