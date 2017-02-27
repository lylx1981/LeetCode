/*思路： 仍然复用strobogrammatic number II方法，代码基本都一样，就是对所有lower bound长度一直到upper bound长度的所有长度都调用strobogrammatic number II的方法，找到所有Candidate，最后再对每个Candidate判断一下是不是在lower bound和higher bound的范围内，就行了。

记得要注意String的num.compareT ()方法可以直接用来比较两个字符串的算术值。这个要记住。另外，记得这个代码在构建strobogrammatic number的时候是从最中心往外构建的，而下一个方法是从外往内构建的。

The basic idea is to find generate a list of strobogrammatic number with the length between the length of lower bound and the length of upper bound. Then we pass the list and ignore the numbers with the same length of lower bound or upper bound but not in the range.

I think it is not a a very optimized method and can any one provide a better one?*/




public class Solution{


	public int strobogrammaticInRange(String low, String high){
		int count = 0;
		List<String> rst = new ArrayList<String>();
		for(int n = low.length(); n <= high.length(); n++){
			rst.addAll(helper(n, n)); //对每个了length都调用strobogrammatic number II方法
		}
		for(String num : rst){
		     //对每个Candidate，都判断他的算术大小是不是在low 和high之间就行了，但是只用判断哪些length和low和high一样长度的Candidate就行了。
			if((num.length() == low.length()&&num.compareTo(low) < 0 ) ||(num.length() == high.length() && num.compareTo(high) > 0)) continue;
				count++;
		}
		return count;
	}

	private List<String> helper(int cur, int max){
		if(cur == 0) return new ArrayList<String>(Arrays.asList(""));
		if(cur == 1) return new ArrayList<String>(Arrays.asList("1", "8", "0"));

		List<String> rst = new ArrayList<String>();
		List<String> center = helper(cur - 2, max);

		for(int i = 0; i < center.size(); i++){
			String tmp = center.get(i);
			if(cur != max) rst.add("0" + tmp + "0");
			rst.add("1" + tmp + "1");
			rst.add("6" + tmp + "9");
			rst.add("8" + tmp + "8");
			rst.add("9" + tmp + "6");
		}
		return rst;
	}
}


/*这个是Leetcode上评论最多的方法基本和上面一样，但是是从外向内依次构建每个strobogrammatic number的。另外，也是构建完成后，使用同样的方法判断他们是不是在low和high的范围内。

1.Construct char arrays from low.length() to high.length()
2.Add stro pairs from outside
3. When left > right, add eligible count
*/
private static final char[][] pairs = {{'0', '0'}, {'1', '1'}, {'6', '9'}, {'8', '8'}, {'9', '6'}};

public int strobogrammaticInRange(String low, String high) {
    int[] count = {0};
    for (int len = low.length(); len <= high.length(); len++) {
        char[] c = new char[len];
        dfs(low, high, c, 0, len - 1, count);
    }
    return count[0];
}

public void dfs(String low, String high , char[] c, int left, int right, int[] count) {
    if (left > right) { //当left>right的时候，代表已经构建出一个strobogrammatic number了。
        String s = new String(c);
        if ((s.length() == low.length() && s.compareTo(low) < 0) || 
            (s.length() == high.length() && s.compareTo(high) > 0)) {
            return;
        }
        count[0]++;
        return;
    }
    for (char[] p : pairs) {
        c[left] = p[0];
        c[right] = p[1];
        if (c.length != 1 && c[0] == '0') {
            continue;
        }
        if (left == right && p[0] != p[1]) {
            continue;
        }
        dfs(low, high, c, left + 1, right - 1, count);
    }
}
