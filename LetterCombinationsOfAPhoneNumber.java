
/** BackTracking方法。 

 */

public class Solution {
    public ArrayList<String> letterCombinations(String digits) {
        ArrayList<String> result = new ArrayList<String>();

        if (digits == null || digits.equals("")) {
            return result;
        }
        //注意这种字典的生成代码
        Map<Character, char[]> map = new HashMap<Character, char[]>();
        map.put('0', new char[] {});
        map.put('1', new char[] {});
        map.put('2', new char[] { 'a', 'b', 'c' });
        map.put('3', new char[] { 'd', 'e', 'f' });
        map.put('4', new char[] { 'g', 'h', 'i' });
        map.put('5', new char[] { 'j', 'k', 'l' });
        map.put('6', new char[] { 'm', 'n', 'o' });
        map.put('7', new char[] { 'p', 'q', 'r', 's' });
        map.put('8', new char[] { 't', 'u', 'v'});
        map.put('9', new char[] { 'w', 'x', 'y', 'z' });

        StringBuilder sb = new StringBuilder();
        helper(map, digits, sb, result);

        return result;
    }

    private void helper(Map<Character, char[]> map, String digits, 
        StringBuilder sb, ArrayList<String> result) {
        if (sb.length() == digits.length()) {
            result.add(sb.toString());
            return;
        }

        for (char c : map.get(digits.charAt(sb.length()))) {
            sb.append(c);
            helper(map, digits, sb, result);
            sb.deleteCharAt(sb.length() - 1); //Backtrack 回溯，其实感觉这还是一层一层的加
        }
    }
}


/**     BFS方法如下，感觉更好理解。就是一层一层加
     * BFS.
     * Build combination level by level.
     * The length of the combination is the same as the level.
     * Add all possible letters to each of the result in previous level.
     */
     
     private static final String[] LETTERS = {
        "",     // 0
        "",     // 1
        "abc",  // 2
        "def",  // 3
        "ghi",  // 4
        "jkl",  // 5
        "mno",  // 6
        "pqrs", // 7
        "tuv",  // 8
        "wxyz"  // 9
    };
    
    
    public List<String> letterCombinations(String digits) {
        LinkedList<String> queue = new LinkedList<>();
        if (digits == null || digits.length() == 0) {
            return queue;
        }
        queue.add("");
        for (int i = 0; i < digits.length(); i++) {
            char[] letters = LETTERS[digits.charAt(i) - '0'].toCharArray();
            while (queue.peek().length() == i) { // Get all at this level,已经测试过，也可以用int count = queue.size();，然后用另一个k=0，每次k++ 方式也可以,循环退出提交是while(k<count)
                String s = queue.poll();
                for (char l : letters) {
                    queue.offer(s + l);
                }
            }
        }
        return queue;
    }
