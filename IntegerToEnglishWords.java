public class IntegerToEnglishWords {

    public static final String[]
        LESS_THAN_TWENTY =
        {"Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight",
         "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen",
         "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
    // Notice the empty string in the front to make index relate to word.
    public static final String[]
        TENS =
        {"", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
    public static final String[] THOUSANDS = {"", "Thousand", "Million", "Billion"};

    /**非常有意思的一道题，Math,只要发现其中技巧就行，其实就是从低位开始每三位就要进一档（thousand, million, billion, etc..），每一档里面其实就是对一个三位数的正常发音。
Helper函数就是对一个三位数进行发音，20以下因为发音比较特殊，所以要把他们的字符都存起来，20以上的整10发音也不一样（比如20，30，40，50.。。），所以也要存起来。所以对于一个三位数，分别直接<20， 或者>=20但是小于100， 或者>100，三种不同情况来处理即可，同时里面还会继续涉及到递归(比如对百位发音处理完了，就要将数字对100求余，继续处理十位上的发音，十位上的发音处理完后，继续对10求余，最后处理各位上的发音)。
     * Math, String.
     * Try to find the pattern first.
     * The numbers less than 1000, e.g. xyz, can be x Hundred y"ty" z.
     * The numbers larger than 1000, we need to add thousand or million or billion.
     * Given a number num, we pronounce the least significant digits first.
     * Then concat the result to the end to next three least significant digits.
     * So the recurrence relation is:
     * Next result of num = the pronunciation of least three digits + current result of num
     * After that, remove those three digits from number.
     * Stop when number is 0.
     */
    public String numberToWords(int num) {
        if (num == 0) { // The only case that 0 should be pronounced.
            return LESS_THAN_TWENTY[0];
        }

        int i = 0;
        StringBuilder res = new StringBuilder();
        while (num > 0) {
            if (num % 1000 != 0) { // Get last 3 digits and skip intermediate zeros. //每三位处理一次
                res.insert(0, " ");
                res.insert(0, THOUSANDS[i]); // THOUSAND[0] is empty for first 3 digits.
                res.insert(0, helper(num % 1000));
            }
            num /= 1000; // Remove last 3 digits.
            i++; // Move to next THOUSANDS word.
        }
        return res.toString().trim();
    }

    /**
     * Recursive.
     * Convert number n < 1000 to English words string.
     * Base cases:
     * If n == 0, no need to convert except when the number is only 0.
     * If n < 20, can be directly fetch from less than 20 string array.
     * Recurrence relation:
     * 3 digits is the most significant digit + Hundred + recursive call on the 2 digits.
     * 2 digits is TENS + space + recursive call on the 1 digit.
     * If n < 100, combine tens' digit with the rest.
     * If 100 < n < 1000, combine hundreds' digit with " Hundred ", with the words less than 100.
     * Every word should be followed with a space, very important.
     */
    private String helper(int n) {
        if (n == 0) {
            return ""; // Only one 0 is already handled as a special case.
        } else if (n < 20) { //《20的情况，因为发音完全不同，所以直接特殊处理即可
            return LESS_THAN_TWENTY[n] + " "; // Note the blank is the space at the end.
        } else if (n < 100) {
            return TENS[n / 10] + " " + helper(n % 10); // Note the space in between. //十位数发音求完后，现在对10求余，就是对个位数的发音了 
        } else {
            return LESS_THAN_TWENTY[n / 100] + " Hundred " + helper(n % 100); //对100求余后，现在就是十位数的发音了
        }
    }

}
