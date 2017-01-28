/*
    思路： 就是用一个数组来统计maganzine里的每个字符的个数，作为Budget，然后便利ransomNote,每便利一个就让Budget减去1，如果遇到某个字符Budget<0, 就说明budget不够了，返回False即可。
    
    我自己想到的是HashMap的方法，其实是一个思想，所以注意这道题如何用26个int的数组来实现，也就是用arr[magazine.charAt(i) - 'a']这一句来确定一个字符在数组中的位置，重要！！ 
 */

    
   public class Solution {
    public boolean canConstruct(String ransomNote, String magazine) {
        int[] arr = new int[26];
        for (int i = 0; i < magazine.length(); i++) {
            arr[magazine.charAt(i) - 'a']++;
        }
        for (int i = 0; i < ransomNote.length(); i++) {
            if(--arr[ransomNote.charAt(i)-'a'] < 0) {
                return false;
            }
        }
        return true;
    }
}
