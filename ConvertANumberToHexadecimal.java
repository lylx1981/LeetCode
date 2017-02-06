
/* 这道题非常好，要好好掌握基础。
思路： 打基础的一道题，应该很熟练的会。基本思路还是将10进制数换到2进制后再进行处理，每次只处理当前数的最后4位。要只处理4位，只需要用1111（也就是10进制的15）对当前数做&操作就可以得到当前数的最后4位了。并且与&的计算结果值就对应上一个10进制的值（也就是当前数的最后4位所对应的10进制的值）。用一个Map存储所有16进制的字符，并用刚才计算的结果去Map里找到一个16进制中其对应的值，，并将当前这个映射值添加到原结果前面即可，这样一步步就把16进制表示构造出来了。然后将当前数右移4位，去除处理过的当前4为，继续，循环直到所有位数都处理完便可推出！






正数的反码是其本身
负数的反码是在其原码的基础上,符号位不变，其余各个位取反.
正数的补码就是其本身
负数的补码是在反码的基础上+1。

所以-1的32位表示是32个1，除以8，每个都是4个1，4个1在16进制里面表示f,所以-1的16进制表示就是ffffffff.


Basic idea: each time we take a look at the last four digits of
            binary verion of the input, and maps that to a hex char
            shift the input to the right by 4 bits, do it again
            until input becomes 0.

*/

public class Solution {
    
    char[] map = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};//16进制所有符号的一个Map
    
    public String toHex(int num) {
        if(num == 0) return "0";
        String result = "";
        while(num != 0){
            result = map[(num & 15)] + result; //num & 15的结果就是当前数的最后4为所代表的10进制数是多少
            num = (num >>> 4);
        }
        return result;
    }
    
    
}
