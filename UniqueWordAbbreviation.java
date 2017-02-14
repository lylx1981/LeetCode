/*  思路： 感觉挺简单的一道题，主要用Hashmap，但是有一些细节要注意，第一种情况是输入Word的缩写和字典里的所有Word的缩写完全不一样，第二种情况是和字典里某个Word的缩写一样，在这种情况下，当且仅当这个输入Word和字典里的那个Word一样时，才可以返回Ture，否则就是False，还是第三种情况，可能字典里本身就有2个词可以映射到同一个缩写了，那么这种情况下，任何输入Word设置到这个缩写的都要返回False。

综合感觉这道题貌似简单，但是还是非常容易错的！

The description (A word's abbreviation is unique if no other word from the dictionary has the same abbreviation) is clear however a bit twisting. It took me a few "Wrong Answer"s to finally understand what it's asking for.
We are trying to search for a word in a dictionary. If this word (also this word’s abbreviation) is not in the dictionary OR this word and only it’s abbreviation in the dictionary. We call a word’s abbreviation unique.
EX:

1) [“dog”]; isUnique(“dig”);   
//False, because “dig” has the same abbreviation with “dog" and “dog” is already in the dictionary. It’s not unique.

2) [“dog”, “dog"]; isUnique(“dog”);  
//True, because “dog” is the only word that has “d1g” abbreviation.

3) [“dog”, “dig”]; isUnique(“dog”);   
//False, because if we have more than one word match to the same abbreviation, this abbreviation will never be unique.



     */

public class ValidWordAbbr {
    HashMap<String, String> map;
    public ValidWordAbbr(String[] dictionary) {
        map = new HashMap<String, String>();
        //对字典里的所有词进行处理
        for(String str:dictionary){
            String key = getKey(str);
            // If there is more than one string belong to the same key
            // then the key will be invalid, we set the value to ""
            if(map.containsKey(key)){
                if(!map.get(key).equals(str)){
                    map.put(key, ""); //如果字典里本身已经有两个词具有相同的Key了，那么设置Value为空，这样凡是后面只要具有该Key的输入，都将是需要返回False。
                }
            }
            else{
                map.put(key, str);  //计算出来的Key也就是缩写作为Key，原字符串作为Value。
            }
        }
    }
    
    public boolean isUnique(String word) {
        //如果1.现在字典里不包含这个Key 2。 或者包含这个Key并且这个Key对应的VAlue和当前Word一样，也就是输入的Word和字典里面的词一样
        return !map.containsKey(getKey(word))||map.get(getKey(word)).equals(word);
    }
    //计算Key
    String getKey(String str){
        if(str.length()<=2) return str;
        return str.charAt(0)+Integer.toString(str.length()-2)+str.charAt(str.length()-1);
    }
}
