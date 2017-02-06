/*
思路： 比较简单的一道题，就是把每个单词和其对应的那一行进行比较就行了，主要判断依据是首先有没有对应的那个字串，有的话大家长度是否对应，长度对应的话，每个字符是不是一样，
只要有一个不符合，就返回False。 这道题其实挺好的，细节的地方很重要！！

*/

public class Solution {
    public boolean validWordSquare(List<String> words) {
        if(words == null || words.size() == 0){
            return true;
        }
        int n = words.size();
        for(int i=0; i<n; i++){ //对每个Word进行检查，每个Word就相当于一行
 //对Word所对应的列进行检查
            for(int j=0; j<words.get(i).length(); j++){
 //j >= n 保证了列数不能比Words所有的词的个数还多 （当Words.get（i）是个特别长的字串的时候就会出现这种情况），
 //words.get(j).length() <= i 保证了对应的那一列所包含的字符数至少要比i大，否则charAt(i)，就会使空值，也就是第J列对应的Word长度至少要比i大,因为charAt(i)其实取的是第i+1个字符。
 //words.get(j).charAt(i) != words.get(i).charAt(j)保证了对应位是不是相同字符

                if(j >= n || words.get(j).length() <= i || words.get(j).charAt(i) != words.get(i).charAt(j))
                    return false;
            }
        }
        return true;
    }
}
