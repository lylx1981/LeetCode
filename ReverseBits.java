class Solution {
    /*
     * 
        这道题是非常重要的一题，涉及到了很多位操作的基础知识复习，要好好看看！！！！
        
        下面的这个算法描述非常重要和详细！！！！
        
        另外，这部分进行优化的部分也很重要，涉及到了很多知识和技巧，要好好看看！！！！
        "
        We first intitialize result to 0. We then iterate from
        0 to 31 (an integer has 32 bits，也就是从低位到高位处理). In each iteration:
        We first shift result to the left by 1 bit. ----每往左移一位相当于做了一次乘法，因为是2进制的，所以说对result乘以了2
        Then, if the last digit of input n is 1, we add 1 to result. To
        find the last digit of n, we just do: (n & 1) --- 为了找一个数的最后一位，只需要与1做“&” 操作就可以了，非常重要的技巧！！
        Example, if n=5 (101), n&1 = 101 & 001 = 001 = 1;
        however, if n = 2 (10), n&1 = 10 & 01 = 0).
        Finally, we update n by shifting it to the right by 1 (n >>= 1) --每往右移一位，相当于把原来的末位移掉了，这样在下一轮中可以继续处理接下来的一位。
        At the end of the iteration, we return result.
        
        Example, if input n = 13 (represented in binary as
        0000_0000_0000_0000_0000_0000_0000_1101, the "_" is for readability),
        calling reverseBits(13) should return:
        1011_0000_0000_0000_0000_0000_0000_0000
        
        Here is how our algorithm would work for input n = 13:
        
        Initially, result = 0 = 0000_0000_0000_0000_0000_0000_0000_0000,
        n = 13 = 0000_0000_0000_0000_0000_0000_0000_1101
        
        Starting for loop:
        i = 0:
        result = result << 1 = 0000_0000_0000_0000_0000_0000_0000_0000.
        n&1 = 0000_0000_0000_0000_0000_0000_0000_1101 &
        0000_0000_0000_0000_0000_0000_0000_0001 =
        0000_0000_0000_0000_0000_0000_0000_0001 = 1
        therefore result = result + 1 =
        0000_0000_0000_0000_0000_0000_0000_0000 +
        0000_0000_0000_0000_0000_0000_0000_0001 =
        0000_0000_0000_0000_0000_0000_0000_0001 = 1
        We right shift n by 1 (n >>= 1) to get:
        n = 0000_0000_0000_0000_0000_0000_0000_0110.
        We then go to the next iteration.
        
        i = 1:
        result = result << 1 = 0000_0000_0000_0000_0000_0000_0000_0010;
        n&1 = 0000_0000_0000_0000_0000_0000_0000_0110 &
        0000_0000_0000_0000_0000_0000_0000_0001
        = 0000_0000_0000_0000_0000_0000_0000_0000 = 0;
        therefore we don't increment result.
        We right shift n by 1 (n >>= 1) to get:
        n = 0000_0000_0000_0000_0000_0000_0000_0011.
        We then go to the next iteration.
        
        i = 2:
        result = result << 1 = 0000_0000_0000_0000_0000_0000_0000_0100.
        n&1 = 0000_0000_0000_0000_0000_0000_0000_0011 &
        0000_0000_0000_0000_0000_0000_0000_0001 =
        0000_0000_0000_0000_0000_0000_0000_0001 = 1
        therefore result = result + 1 =
        0000_0000_0000_0000_0000_0000_0000_0100 +
        0000_0000_0000_0000_0000_0000_0000_0001 =
        result = 0000_0000_0000_0000_0000_0000_0000_0101
        We right shift n by 1 to get:
        n = 0000_0000_0000_0000_0000_0000_0000_0001.
        We then go to the next iteration.
        
        i = 3:
        result = result << 1 = 0000_0000_0000_0000_0000_0000_0000_1010.
        n&1 = 0000_0000_0000_0000_0000_0000_0000_0001 &
        0000_0000_0000_0000_0000_0000_0000_0001 =
        0000_0000_0000_0000_0000_0000_0000_0001 = 1
        therefore result = result + 1 =
        = 0000_0000_0000_0000_0000_0000_0000_1011
        We right shift n by 1 to get:
        n = 0000_0000_0000_0000_0000_0000_0000_0000 = 0.
        
        Now, from here to the end of the iteration, n is 0, so (n&1)
        will always be 0 and and n >>=1 will not change n. The only change
        will be for result <<=1, i.e. shifting result to the left by 1 digit.
        Since there we have i=4 to i = 31 iterations left, this will result
        in padding 28 0's to the right of result. i.e at the end, we get
        result = 1011_0000_0000_0000_0000_0000_0000_0000
        
        This is exactly what we expected to get

        
     */


    /**
     * O(log5-n)
     */
     /**
     * O(1) Time, O(1) Space
     * Move res 1 bit left, a
     * Get first bit of n, b
     * res = a ^ b
     * Move n right 1 bit for next loop
     * Unsigned shift means fill new bit at the left with 0 instead of 1
     */
public int reverseBits(int n) {
    int result = 0;
    for (int i = 0; i < 32; i++) {
        result <<= 1;
        if ((n & 1) == 1) result++;
        n >>>= 1; //注意正规的应该是下面这个行里面用到的>>>无符号右移这个操作,我已经改成三个>>>了，程序也过Leetcode了。
    }

    
    /*    for循环也可以写成如下格式：
    for (int i = 0; i < 32; i++) res = (res << 1) ^ ((n >>> i) & 1); ---这里的^其实改为+好也可以，^不好理解。。。其实就是实现相加操作
    */
        
    return result;
}

 /** 优化部分的思路： 把整个32位数分割成4个Byte，然后分别把他们的Reverse的结果放进Hashmap里，这样每次先在Map找，如果没有的话，再现场Reverse!!
     * O(1) Time, O(1) Space
     * Divide 32 bits into 4 bytes
     * Cache each byte and its reversed result in a hashmap
     * Check cache for result first instead of computing all
     */
    private Map<Byte, Integer> cache = new HashMap<Byte, Integer>();
      
      
    public int reverseBitsOpt(int n) {
        byte[] bytes = new byte[4];
        for (int i = 0; i < 4; i++)
            bytes[i] = (byte)((n >>> 8 * i) & 0xFF); //在第i次的loop下，相当于对n的从左边数第i个字节进行处理（假设index从0开始，8是因为每个字节有8位，所以一次8位8位的进行考察）.为了得到这个字节，可以用将n右移 8*i这么多位，经过这样的操作，要处理的那个8位就在最末位的8位了（i=0的时候不用挪，因为第0个字节现在就在末尾了）。然后再对与0xFF做"&"操作，这样的作用是，除了末位这8位保留，其他位都为变为0.这是个非常巧的技巧，要掌握！！！ 另外，看下面的描述 
            
            /*
                        把number转换为二进制，只取最低的8位（bit）。因为0xff二进制就是1111 1111。
            & 运算是，如果对应的两个bit都是1，则那个bit结果为1，否则为0.
            比如 1010 & 1101 = 1000 （二进制）
            由于0xff最低的8位是1，因此number中低8位中的&之后，如果原来是1，结果还是1，原来是0，结果位还是0.高于8位的，0xff都是0，所以无论是0还是1，结果都是0.*/
        
        int res = 0;
        for (int i = 0; i < 4; i++)
            res = (res << 8) ^ reverseBytes(bytes[i]); //因为是8位8位考虑，所以这里直接将当前res左移8位，然后衔接上当前新加的一个Reserve好的Byte。
        return res;
    }
    
    public int reverseBytes(byte b) {
        //先看看Map里有没有，有的话，直接用。
        if (cache.containsKey(b)) return cache.get(b);
        int res = 0;
        //没有的话，现场Reverse
        for (int i = 0; i < 8; i++) {
            res = (res << 1) ^ ((b >>> i) & 1); //这个地方的代码就和前面一样了，并且^好这里改为+好也可以，其实就是相加操作。Leetcode上就是相+操作
        }
        //现场Reserver完，再当即把它加入到Map里，以备后续使用，注意这里Map的可以说当前Byte，Value是这个Key被Reserve后的结果,是个int的值
        cache.put(b, res);
        return res;
    }
}



