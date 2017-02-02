/** 思路，建议一个中间缓冲，主要解决上一次读取可能多读的问题，难点主要是怎么把这些多读的字符保留起来，以备下一次读取的时候用到，看下面的分析很详细
 * 
 * /**
     * What is the difference between call once and call multiple times?
     * When you call read4() which reads 4 bytes into your buffer you might read more than you need.
     * So you want to store those bytes.
     * And next time you call read will start from those stored bytes, then read more from the file.
     * <p>
     * Example:
     * You have 4 chars "a, b, c, d" in the file, and you want to call your function twice like this:
     * read(buf, 1); // should return 'a'
     * read(buf, 3); // should return 'b, c, d'
     * All the 4 chars will be consumed in the first call of read4().
     * So the tricky part of this question is:
     * how can you preserve the remaining 'b, c, d' to the second call.
     * https://discuss.leetcode.com/topic/36179/what-is-the-difference-between-call-once-and-call-multiple-times
     */
    /* 程序的具体思路是：
     
     *   Read from the file into an intermediate buffer (called buffer).
         * Write from that buffer to the outside buffer (也就是最终返回给用户的，叫做Buf).
         * If that buffer is used up, read from file again.
         * If that buffer still has characters, keep for the next time.
         * Return when we reach the end of file, or we reach n.
      
     *              
     */
    


        //buffCnt) 记录了最近一次一共从文件中读取出来多少字节放入了中间缓冲中，
        //buffPtr记录了从最近一次从文件中读取出来多少字节中已经消耗了多少， 
        private int bufPtr = 0;
        private int bufCnt = 0;
        private char[] buffer = new char[4]; //中间缓冲，不是返回给用户的，读到的先存到这里，再考到最终Buf里。


public int read(char[] buf, int n) {
    int readBytes = 0;
    while (readBytes < n) {
        if (bufPtr == 0) { // Refill intermediate buffer if needed.中间缓冲空了，所以从文件中读取填满中间缓冲
            bufCnt = read4(buffer);
        }
        if (bufCnt == 0) { // End of file.
            break; //从文件里什么也没有读到，说明到头来，退出即可
        }
        while (readBytes < n && bufPtr < bufCnt) { // Copy to outside buffer.
            buf[readBytes++] = buffer[bufPtr++]; //将中间缓冲的东西，拷贝到最终Buf里。同时ReadBytes用来记录到现在为止一共往最终缓冲Buf里拷贝了多少。
        }
        if (bufPtr == bufCnt) { // Intermediate buffer used up.
            bufPtr = 0; //当bufPtr == bufCnt，说明中间缓冲用完了，这个时候将bufPtr归为0.如果现在readBytes还没哟读够n的话，就继续进入下一轮While循环，然后再从文件里把中间缓冲填满。
        }
    }
    return readBytes;
}
}
