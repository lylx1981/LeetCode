
    /** 思路：基本上很简单的题，对出现的字符计数即可。但是这里更巧的方法是借助位运算，两次^操作的结果导致1）从来没有在两个串里出现的字符该是0还是0， 2） 在两个串里都出现的，因为两次异或操作又变回0， 3） 新加的那个（不管是与串中某个字符一样，还是完全一个新的从未出现的字符），其位置上没有办法变回0， 所以那一位上会保留1。
     * 
     * 因为这里题目已经说明只可能是小写字母，所以最多是24个。所以就用1个Char字符的字节就够用了，JAVA中Char是16-Bit的
     * The char data type is a single 16-bit 
     * 
     * char: The char data type is a single 16-bit Unicode character.
     * 
     */



public char findTheDifference(String s, String t) {
	char c = 0;
	for (int i = 0; i < s.length(); ++i) {
		c ^= s.charAt(i);
	}
	for (int i = 0; i < t.length(); ++i) {
		c ^= t.charAt(i);
	}
	return c; //直接返回c其直接就是对应的那个字符，非常巧！！
}
