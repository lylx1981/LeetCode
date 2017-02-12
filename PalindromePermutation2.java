
    /*
    思路：其实不难的一道题，只是分好几个步骤：
    1. 判断是不是能生成回文 -- 单独个字符的数量多于1个的话就无法生成回文
    2. 把每个字符一半数量的那么多个字符作为生成回文的基
    3. 根据回文的基，生成全部回文
*/
public List<String> generatePalindromes(String s) {
    int odd = 0;
    String mid = "";
    List<String> res = new ArrayList<>();
    List<Character> list = new ArrayList<>();
    Map<Character, Integer> map = new HashMap<>();

    // step 1. build character count map and count odds
    for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        map.put(c, map.containsKey(c) ? map.get(c) + 1 : 1);
        odd += map.get(c) % 2 != 0 ? 1 : -1;
    }

    // cannot form any palindromic string
    if (odd > 1) return res; // 单独个字符的数量多于1个的话就无法生成回文

    // step 2. add half count of each character to list
    for (Map.Entry<Character, Integer> entry : map.entrySet()) {
        char key = entry.getKey();
        int val = entry.getValue();

        if (val % 2 != 0) mid += key; //如果这是奇数个的话，那么回文正中间的那个必定就是这个字符

        for (int i = 0; i < val / 2; i++) list.add(key); //把这个字符的二分之一数量加入到list里，List就是生成回文的基
    }

    // step 3. generate all the permutations
    getPerm(list, mid, new boolean[list.size()], new StringBuilder(), res);

    return res;
}

//这个基本上就和47题90%是一样的了。
// generate all unique permutation from list
void getPerm(List<Character> list, String mid, boolean[] used, StringBuilder sb, List<String> res) {
    if (sb.length() == list.size()) {
        // form the palindromic string
        res.add(sb.toString() + mid + sb.reverse().toString()); //如果达到了listsize,就生成最终回文
        //生成方法是加上中间的mid，如果是偶数个的话，没关系，mid就是“”，不影响。当前的sb求revese就得到了回文的右半部分，也衔接到mid后面去，这样就是一样完整的回文了。
        sb.reverse();
        return;
    }

    for (int i = 0; i < list.size(); i++) {
        // avoid duplication
        if (i > 0 && list.get(i) == list.get(i - 1) && !used[i - 1]) continue; //这里注意如果当前考察的List里的字符和其之前一位是一样的，那么如果其之前那个还没有使用过，那么现在这一位也不能使用，这是通过保持重复元素的相对顺序，而达到不生成duplicate的好方法， 和47题是一样的技巧
    
        if (!used[i]) {
            used[i] = true; //把当前i设置为访问过
            sb.append(list.get(i)); //把其加入到当前Sb 中间结果集
            // recursion
            getPerm(list, mid, used, sb, res); //继续下一层的递归调用
            // backtracking
            //回溯，注意这里回溯要有2个动作，不仅是将i从当前sb中删除，同时把used[i]也要重新设置为没有访问过。
            used[i] = false; 
            sb.deleteCharAt(sb.length() - 1); 
        }
    }
}
