/*思路： 觉得很傻逼的一道题。可以看看下面的这个解，主要是利用递归。
自己还是不太明白的是如果对a的k次方求Mod的函数

另外一个是如何对一个用数组表示的大Integer进行求Mode的操作，思想是从高位往低位依次求Mod，每次把上次剩余的Mod乘以10，基本上就是在模拟除法，这个倒是要掌握，见下面的代码，并不是本题Solutino里所包含的：

int modBy(int[] b, int m){
        int rem = 0;
        for (int i=0; i < b.length; i++) {
            rem = (rem*10+b[i]) % m;
        }
        return rem;
}

运算规则
模运算与基本四则运算有些相似，但是除法例外。其规则如下：
(a + b) % p = (a % p + b % p) % p 
(a - b) % p = (a % p - b % p) % p 
(a * b) % p = (a % p * b % p) % p 
a ^ b % p = ((a % p)^b) % p 




One knowledge: ab % k = (a%k)(b%k)%k
Since the power here is an array, we'd better handle it digit by digit.
One observation:
a^1234567 % k = (a^1234560 % k) * (a^7 % k) % k = (a^123456 % k)^10 % k * (a^7 % k) % k
Looks complicated? Let me put it other way:
Suppose f(a, b) calculates a^b % k; Then translate above formula to using f :
f(a,1234567) = f(a, 1234560) * f(a, 7) % k = f(f(a, 123456),10) * f(a,7)%k;
Implementation of this idea:*/

class Solution {
    const int base = 1337;
    
    //求解a的k次方Mod 1337的函数
    int powmod(int a, int k) //a^k mod 1337 where 0 <= k <= 10
    {
        a %= base; //先对a求摸假设这个数是c，其实最后结果就是这些c的K次乘积的余数
        int result = 1;
        for (int i = 0; i < k; ++i)
            result = (result * a) % base; //这里是最不好理解的地方！！！！！！！
            //这里是直接使用(a * b) % p = (a % p * b % p) % p 这个性质，这是因为现在的a并不是原来的a, 而已经是经历过一次求摸运算后的结果了！！！%运算是敏等的，就是做一次和做多次数一样的。所以每次都应用一次%base，而不是把a乘以k次后做一次%base,是为了防止溢出
            
        return result;
    }
public:
    int superPow(int a, vector<int>& b) {
        if (b.empty()) return 1;
        int last_digit = b.back();
        b.pop_back();
        return powmod(superPow(a, b), 10) * powmod(a, last_digit) % base;
    }
};
