

    /**给定一个words,如何计算它可能的回文字符串。
     * 思路：暴力的方法是直接对枚举所有可能的情况，然后看看是否回文串。

    改进的方法是用hash，首先将所有的单词作为Key，相应的value为它的下标。
    
    
    将worsd[i]分为两部分，一部分是left,一部分是right,当right是回文串，并且left的revers words[j]在字典中存在时，那么此时[i,j]构成回文串。
    同样的道理，当left是回文串，并且right的revers word[j] 在字典中存在时，那么此时[j,i]构成回文串。
    
    Partition the word into left and right, and see 1) if there exists a candidate in map equals the left side of current word, and right side of current word is palindrome, so concatenate(current word, candidate) forms a pair: left | right | candidate. 2) same for checking the right side of current word: candidate | left | right.
    
    
    如果给定一个单词，要么你只能挑另一个单词，然后直接比较（暴力解法）。要么你从给定单词发现可能会配对的可能，一个发现的方法就是我把给定单词分割为两部分，如果一部分已经是回文了，我只要找另一部分是不是有对应的回文在Words里（用Hash），这个时候就变成了我主动知道要找什么东西了，而不是直接拿一个其他Word来直接比较。把一个给定单词切成两部分这个想法是最难想到的，不是那么直观。
    
    这道题其实仔细想想没有那么容易，暴力解法虽然简单，但是肯定不是面试中要考察的 

     */
     
   public List<List<Integer>> palindromePairs(String[] words) {
    List<List<Integer>> ret = new ArrayList<>(); 
    if (words == null || words.length < 2) return ret;
    Map<String, Integer> map = new HashMap<String, Integer>();
    for (int i=0; i<words.length; i++) map.put(words[i], i);
    for (int i=0; i<words.length; i++) {
        // System.out.println(words[i]);
        for (int j=0; j<=words[i].length(); j++) { // notice it should be "j <= words[i].length()"
            /*        
                    The <= in for (int j=0; j<=words[i].length(); j++) is aimed to handle empty string in the input. Consider the test case of ["a", ""];
            
            Since we now use <= in for (int j=0; j<=words[i].length(); j++) instead of <. There may be duplicates in the output (consider test case ["abcd", "dcba"]). Therefore I put a str2.length()!=0 to avoid duplicates.
            
            Another way to avoid duplicates is to use Set<List<Integer>> ret = new HashSet<>(); and return new ArrayList<>(ret);*/

            String str1 = words[i].substring(0, j); 
            String str2 = words[i].substring(j);
            if (isPalindrome(str1)) {
                String str2rvs = new StringBuilder(str2).reverse().toString();
                if (map.containsKey(str2rvs) && map.get(str2rvs) != i) {
                    List<Integer> list = new ArrayList<Integer>();
                    list.add(map.get(str2rvs));
                    list.add(i);
                    ret.add(list);
                    // System.out.printf("isPal(str1): %s\n", list.toString());
                }
            }
            if (isPalindrome(str2)) {
                String str1rvs = new StringBuilder(str1).reverse().toString();
                // check "str.length() != 0" to avoid duplicates
                if (map.containsKey(str1rvs) && map.get(str1rvs) != i && str2.length()!=0) { 
                    List<Integer> list = new ArrayList<Integer>();
                    list.add(i);
                    list.add(map.get(str1rvs));
                    ret.add(list);
                    // System.out.printf("isPal(str2): %s\n", list.toString());
                }
            }
        }
    }
    return ret;
}

//这里注意“”空串以及一个字符的字符串都 会被判定给一个合法的回文，这个很重要，不然想abcd这样的就没法处理了。
private boolean isPalindrome(String str) {
    int left = 0;
    int right = str.length() - 1;
    while (left <= right) {
        if (str.charAt(left++) !=  str.charAt(right--)) return false;
    }
    return true;
}
