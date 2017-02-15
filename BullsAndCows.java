    /*
    思路： 比较简单的题，Bull比较好计算只要对两个数组从头遍历，如果当前i位两个字符都一样就Bull+1.主要是Cow的计算，只要当前考察的i位不是Bull，那么就是潜在的Cow位，简单的方法就是分别用两个数组分别记录每个字符串中每个字符出现的次数。然后最后比较两个数组同一个字符对应的最小值（因为能够匹配的意思就是一个字符有同样多个副本在Guess和Secret里），其就是该字符贡献的Cow的个数，求和最后就是Final的Cow的个数。
    */        
        
    public class Solution {
    public String getHint(String secret, String guess) {
        int len = secret.length();
		int[] secretarr = new int[10];
		int[] guessarr = new int[10];
		int bull = 0, cow = 0;
		for (int i = 0; i < len; ++i) {
			if (secret.charAt(i) == guess.charAt(i)) {
				++bull;
			} else {
				++secretarr[secret.charAt(i) - '0'];
				++guessarr[guess.charAt(i) - '0'];
			}
		}
		for (int i = 0; i < 10; ++i) {
			cow += Math.min(secretarr[i], guessarr[i]);
		}
		return "" + bull + "A" + cow + "B";
    }
}


//方法二： 另外一个基本思想也是一样，只不过更优化，只用一个数组，有Budget使用的意思。

public String getHint(String secret, String guess) {
    int bulls = 0;
    int cows = 0;
    int[] numbers = new int[10];
    for (int i = 0; i<secret.length(); i++) {
        int s = Character.getNumericValue(secret.charAt(i));
        int g = Character.getNumericValue(guess.charAt(i));
        if (s == g) bulls++;
        else {
            if (numbers[s] < 0) cows++; //如果当前numbers[s]还是小于0的，说明numbers[s]对应的字符之前有富余的量，所以肯定能用来匹配当前出现在screat里面的这个字符,所以cows++
            if (numbers[g] > 0) cows++; //如果numbers[g] > 0，说明numbers[g]这个字符已经积攒出来需要匹配的需求，而因为guess现在出现了该字符，那么该字符现在肯定能匹配上一个之前在Screat里面出现过的该字符，所以Cows++
            numbers[s] ++; //++增加一个需要匹配的需求
            numbers[g] --; //-- 就是消耗掉一个需要匹配的需求，如果是<0，说明书当前在guess里面该字符富余的量
        }
    }
    return bulls + "A" + cows + "B";
}
