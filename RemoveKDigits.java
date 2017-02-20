/*思路： 自己在纸上画了画，基本想出来了，主要是发现如果每次从当前数字里要去除一位的话，并不是应该去除那个最大的数字，而是看如果某一位的数字比起后一位数字大的话，那么去除这一位就能导致一个更小的数。比如859，我现在应该去除8，因为8>5,这样会导致一个更小的数也就是59.相反的，我这里不应该去掉9，因为去掉9的话，得到的是85.所以具体的算法是就从一个输入数字的最高位开始判断，同时用一个新的数组存放当前生成的最新的数（虽然只是部分完成的）。当前位和最新数组的最后一位相比如果其小于最新数组的最后一位（且我们还有Budget进行去Digit操作，也就是还没去够K Digits），那么就需要把最新数组的当前最后一位删除，然后继续再和最新的最后一位比较（while循环），直到退出While循环（没删除一位，就将Budget减1）。最后，当退出While后，再把当前这个位加入到最新数组里。一直遍历原数组直到结束。最后再从最新数组从头开始对首位是0的位去除，这个时候里面剩下的就是我们需要的结果了。*/


public class Solution {
    public String removeKdigits(String num, int k) {
        int digits = num.length() - k;
        char[] stk = new char[num.length()];//存放需要生成的新的数字
        int top = 0;
        // k keeps track of how many characters we can remove
        // if the previous character in stk is larger than the current one
        // then removing it will get a smaller number
        // but we can only do so when k is larger than 0
        for (int i = 0; i < num.length(); ++i) {
            char c = num.charAt(i);
            while (top > 0 && stk[top-1] > c && k > 0) { //与当前需要生成的新的数字的最后一位比较，如果其小于那个数，就删除最新数组的最后一位，注意这里将是循环操作，不断的和stk里面最新的一位比较！！
                top -= 1;//删除当前最新数组的最后一位
                k -= 1;//每删除一个，就将K减1，也就是Budget-1.
            }
            stk[top++] = c;//While循环后，再把当前数组加入stk中。
        }
        // find the index of first non-zero digit
        int idx = 0;
        while (idx < digits && stk[idx] == '0') idx++;//去除所有开头为0的位，所以idx退出循环后就是第一个不为0的位
        return idx == digits? "0": new String(stk, idx, digits - idx);//从idx位开始，截取digits - idx长度的串就是最后结果，digit是本应该有的串的长度，而因为头0的存在，实际的最终串的长度只有digits - idx这么长
    }
}
