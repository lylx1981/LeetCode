
    /** 思路：比较经典的二分法。用九章算法写了一下
     * 
     
     */


public class Solution extends GuessGame {
  public int guessNumber(int n) { //我只知道用户从1，n之间挑一个数，但不知道是哪个。但是guess这个函数知道是哪个 ，所以我就在1，n之间用二分法利用Guess去猜就行了。
        
        int l = 1;
        int h = n;
        while (l +1 < h) {
            int m = l + (h - l) / 2;
            int res = guess(m); //我看看当前这个m是不是用户选的数
            if (res == 0) {
                return m;
            } else if (res > 0) {
                l = m + 1;
            } else {
                h = m - 1;
            }
        }
        if(guess(l) == 0) {
            return l;
        } else {
            return h;
        }
    }

}

