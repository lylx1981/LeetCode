public class PlusOne {
    /**
     * @param digits a number represented as an array of digits
     * @return the result
     */
     
    方法一：

    // 又是一道两个整数按数位相加的题，自后往前累加，处理下进位即可。这道题中是加1，其实还可以扩展至加2，加3等。
    // 一个数组eg[9,9,9] 代表999 然后要+1 最后一数组的形式返回 
    // 做法 本位 进位 和 linkedlist相加思路差不多
    // 注意 当 +1后n位变n+1位后 要返回一个长度更长的新数组
    // 同样，carry在这道题目里面是加1，同样可以是+2，+3都可以。 
    
    public int[] plusOne(int[] digits) {
        for (int i = digits.length - 1; i >= 0; i--) {
            digits[i] = 1 + digits[i];
            if (digits[i] == 10) { // Carry.
                digits[i] = 0;
            } else { // No carry, just return.
                return digits;
            }
        }
        // Not returned, must have carry.
        int[] ans = new int[digits.length + 1];
        ans[0] = 1;
        for (int i = 0; i < digits.length; i++) {
            ans[i + 1] = digits[i];
        }
        return ans;
    }
    
    // 方法二：

    //另外注意10进制的求余操作以及求进位的操作应该也可以是2进制的，都是通用的应该，要学会举一反三。
    // The complexity is O(1)
    // f(n) = 9/10 + 1/10 * O(n-1)
    //  ==>  O(n) =  10 / 9 = 1.1111 = O(1)
    
     public int[] plusOne(int[] digits) {
        int carries = 1;
        for (int i = digits.length - 1; i >= 0 && carries > 0; i--){  // fast break when carries equals zero
            int sum = digits[i] + carries;
            digits[i] = sum % 10;
            carries = sum / 10;
        }
        if (carries == 0){
            return digits;
        }
            
        int[] rst = new int[digits.length + 1];
        rst[0] = 1;
        for (int i = 1; i < rst.length; i++){
            rst[i] = digits[i - 1];
        }
        return rst;
    }
}
