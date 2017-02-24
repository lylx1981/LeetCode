/*
    思路： 这个题的题意没有理解透，想了半天才明白read4(char *buf) 这个函数的意思是“把读到的字符放到buf里，并且返回读到的个数，*是个指针的意思，所以这个函数不仅仅是要返回一个读到的个数，同时还将读到的具体的东西放在了buf里”
    
    这个题要好好琢磨琢磨，感觉还是比较重要的。

发现其他人也有如下疑问：
这道题的题意有歧义
read4的函数不仅会返回读了几个字符  还会把字符读进你传得那个参数里
然后要把这些读出来的字符 存进buf里 并且返回一共读了多少字符

 */


public class Solution extends Reader4 {
    /**
     * @param buf Destination buffer //buf是destination buff这个很重要
     * @param n   Maximum number of characters to read
     * @return    The number of characters read
     */
    public int read(char[] buf, int n) {
        char[] buf4 = new char[4]; //用来存放每次用read4 API读到的新的字符，其最多是4个
        int offset = 0; //用来记录到目前为止一共读取了多少
        
        while (true) {
            int size = read4(buf4); //每次调用Read4,buf4里面就又填满了新读到的字符，同时Size反映了到底读到了几个
            for (int i = 0; i < size && offset < n; i++) { // 这里offset < n是保证我只最多读到n个就要返回了。
                buf[offset++] = buf4[i]; //把读到的依次加入最终buf即可。
            }
            if (size == 0 || offset == n) {
                return offset; //两种情况返回，一种是size==0，说明已经读不到了，所以返回。或者是已经读了n个了，直接返回。
            }
        }
    }
}
