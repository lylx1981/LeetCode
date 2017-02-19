/*
思路：建议看看这道题的解释以学习UTF-8编码机制，看懂了还是非常简单的，就先看首字节的最高位有几个1，代表了当前这个字符是需要用几个字节表示的，最多是4个字节。然后后面紧跟的每个字节开头都是以01开始。就这样判断即可

*/
//C++的解法，但是比较清晰，0b的意思是二进制的意思应该？
class Solution {
public:
    bool validUtf8(vector<int>& data) {
        int count = 0;
        for (auto c : data) {
            if (count == 0) { //对于第一个数字，用其判断到底这个字符是用几个字节判断的，直接列举即可
                if ((c >> 5) == 0b110) count = 1; //这里的Count都比实际的少1，因为当前第一个字节已经算做一个字节了。
                else if ((c >> 4) == 0b1110) count = 2;
                else if ((c >> 3) == 0b11110) count = 3;
                else if ((c >> 7)) return false; //如果前面的else if都没有通过，那么只有一种可能就是单字节字符，这样的话其最高位只能是0，但是如果其最高位是1的话，直接就返回False。
            } else {
                if ((c >> 6) != 0b10) return false; //只要开头不是10，就是假的
                count--;
            }
        }
        return count == 0; //最后Count应该是0就对了
    }
};
