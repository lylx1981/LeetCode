/* 思路: BackTracking。就是每次把当前String的所有前缀都检查一遍。对于一个前缀，如果是一个回文，就把它暂时加入到中间结果集中，并将去掉该前缀剩下的部分继续进行递归。结束后，再把该前缀从结果集中删除，然后继续判断下一个前缀。对于每一个中间结果集，如果发现其已经到达字符串末尾，那么它当前就正式形成一个Solution，把它放进最终结果Res里即可。      
         
*/

class Solution {
    
    public static void main(String[] args) {
        System.out.println(partition("aab"));
        System.out.println(partition("aa"));
    }
    
    /**
     * Backtracking
     */
    public static List<List<String>> partition(String s) {
        List<List<String>> res = new ArrayList<List<String>>();
        if (s == null || s.length() == 0) return res;
        partition(s, 0, res, new ArrayList<String>());
        return res;
    }
    
    public static void partition(String s, int pos, List<List<String>> res, List<String> cut) {
        //cut就是一个用来存放中间结果的地方
        if (pos == s.length()) { // note the stop condition
            res.add(new ArrayList<String>(cut)); // dereference
            return;
        }
        for (int i = pos + 1; i <= s.length(); i++) {
            String prefix = s.substring(pos, i); //注意这个JAVA函数只会去到i-1这个位置
            if (isPalindrome(prefix)) {
                cut.add(prefix);
                partition(s, i, res, cut); // update pos with i
                cut.remove(cut.size() - 1);
            }
        }
    }
    
    private static boolean isPalindrome(String str) {
        int s = 0;
        int e = str.length() - 1;
        while (s < e) {
            if (str.charAt(s) != str.charAt(e)) return false;
            s++;
            e--;
        }
        return true;
    }
}
