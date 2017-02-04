
    /* 思路：基本就是类似做一个HashMap的方法，对第一个字串不仅统计存在性，而且记录出现次数。然后对第二个字串遍历，对相关位置的次数--，这样的话，如果最后Hashmap里面只要有一个位置不是0，比如说负数（说明第一个串里根本没有这个字符，或者第二个字串里的这个字符比第一个字串中的个数多） 或者正数（说明第1个字串里的这个字符比第2个字串中的个数多），则表明他们不是Anagram，返回False即可。
     * Anagram 定义： A word, phrase, or sentence formed from another by rearranging its letters （所以字符不仅要一致，而且每个字符出现的个数也要一致）
        
        一个通用的技巧： 另外，要注意的时候，因为是对字符进行判定，所以Hashmap可以简化为一个Char类型的数组。因为这里只是判断小写字母，所以直接用一个26个字符大小的int数组来替代Hashmap的作用了。Again，这里用了技巧s.charAt(i) - 'a'来计算一个字符在数组中的位置。如果不仅仅是小写字母，可以直接把数组扩大为256，也就是包含所有的ASCAII码
    
    右边两个图是关于字符，字节，编码的一些知识，可以看一下底下的两个链接：    
    http://liujiacai.net/blog/2015/11/20/strings/
    http://www.regexlab.com/zh/encoding.htm


     */
     
public class Solution {
    public boolean isAnagram(String s, String t) {
        int[] alphabet = new int[26];
        for (int i = 0; i < s.length(); i++) alphabet[s.charAt(i) - 'a']++;
        for (int i = 0; i < t.length(); i++) alphabet[t.charAt(i) - 'a']--;
        for (int i : alphabet) if (i != 0) return false;
        return true;
    }
}

