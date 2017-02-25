/*思路：这道题看了将近2个小时，还是不是很看懂。基本思想是从数字中的每一位进行考察，并假设当前位是一个1，然后计算其他位有多少种可能情况，然后再把假设的这一位的1和这一位的当前真实值比较，最后确定这一位到底能贡献多少个1.最后，把每一位考察完毕后，把每一位考察的结果求和即可。


。
For every digit in n (Suppose n = 240315, the digits are 2, 4, 0, 3, 1, 5)，I respectively count the number of digit 1 assuming the position of current digit is 1 and other digits of n is arbitrary.

For example, I select 3 in n as the current digit, and I suppose the position of 3 is 1.

The highn is the number composed with the digits before the current digit. In the example, highn = 240;

The lown is the number composed with the digits after the current digit. In the example, lown = 15.

The lowc = 10 ^ (the number of lower digits). In the example, lowc = 100;

As curn = 3 and curn > 1, (highn * 10 + 1) must be less than (highn * 10 + curn). Then the higher part can be 0 ~ highn, the lower part can be 0 ~ (lowc-1) （这里0可是就想象成前面什么都没有就可以了）, and the current result = (highn + 1) * lowc.

对于Curn=0的情况，因为当前位是0，所以这个时候该位实际上不是1，所以只可能对高位是0~(highn-1)考察计算该位是1的情况，而对highn ~ highn的情况，直接跳过，不计算任何值


*/
//虽然这不是Leetcode上评论最多的Solution，但是自己感觉最时刻自己看懂
public class Solution {
public int countDigitOne(int n) {
        int  res=0;
        int highn= n;
        int lowc = 1;
        int lown = 0;
        while(highn > 0){
            int curn = highn % 10;
            highn = highn / 10;
            if(1 == curn){
                //higher: 0~(highn-1);  lower:  0 ~ (lowc-1)
                res += highn * lowc;
                //higher: highn ~ highn;     lower:0~lown
                res += lown + 1;
            }else if(0 == curn){  
                //curn < 1
               //higher: 0~(highn-1);  lower:  0 ~ (lowc-1)
                res += highn * lowc;
                //higher: highn ~ highn;     lower:0 因为当前位是0，所以这个时候该位实际上不是1，所以只可能对高位是0~(highn-1)的情况考察该位是1的情况（如上面一行代码），而对highn ~ highn的情况，直接跳过，不计算任何值
            }else{              
                //curn > 1
                res += (highn + 1) * lowc;
            }
            //update lown and lowc
            lown = curn * lowc + lown;
            lowc = lowc * 10;
        }
        return res;
    }
}
