/*
思路： 比较有意思且打基础的一道题，可以试验几个数，会发现基本上分数都是有理数，而且有理数基本上都是循环小数，那么可能循环的长度不会超过10为，因为只有0-9这几个数。
题目考虑的几点比较细，比如要转换成long,来操作，另外注意long也是整型，long类型的数之间相除还是整数相除，没有小数位的，和Float和double不一样。

另外，细节要注意几点，比如转换成整数，负号的问题，以及如何模拟仿真除法的运算。其实在纸上自己写写一个除法的计算过程就明白了，基本上就是一直把余数后面补充（也就是相当于×10），继续除。
另外，这到底把最终结果按照三部分计算出来，也不错，这样更清晰。
*/

class FractionToRecurringDeci {

    public static void main(String[] args) {
        FractionToRecurringDeci f = new FractionToRecurringDeci();
        // System.out.println(f.fractionToDecimal(1, 2));
        // System.out.println(f.fractionToDecimal(2, 1));
        // System.out.println(f.fractionToDecimal(2, 3));
        System.out.println(f.fractionToDecimal(Integer.MAX_VALUE, Integer.MIN_VALUE));
    }
    
    /**
     * Valid input, denominator can't be zero
     * Convert to long to avoid overflow
     * Divide into three parts, sign, before dot and after dot
     * Before dot = numerator / denominator
     * After dot = remainder * 10 / denominator
     * if already showed up, insert parentheses
     */
    public String fractionToDecimal(int numerator, int denominator) {
        if (denominator == 0) return "";
        if (numerator == 0) return "0";
        
        StringBuilder res = new StringBuilder();
        Long n = new Long(numerator); // convert to long
        Long d = new Long(denominator);
        if ((n < 0 && d > 0) || (n > 0 && d < 0)) res.append("-"); // negative
        
        n = Math.abs(n); // to abstract value
        d = Math.abs(d);
        res.append(n / d); // before dot 
        if (n % d == 0) return res.toString(); // no fraction
        
        res.append("."); // add dot
        HashMap<Long, Integer> map = new HashMap<Long, Integer>(); //Value这里记录的是当前这个数是在字符串的那个位置
        Long r = n % d; // get first remainder
        while (r > 0) {
            if (map.containsKey(r)) { // remainder appeared before 一旦在Map里发现了，那么说明一个完整循环已经出现了。
                res.insert(map.get(r), "("); // insert an open paren，找出这个数对应的Value，这个Value就是现在在Res里应该插入（括号的地方
                res.append(")"); // append a close paren 然后现在再在Res末位插入）右括号，完毕！
                break;
            }
            map.put(r, res.length()); // save remainder and the length， 把当前这个碰到的余数放进map里，并且记录res的长度，这个也就是下一次遇到r时需要插入（括号的地方
            r *= 10; // simulate long division
            res.append(r / d); //把r/d的值加入到res里，这个就是其中的一个小数位
            r %= d; // get next remainder
        }
        return res.toString();
    }
}
