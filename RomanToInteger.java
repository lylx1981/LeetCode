/*
思路：这道题主要考察对罗马数字的编排知识，查询了百科，可以看到有如下几个规矩：
    在較大的羅馬數字的右邊記上較小的羅馬數字，表示大數字加小數字。
    在較大的羅馬數字的左邊記上較小的羅馬數字，表示大數字减小數字。
    左减的数字有限制，仅限于I、X、C。比如45不可以写成VL，只能是XLV
    但是，左減時不可跨越一個位值。比如，99不可以用IC（ {\displaystyle 100-1} 100-1）表示，而是用XCIX（ {\displaystyle [100-10]+[10-1]} [100-10]+[10-1]）表示。（等同於阿拉伯數字每位數字分別表示。）
    左減數字必須為一位，比如8寫成VIII，而非IIX。
    右加數字不可連續超過三位，比如14寫成XIV，而非XIIII。（見下方“數碼限制”一項。）
    
    
这里面最重要的是“左減數字必須為一位，比如8寫成VIII，而非IIX。”这一条，所以可以通过这一条来判断何时该数字是加还是减（也就是说这是唯一一种情况需要减的）。

*/

public class Solution {
public int romanToInt(String s) {
	    if (s == null || s.length()==0) {
                return 0;
	    }
	    Map<Character, Integer> m = new HashMap<Character, Integer>();
	    m.put('I', 1);
	    m.put('V', 5);
	    m.put('X', 10);
	    m.put('L', 50);
	    m.put('C', 100);
	    m.put('D', 500);
	    m.put('M', 1000);

	    int length = s.length();
	    int result = m.get(s.charAt(length - 1));
	    /* 九章的代码如下，从末尾开始，不太好理解
	    for (int i = length - 2; i >= 0; i--) {
	        if (m.get(s.charAt(i + 1)) <= m.get(s.charAt(i))) {
	            result += m.get(s.charAt(i));
	        } else {
	            result -= m.get(s.charAt(i));
	        }
	    }
	    */
	    /* 我自己的代码，从高位开始，只要当前考察的位不必下一位小，则该位就不是减，直接加就可以了，非常简单！“等于”也是加，因为减位按照规定不会出现2个减位，所以只可能有一个减位。
	    
	    */
	     for (int i = 0; i <= length - 2; i++) {
	        if (m.get(s.charAt(i)) < m.get(s.charAt(i+1))) {
	            result -= m.get(s.charAt(i));
	        } else {
	            result += m.get(s.charAt(i));
	        }
	    }
	    
	    return result;
	}
}
