

/*

思路：比较有意思的一道题。简单的方法就是判断即可。但是一个关键点是如何快速判断两个人一字符串是不是有任何重复字符。方法是对每个字符串的每个字符进行考察，每次将一个1左移该字符与‘a’的距离同时与当前结果做|操作。这样的话，对所有字符考察完毕后，每个字符串都有一个唯一的26位的二进制数表示，其中为1的位就是这个字符串里出现了哪些字符。这样，对于任意两个字符串，只要对他们的这个二进制数进行&操作，一旦等于0，代表两者没有任何重复字符，则可以对其进行计算其乘积 。

int has 32bits,but lower case letters only has 26 .we can use the lowest 26 bit of int indicates that the word has how many kinds of lower case letters .If the lowest bit of int is 1,it indicates the word has lower case letter 'a'.......the order of lower case letter is from right to left,like zyx.....cba.so value[i] indicates the condition of the word i having how many kinds of lower case letters


And is that possible that different characters and different length might create the same value?

Short Answer is Yes if I understand your problem correctly.

Example, "abcd" and "aabbccdd" will both return 0000 ***0 1111

you should know, we don't care how many times of a character occurs in one word, we just want to record what letters occurs in one word, so that we can use AND operation to compare if two words has the same letter afterwards.

for prior case, 0000 ***0 1111 & 0000 ***0 1111 equals to **** 1111 which means has same letter.

another case would be "abcd" and "efgh",

0000 **** 0000 1111 & 0000 **** 1111 0000 equals to 0000 **** 0000, which means these two words don't have same letters.


*/


public static int maxProduct(String[] words) {
	if (words == null || words.length == 0)
		return 0;
	int len = words.length;
	int[] value = new int[len];
	for (int i = 0; i < len; i++) {
		String tmp = words[i];
		value[i] = 0;
		for (int j = 0; j < tmp.length(); j++) {
			value[i] |= 1 << (tmp.charAt(j) - 'a');
		}
	}
	int maxProduct = 0;
	for (int i = 0; i < len; i++)
		for (int j = i + 1; j < len; j++) {
			if ((value[i] & value[j]) == 0 && (words[i].length() * words[j].length() > maxProduct))
				maxProduct = words[i].length() * words[j].length();
		}
	return maxProduct;
}

/*
第二个方法，和我想的一样，有一些优化的地方：
1. 先把数组按照字符串长度从大到小排列，然后依次判断，然后一旦要没有必要判断的地方，直接剪枝推出即可
*/

public int maxProduct(String[] words) {
        if (words == null || words.length < 2) return 0;
        Arrays.sort(words, (s1, s2) -> s2.length() - s1.length()); //这里的比较符定义了数组将按照长度从大到小排列

        int[] masks = new int[words.length];
        //计算他们的bit Mask,也就是二进制表示，和上面一样
        for (int i = 0; i < words.length; i++) {
            masks[i] = getBitMask(words[i]);
        }

        int max = 0;
        for (int i = 0; i < words.length - 1; i++) {
            //从最长的字符串开始判断，如果当前字符串长度的平方比当前max还小（因为后面要算乘积的话，也是words[i]和一个比他更小的words[j]做乘积，所以现在用words[i]的平方和max比较，因为它就是可能出现的乘积的上界），直接都不用继续判断了他们是不是有相同字符流，因为他们的成绩都还比当前max还小
            if (words[i].length() * words[i].length() <= max) break; // Pruning, all words[j] are shorter than words[i]
            for (int j = i + 1; j < words.length; j++) {
                if ((masks[i] & masks[j]) == 0) {
                    max = Math.max(max, words[i].length() * words[j].length());
                    break; // Pruning, find one is enough, the following words are shorter
                    //一旦当前更新了max, 也可以暂时跳出这一层循环，因为后面的words[j]长度更小，更不可能
                }
            }
        }
        return max;
    }

    private int getBitMask(String s) {
        int mask = 0;
        for (int i = 0; i < s.length(); i++) {
            int index = s.charAt(i) - 'a';
            mask |= 1 << index;
        }
        return mask;
    }
