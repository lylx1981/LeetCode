
/** 最直接的方法，就是把每一位对应的字符直接写好，直接映射就行了。
 * 
 * 

 */


public static String intToRoman(int num) {
    String M[] = {"", "M", "MM", "MMM"};
    String C[] = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
    String X[] = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
    String I[] = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
    return M[num/1000] + C[(num%1000)/100] + X[(num%100)/10] + I[num%10];
}

/** 
 * 九章的方法更复杂一点，是只记录关键位即可，这里面关键位除了正常的位置外，还有900（往上一位是1000，所以表示法不同），400（往上一位是500，表示法不同），90，40，9，4 等。其他位就是直接向右加即可。
 * 
 * -------------------下面是罗马数字的一些知识--------------------
 * 基本字符	
    I(1)
    V(5)
    X(10)
    L(50)
    C(100)
    D(500)
    M(1000)
    
    相同的数字连写、所表示的数等于这些数字相加得到的数、如：Ⅲ=3；
    小的数字在大的数字的右边、所表示的数等于这些数字相加得到的数、 如：Ⅷ=8、Ⅻ=12；
    小的数字、（限于 Ⅰ、X 和 C）在大的数字的左边、所表示的数等于大数减小数得到的数、如：Ⅳ=4、Ⅸ=9；
    正常使用时、连写的数字重复不得超过三次；
    在一个数的上面画一条横线、表示这个数扩大 1000 倍。
    
    组数规则编辑
    有两条须注意掌握：
    基本数字 Ⅰ、X 、C 中的任何一个、自身连用构成数目、或者放在大数的右边连用构成数目、都不能超过三个；放在大数的左边只能用一个；
    不能把基本数字 V 、L 、D 中的任何一个作为小数放在大数的左边采用相减的方法构成数目；放在大数的右边采用相加的方式构成数目、只能使用一个；
    
    对照举例编辑
    ·个位数举例
    Ⅰ－1、Ⅱ－2、Ⅲ－3、Ⅳ－4、Ⅴ－5、Ⅵ－6、Ⅶ－7、Ⅷ－8、Ⅸ－9
    ·十位数举例
    Ⅹ－10、Ⅺ－11、Ⅻ－12、XIII－13、XIV－14、XV－15、XVI－16、XVII－17、XVIII－18、XIX－19、XX－20、XXI－21、XXII－22、XXIX－29、XXX－30、XXXIV－34、XXXV－35、XXXIX－39、XL－40、L－50、LI－51、LV－55、LX－60、LXV－65、LXXX－80、XC－90、XCIII－93、XCV－95、XCVIII－98、XCIX－99
    ·百位数举例
    C－100、CC－200、CCC－300、CD－400、D－500、DC－600、DCC－700、DCCC－800、CM－900、CMXCIX－999
    ·千位数举例
    M－1000、MC－1100、MCD－1400、MD－1500、MDC－1600、MDCLXVI－1666、MDCCCLXXXVIII－1888、MDCCCXCIX－1899、MCM－1900、MCMLXXVI－1976、MCMLXXXIV－1984、MCMXC－1990、MM－2000、MMMCMXCIX－3999   

 */

public class Solution {
	public String intToRoman(int num) {
		if(num <= 0) {
			return "";
		}
	    int[] nums = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
	    String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
	    StringBuilder res = new StringBuilder();
	    int digit=0;
	    while (num > 0) {
	        int times = num / nums[digit];
	        num -= nums[digit] * times;
	        for ( ; times > 0; times--) {
	            res.append(symbols[digit]);
	        }
	        digit++;
	    }
	    return res.toString();
	}
}
